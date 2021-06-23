package realfz.admin;

import realfz.Admin;
import realfz.DataBase;
import realfz.model.ModelPendaftaran;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class PendaftaranUjian extends JFrame {
    private JTable table1;
    private JPanel main;
    private JPanel mains;
    private JTextField txt_nim;
    private JTextField txt_nilai;
    private JTextField txt_waktu;
    private JTextField txt_tempat;
    private JButton btn_submit;
    private JTextField txt_tanggal;
    private JButton kembaliButton;
    private JScrollPane Jscroll;
    private JTextField txt_noInduk;
    private JButton downloadButton;
    private final List<ModelPendaftaran> list = new ArrayList<>();
    private String nim;

    public PendaftaranUjian(String code) {
        this.setContentPane(main);
        this.setSize(510, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2-this.getSize().height/2, dim.height/2-this.getSize().height/2);

        setTables();
        Jscroll.setEnabled(false);

        kembaliButton.addActionListener(event ->{
            JFrame frame = new Admin(code);
            frame.setVisible(true);
            this.dispose();
        });

        btn_submit.addActionListener(event -> {
            String sql = "insert into jadwal (code, nim, no_induk, nilai, tanggal, waktu, tempat) values (?, ?, ?, ?, ?, ?, ?)";
            try{
                if (!txt_nim.getText().equals("") || !txt_nilai.getText().equals("") || !txt_tempat.getText().equals("")
                        || !txt_tanggal.getText().equals("") || !txt_waktu.getText().equals("")){
                    PreparedStatement setter = DataBase.getDatafromDataBase().prepareStatement(sql);
                    setter.setInt(1, Integer.parseInt(code));
                    setter.setInt(2, Integer.parseInt(txt_nim.getText()));
                    setter.setInt(3,Integer.parseInt(txt_noInduk.getText()));
                    setter.setString(4, txt_nilai.getText());
                    setter.setString(5, txt_tanggal.getText());
                    setter.setString(6, txt_waktu.getText());
                    setter.setString(7, txt_tempat.getText());
                    setter.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data telah ditambahkan");
                } else {
                    JOptionPane.showMessageDialog(null, "Tolong isi semua datanya");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error : " + e);
            }
        });

        downloadButton.addActionListener(event -> {
            String sql = "select * from pendaftaran where nim = ?";
            InputStream input = null;
            FileOutputStream output = null;
            try {
                PreparedStatement getter = DataBase.getDatafromDataBase().prepareStatement(sql);
                if (nim != null){
                    getter.setInt(1, Integer.parseInt(nim));
                    ResultSet result = getter.executeQuery();
                    output = new FileOutputStream(new File("E:/storage/" + nim + ".pdf"));
                    if (result.next()) {
                        input = result.getBinaryStream("bukti_nilai");
                        int read;
                        while ((read = input.read())!= -1){
                            output.write(read);
                        }
                    }
                    input.close();
                    output.flush();
                    output.close();
                    JOptionPane.showMessageDialog(null, "Data telah terdownload");
                } else {
                    JOptionPane.showMessageDialog(null, "Pilih data di tabel terlebih dahulu");
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error : " + e);
            }
        });
    }

    private void setTables() {
        String sql = "select * from pendaftaran";
        try {
            Statement statement = DataBase.getDatafromDataBase().createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()){
                list.add(new ModelPendaftaran(result.getInt("nim"), result.getString("nilai")));
            }
            TableModeling modeling = new TableModeling(list);
            table1.setModel(modeling);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error : " + e);
        }
        table1.setAutoCreateRowSorter(true);

        table1.getSelectionModel().addListSelectionListener(event ->{
            if(!table1.getSelectionModel().isSelectionEmpty()){
                int selectedRow = table1.convertRowIndexToModel(table1.getSelectedRow());
                ModelPendaftaran data = list.get(selectedRow);
                if (data != null){
                    nim = String.valueOf(data.getNim());
                    txt_nim.setText(nim);
                    txt_nilai.setText(data.getNilai());
                }
            }
        });
    }

    private static class TableModeling extends AbstractTableModel {
        private final String[] header = {"Nim", "Nilai"};
        private final List<ModelPendaftaran> list;

        private TableModeling(List<ModelPendaftaran> list){
            this.list = list;
        }

        @Override
        public int getRowCount() {
            return list.size();
        }

        @Override
        public int getColumnCount() {
            return header.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> list.get(rowIndex).getNim();
                case 1 -> list.get(rowIndex).getNilai();
                default -> "-";
            };
        }

        @Override
        public String getColumnName(int column) {
            return header[column];
        }
    }
}

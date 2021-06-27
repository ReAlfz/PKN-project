package realfz;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import realfz.model.ModelJadwal;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Dosen extends JFrame {
    private JPanel main;
    private JTable table1;
    private JTextField txt_nim;
    private JTextField txt_Nperusahaan;
    private JTextField txt_Nujian;
    private JButton btn_submit;
    private JButton btn_logout;
    private JButton kembaliButton;
    private List<ModelJadwal> list = new ArrayList<>();

    public Dosen(String induk) {
        this.setContentPane(main);
        this.setSize(550, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().height / 2, dim.height / 2 - this.getSize().height / 2);

        setTables(induk);
        btn_logout.addActionListener(event ->{
            JFrame frame = new Login();
            frame.setVisible(true);
            this.dispose();
        });

        kembaliButton.addActionListener(event -> {
            JFrame frame = new Login();
            frame.setVisible(true);
            this.dispose();
        });

        btn_submit.addActionListener(event -> {
            String sql = "insert into nilai_sementara (nim, no_induk, nilai_perusahaan, nilai_ujian) values (?, ?, ?, ?)";

            if (!txt_nim.getText().equals("") || !txt_Nujian.getText().equals("") || !txt_Nperusahaan.getText().equals("")){
                try {
                    PreparedStatement setter = DataBase.getDatafromDataBase().prepareStatement(sql);
                    setter.setInt(1, Integer.parseInt(txt_nim.getText()));
                    setter.setInt(2, Integer.parseInt(induk));
                    setter.setString(3, txt_Nperusahaan.getText());
                    setter.setString(4, txt_Nujian.getText());
                    setter.executeUpdate();
                    JOptionPane.showMessageDialog(null, "data telah ditambahkan");

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error : " + e);
                }
            } else {
                JOptionPane.showMessageDialog(null, "tolong isi semua datanya");
            }
        });
    }

    private void setTables(String induk) {
        String sql = "select * from jadwal where no_induk='" + induk + "'";
        try {
            Statement statement = DataBase.getDatafromDataBase().createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()){
                list.add(new ModelJadwal(result.getInt("nim"), result.getInt("no_induk"), result.getString("nilai"),
                        result.getString("tanggal"), result.getString("waktu"), result.getString("tempat")));
            }
            ModelingTable model = new ModelingTable(list);
            table1.setModel(model);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error : " + e);
        }

        table1.getSelectionModel().addListSelectionListener(event ->{
            if (!table1.getSelectionModel().isSelectionEmpty()){
                int selectedRow = table1.convertRowIndexToModel(table1.getSelectedRow());
                ModelJadwal data = list.get(selectedRow);
                if (data != null) {
                    txt_nim.setText(String.valueOf(data.getNim()));
                    txt_Nperusahaan.setText(data.getNilai());
                }
            }
        });
    }

    private static class ModelingTable extends AbstractTableModel {
        private  final String[] header = {"Nim", "No_induk", "nilai perusahaan", "Tanggal", "Waktu", "Tempat"};
        private final List<ModelJadwal> list;

        private ModelingTable(List<ModelJadwal> list) {this.list = list;}

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
            return switch (columnIndex){
                case 0 -> list.get(rowIndex).getNim();
                case 1 -> list.get(rowIndex).getNo_induk();
                case 2 -> list.get(rowIndex).getNilai();
                case 3 -> list.get(rowIndex).getTanggal();
                case 4 -> list.get(rowIndex).getWaktu();
                case 5 -> list.get(rowIndex).getTempat();
                default -> "-";
            };
        }

        @Override
        public String getColumnName(int column) {
            return header[column];
        }
    }
}

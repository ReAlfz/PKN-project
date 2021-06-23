package realfz.admin;

import realfz.Admin;
import realfz.DataBase;
import realfz.model.ModelNilai;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NilaiPkn extends JFrame {

    private JPanel main;
    private JTable table1;
    private JTextField txt_nim;
    private JTextField txt_induk;
    private JTextField txt_nilai_perusahaan;
    private JTextField txt_nilai_ujian;
    private JTextField txt_nilai_final;
    private JButton submitButton;
    private JButton kembaliButton;
    private List<ModelNilai> list = new ArrayList<>();

    public NilaiPkn(String code) {
        this.setContentPane(main);
        this.setSize(400, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2-this.getSize().height/2, dim.height/2-this.getSize().height/2);

        setTables();

        kembaliButton.addActionListener(event -> {
            JFrame frame = new Admin(code);
            frame.setVisible(true);
            this.dispose();
        });

        submitButton.addActionListener(event -> {
            int nilai_perusahaan = Integer.parseInt(txt_nilai_perusahaan.getText()) * 60 / 100;
            int nilai_ujian = Integer.parseInt(txt_nilai_ujian.getText()) * 40 / 100;
            int finalNilai = nilai_perusahaan + nilai_ujian;
            txt_nilai_final.setText(String.valueOf(finalNilai));

            if (!txt_nim.getText().equals("") || !txt_nilai_perusahaan.getText().equals("") || !txt_induk.getText().equals("") || !txt_nilai_ujian.getText().equals("")){
                String sql = "insert into nilai_final (code, nim, no_induk, nilai_perusahaan, nilai_ujian, nilai_final) values (?, ?, ?, ?, ?, ?)";
                try {
                    PreparedStatement setter = DataBase.getDatafromDataBase().prepareStatement(sql);
                    setter.setInt(1, Integer.parseInt(code));
                    setter.setInt(2, Integer.parseInt(txt_nim.getText()));
                    setter.setInt(3, Integer.parseInt(txt_induk.getText()));
                    setter.setString(4, txt_nilai_perusahaan.getText());
                    setter.setString(5, txt_nilai_ujian.getText());
                    setter.setString(6, String.valueOf(finalNilai));
                    setter.executeUpdate();
                    JOptionPane.showMessageDialog(null, "data telah ditambahkan");

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error : " + e);
                }
            } else {
                JOptionPane.showMessageDialog(null, "tolong pilih data dari tabel");
            }
        });
    }

    private void setTables() {
        String sql = "Select * from nilai_sementara";
        try {
            Statement statement = DataBase.getDatafromDataBase().createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()){
                list.add(new ModelNilai(result.getInt("nim"), result.getInt("no_induk"), result.getString("nilai_perusahaan"), result.getString("nilai_ujian")));
            }
            ModelingTable model = new ModelingTable(list);
            table1.setModel(model);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error : " + e);
        }

        table1.getSelectionModel().addListSelectionListener(event ->{
            if (!table1.getSelectionModel().isSelectionEmpty()){
                int selectedRow = table1.convertRowIndexToModel(table1.getSelectedRow());
                ModelNilai data = list.get(selectedRow);
                if (data != null) {
                    txt_nim.setText(String.valueOf(data.getNim()));
                    txt_induk.setText(String.valueOf(data.getNo_induk()));
                    txt_nilai_perusahaan.setText(data.getNilai_perusahaan());
                    txt_nilai_ujian.setText(data.getNilai_ujian());
                }
            }
        });
    }

    private static class ModelingTable extends AbstractTableModel {
        private String[] header = {"Nim" ,"No induk", "Nilai dari perusahaan", "Nilai dari ujian"};
        private List<ModelNilai> list;

        private ModelingTable(List<ModelNilai> list) {
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
            return switch (columnIndex){
                case 0 -> list.get(rowIndex).getNim();
                case 1 -> list.get(rowIndex).getNo_induk();
                case 2 -> list.get(rowIndex).getNilai_perusahaan();
                case 3 -> list.get(rowIndex).getNilai_ujian();
                default -> "-";
            };
        }

        @Override
        public String getColumnName(int column) {
            return header[column];
        }
    }
}

package realfz.mahasiswa;

import realfz.DataBase;
import realfz.Mahasiswa;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;

public class Pendaftaran extends JFrame {

    private JPanel main;
    private JButton btn_back;
    private JTextField txt_nilai;
    private JTextField txt_bukti;
    private JTextField txt_laporan;
    private JButton btn_upload;
    private JButton btn_nilai;
    private JButton btn_laporan;

    String path_laporan, path_nilai, name_nilai, name_laporan;
    FileInputStream file_nilai = null;
    FileInputStream file_laporan = null;

    public Pendaftaran(String nim){
        this.setContentPane(main);
        this.setSize(400, 450);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2-this.getSize().height/2, dim.height/2-this.getSize().height/2);

        btn_back.addActionListener(event ->{
            JFrame frame = new Mahasiswa(nim);
            frame.setVisible(true);
            this.dispose();
        });

        btn_laporan.addActionListener(event ->{
            JFileChooser files = selectFiles();
            files.showSaveDialog(null);
            File selected = files.getSelectedFile();
            path_laporan = selected.getAbsolutePath();
            name_laporan = selected.getName();

            if (!name_laporan.endsWith(".pdf")){
                JOptionPane.showMessageDialog(null, "file harus berformat pdf");
            } else {
                txt_laporan.setText(path_laporan);
            }
        });

        btn_nilai.addActionListener(event ->{
            JFileChooser files = selectFiles();
            files.showSaveDialog(null);
            File selected = files.getSelectedFile();
            path_nilai = selected.getAbsolutePath();
            name_nilai = selected.getName();

            if (!name_nilai.endsWith(".pdf")){
                JOptionPane.showMessageDialog(null, "file harus berformat pdf");
            } else {
                txt_bukti.setText(path_nilai);
            }
        });

        btn_upload.addActionListener(event ->{
            try {
                if (!txt_bukti.getText().equals("") || !txt_laporan.getText().equals("") || !txt_nilai.getText().equals("")){
                    String sql = "insert into pendaftaran (nim, nilai, bukti_nilai, laporan) values (?, ?, ?, ?)";
                    PreparedStatement setData = DataBase.getDatafromDataBase().prepareStatement(sql);
                    file_nilai = new FileInputStream(path_nilai);
                    file_laporan = new FileInputStream(path_laporan);

                    setData.setInt(1, Integer.parseInt(nim));
                    setData.setString(2, txt_nilai.getText());
                    setData.setBinaryStream(3, file_nilai);
                    setData.setBinaryStream(4, file_laporan);
                    setData.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data telah ditambahkan");
                } else {
                    JOptionPane.showMessageDialog(null, "tolong isi semua datanya");
                }

            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "Error : " + e);
            }
        });
    }

    private JFileChooser selectFiles(){
        JFileChooser jfile = new JFileChooser();
        jfile.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.pdf", "pdf");
        jfile.addChoosableFileFilter(filter);
        return jfile;
    }
}

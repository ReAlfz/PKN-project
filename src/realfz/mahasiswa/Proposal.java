package realfz.mahasiswa;

import realfz.DataBase;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.Types;

public class Proposal extends JFrame {

    private JPanel main;
    private JButton uploadButton;
    private JTextField txt_tempat;
    private JTextField txt_waktu;
    private JButton btn_curiculum;
    private JButton btn_portofolio;
    private JLabel txt_upload;
    private JTextField txt_subject_email;
    private JTextField txt_to_email;
    private JTextField txt_content_email;
    private JButton btn_content_email;
    private JTextField txt_curiculum;
    private JButton btn_back;

    String path_curiculum, path_portofolio, fileName_portofolio, fileName_curiculum;
    int result_curiculum, result_portofolio;
    FileInputStream file_curiculum = null;
    FileInputStream file_portofolio = null;

    public Proposal(String nim){
        this.setContentPane(main);
        this.setSize(450, 600);
        String sql = "INSERT INTO proposal (curiculum, portofolio, tempat_pkn, waktu, nim) values (?, ?, ?, ?, ?)";


        btn_curiculum.addActionListener(event ->{
            JFileChooser files = selectFiles();
            result_curiculum = files.showSaveDialog(null);
            File selectedFile = files.getSelectedFile();
            fileName_curiculum =  selectedFile.getName();
            path_curiculum = selectedFile.getAbsolutePath();

            if (!fileName_curiculum.endsWith(".pdf")){
                JOptionPane.showMessageDialog(null, "file harus berformat pdf");
            } else {
                txt_curiculum.setText(path_curiculum);
            }
        });

        btn_content_email.addActionListener(event ->{
            JFileChooser files = selectFiles();
            result_portofolio = files.showSaveDialog(null);
            File selectedFile = files.getSelectedFile();
            fileName_portofolio =  selectedFile.getName();
            path_portofolio = selectedFile.getAbsolutePath();

            if (!fileName_portofolio.endsWith(".pdf")){
                JOptionPane.showMessageDialog(null, "file harus berformat pdf");
            } else {
                txt_content_email.setText(path_portofolio);
            }
        });

        uploadButton.addActionListener(event ->{
            String tempat = txt_tempat.getText();
            String waktu = txt_waktu.getText();
            String to = txt_to_email.getText();
            String subject = txt_subject_email.getText();

            if (result_curiculum == JFileChooser.APPROVE_OPTION && result_portofolio == JFileChooser.APPROVE_OPTION){
                try {
                    file_curiculum = new FileInputStream(path_curiculum);
                    file_portofolio = new FileInputStream(path_portofolio);
                    PreparedStatement prepare = DataBase.getDatafromDataBase().prepareStatement(sql);
                    prepare.setBinaryStream(1, file_curiculum);
                    prepare.setBinaryStream(2, file_portofolio);
                    prepare.setString(3, tempat);
                    prepare.setString(4, waktu);
                    prepare.setInt(5, Integer.parseInt(nim));
                    prepare.executeUpdate();

                } catch (Exception e){
                    System.out.println("error : " + e);
                }
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

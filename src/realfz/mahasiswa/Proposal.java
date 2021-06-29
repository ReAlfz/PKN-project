package realfz.mahasiswa;

import realfz.DataBase;
import realfz.Mahasiswa;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.util.Properties;

public class Proposal extends JFrame {

    private JPanel main;
    private JButton uploadButton;
    private JTextField txt_tempat;
    private JTextField txt_waktu;
    private JButton btn_curiculum;
    private JButton btn_portofolio;
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

    public Proposal(String nim) {
        this.setContentPane(main);
        this.setSize(450, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().height / 2, dim.height / 2 - this.getSize().height / 2);

        String sql = "INSERT INTO proposal (curiculum, portofolio, tempat_pkn, waktu, nim) values (?, ?, ?, ?, ?)";

        btn_back.addActionListener(event -> {
            JFrame frame = new Mahasiswa(nim);
            frame.setVisible(true);
            this.dispose();
        });

        btn_curiculum.addActionListener(event -> {
            JFileChooser files = selectFiles();
            result_curiculum = files.showSaveDialog(null);
            File selectedFile = files.getSelectedFile();
            fileName_curiculum = selectedFile.getName();
            path_curiculum = selectedFile.getAbsolutePath();

            if (!fileName_curiculum.endsWith(".pdf")) {
                JOptionPane.showMessageDialog(null, "file harus berformat pdf");
            } else {
                txt_curiculum.setText(path_curiculum);
            }
        });

        btn_content_email.addActionListener(event -> {
            JFileChooser files = selectFiles();
            result_portofolio = files.showSaveDialog(null);
            File selectedFile = files.getSelectedFile();
            fileName_portofolio = selectedFile.getName();
            path_portofolio = selectedFile.getAbsolutePath();

            if (!fileName_portofolio.endsWith(".pdf")) {
                JOptionPane.showMessageDialog(null, "file harus berformat pdf");
            } else {
                txt_content_email.setText(path_portofolio);
            }
        });

        uploadButton.addActionListener(event -> {
            if (result_curiculum == JFileChooser.APPROVE_OPTION && result_portofolio == JFileChooser.APPROVE_OPTION) {
                try {
                    if (!txt_tempat.getText().equals("") || !txt_waktu.getText().equals("")
                            || !txt_to_email.getText().equals("")  || !txt_subject_email.getText().equals("")) {
                        String user = "xxxx";
                        String pass = "xxxx";
                        Properties properties = new Properties();
                        properties.put("mail.smtp.auth", "true");
                        properties.put("mail.smtp.starttls.enable", "true");
                        properties.put("mail.smtp.host", "smtp.gmail.com");
                        properties.put("mail.smtp.port", "587");
                        Session session = Session.getDefaultInstance(properties, new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(user, pass);
                            }
                        });

                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress("alfianrudiyanto1@gmail.com"));
                        message.setSubject(txt_subject_email.getText());
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(txt_to_email.getText()));

                        Multipart multipart = new MimeMultipart();
                        BodyPart messageBody = new MimeBodyPart();
                        DataSource source = new FileDataSource(path_portofolio);
                        messageBody.setDataHandler(new DataHandler(source));
                        messageBody.setFileName(fileName_portofolio);
                        multipart.addBodyPart(messageBody);
                        message.setContent(multipart);
                        Transport.send(message);

                        file_curiculum = new FileInputStream(path_curiculum);
                        file_portofolio = new FileInputStream(path_portofolio);
                        PreparedStatement prepare = DataBase.getDatafromDataBase().prepareStatement(sql);

                        prepare.setBinaryStream(1, file_curiculum);
                        prepare.setBinaryStream(2, file_portofolio);
                        prepare.setString(3, txt_tempat.getText());
                        prepare.setString(4, txt_waktu.getText());
                        prepare.setInt(5, Integer.parseInt(nim));
                        prepare.executeUpdate();
                        session.getStore().close();

                        JOptionPane.showMessageDialog(null, "Data telah Terupload");
                    } else {
                        JOptionPane.showMessageDialog(null, "tolong isi semua datanya");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "error : " + e);
                }
            }
        });
    }

    private JFileChooser selectFiles() {
        JFileChooser jfile = new JFileChooser();
        jfile.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.pdf", "pdf");
        jfile.addChoosableFileFilter(filter);
        return jfile;
    }
}

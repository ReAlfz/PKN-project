package realfz.admin;

import realfz.Admin;
import realfz.DataBase;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.Properties;

public class Approval extends JFrame {

    private JPanel main;
    private JButton btn_kembali;
    private JTextField txt_file;
    private JButton btn_sync;
    private JTextField txt_jumlah;
    private JTextField txt_waktu;
    private JTextField txt_tempat;
    private JButton btn_submit;

    public Approval(String code) {
        this.setContentPane(main);
        this.setSize(450, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2-this.getSize().height/2, dim.height/2-this.getSize().height/2);

        btn_sync.addActionListener(event ->{
            getEmails();
        });

        btn_kembali.addActionListener(event -> {
            JFrame frame = new Admin(code);
            frame.setVisible(true);
            this.dispose();
        });

        btn_submit.addActionListener(event -> {
            String sql = "insert into approval (code, jumlah, tempat, waktu) values (?, ?, ?, ?)";
            try {
                if (!txt_tempat.getText().equals("") || !txt_waktu.getText().equals("") ||
                        !txt_jumlah.getText().equals("") || !txt_file.getText().equals("")){
                    PreparedStatement setter = DataBase.getDatafromDataBase().prepareStatement(sql);
                    setter.setInt(1, Integer.parseInt(code));
                    setter.setString(2, txt_jumlah.getText());
                    setter.setString(3, txt_tempat.getText());
                    setter.setString(4, txt_waktu.getText());
                    setter.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data telah di tambah");
                } else {
                    JOptionPane.showMessageDialog(null, "tolong isi semua datanya");
                }

            } catch (Exception e){
                System.out.println("Error : " + e);
            }
        });
    }

    private void getEmails() {
        String user = "alfianrudiyanto1@webmail.umm.ac.id";
        String pass = "42425566";

        Properties properties = new Properties();

        properties.put("mail.pop3.host", "pop.gmail.com");
        properties.put("mail.pop3.port", "995");

        //ssl
        properties.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.pop3.socketFactory.fallback", "false");
        properties.setProperty("mail.pop3.socketFactory.port", "995");

        Session session = Session.getDefaultInstance(properties);

        try {
            Store store = session.getStore("pop3");
            store.connect(user, pass);
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);
            Message[] arrayMessages = emailFolder.getMessages();
            System.out.println("total : "+arrayMessages.length);

            for (int i = 0; i < arrayMessages.length; i++) {
                Message message = arrayMessages[i];
                Address[] fromAddress = message.getFrom();
                String from = fromAddress[0].toString();
                String subject = message.getSubject();
                String sentDate = message.getSentDate().toString();

                String contentType = message.getContentType();
                String messageContent = "";

                String attachFiles = "";

                if (contentType.contains("multipart")) {
                    // content may contain attachments
                    Multipart multiPart = (Multipart) message.getContent();
                    int numberOfParts = multiPart.getCount();
                    for (int partCount = 0; partCount < numberOfParts; partCount++) {
                        MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                        if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                            // this part is attachment
                            String fileName = part.getFileName();
                            attachFiles += fileName + ", ";
                            part.saveFile("E:/storage" + File.separator + fileName);
                        } else {
                            // this part may be the message content
                            messageContent = part.getContent().toString();
                        }
                    }

                    if (attachFiles.length() > 1) {
                        attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                    }
                } else if (contentType.contains("text/plain")
                        || contentType.contains("text/html")) {
                    Object content = message.getContent();
                    if (content != null) {
                        messageContent = content.toString();
                    }
                }
                System.out.println("Message #" + (i + 1) + ":");
                System.out.println("\t From: " + from);
                System.out.println("\t Subject: " + subject);
                System.out.println("\t Sent Date: " + sentDate);
                System.out.println("\t Message: " + messageContent);
                System.out.println("\t Attachments: " + attachFiles);
                JOptionPane.showMessageDialog(null, "Data telah berhasil didapat\nDengan nama file : "+ "E:/storage" + attachFiles);
                txt_file.setText(attachFiles);
            }

            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException ex) {
            System.out.println("No provider for pop3.");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

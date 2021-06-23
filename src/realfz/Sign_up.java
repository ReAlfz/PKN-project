package realfz;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sign_up extends JFrame {
    private JPanel main;
    private JPanel mains;
    private JTextField txt_username;
    private JTextField txt_password;
    private JRadioButton mahasiswaRadioButton;
    private JRadioButton dosenRadioButton;
    private JRadioButton adminRadioButton;
    private JButton btn_register;
    private JButton btn_login;
    private JTextField txt_name;

    public Sign_up() {
        this.setContentPane(mains);
        this.setSize(450, 300);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2-this.getSize().height/2, dim.height/2-this.getSize().height/2);

        btn_login.addActionListener(event -> {
            JFrame frame = new Login();
            frame.setVisible(true);
            this.dispose();
        });

        btn_register.addActionListener(event -> {
            if (!mahasiswaRadioButton.isSelected() && !adminRadioButton.isSelected() && !dosenRadioButton.isSelected()){
                JOptionPane.showMessageDialog(null, "Pilih status anda");
            } else {

                if (mahasiswaRadioButton.isSelected()){
                    try {
                        String sql = "insert into mahasiswa (nim, password, nama) values (?, ?, ?)";
                        PreparedStatement setter = DataBase.getDatafromDataBase().prepareStatement(sql);
                        setter.setInt(1, Integer.parseInt(txt_username.getText()));
                        setter.setString(2, txt_password.getText());
                        setter.setString(3, txt_name.getText());
                        setter.executeUpdate();
                        JOptionPane.showMessageDialog(null, "data telah ditambahkan");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                } else if (adminRadioButton.isSelected()){
                    try {
                        String sql = "insert into admin (code, password, nama) values (?, ?, ?)";
                        PreparedStatement setter = DataBase.getDatafromDataBase().prepareStatement(sql);
                        setter.setInt(1, Integer.parseInt(txt_username.getText()));
                        setter.setString(2, txt_password.getText());
                        setter.setString(3, txt_name.getText());
                        setter.executeUpdate();
                        JOptionPane.showMessageDialog(null, "data telah ditambahkan");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                } else {
                    try {
                        String sql = "insert into dosen (no_induk, password, nama) values (?, ?, ?)";
                        PreparedStatement setter = DataBase.getDatafromDataBase().prepareStatement(sql);
                        setter.setInt(1, Integer.parseInt(txt_username.getText()));
                        setter.setString(2, txt_password.getText());
                        setter.setString(3, txt_name.getText());
                        setter.executeUpdate();
                        JOptionPane.showMessageDialog(null, "data telah ditambahkan");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
    }
}

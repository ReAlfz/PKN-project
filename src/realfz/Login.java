package realfz;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {
    private JPanel panel1;
    private JPanel login_pane;
    private JTextField txt_user;
    private JPasswordField txt_pass;
    private JButton btn_login;
    private JRadioButton mahasiswaRadioButton;
    private JRadioButton adminRadioButton;
    private JRadioButton dosenRadioButton;
    private JButton btn_register;


    public Login() {
        this.setContentPane(login_pane);
        this.setSize(450, 300);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().height / 2, dim.height / 2 - this.getSize().height / 2);

        btn_register.addActionListener(event -> {
            JFrame frame = new Sign_up();
            frame.setVisible(true);
            this.dispose();
        });

        btn_login.addActionListener(event -> {
            String user = txt_user.getText();
            String pass = txt_pass.getText();

            if (!mahasiswaRadioButton.isSelected() && !adminRadioButton.isSelected() && !dosenRadioButton.isSelected()) {
                JOptionPane.showMessageDialog(null, "Pilih status anda");
            } else {
                if (mahasiswaRadioButton.isSelected()) {
                    String sql = "select * from mahasiswa where nim = ? and password = ?";
                    try {
                        PreparedStatement getter = DataBase.getDatafromDataBase().prepareStatement(sql);
                        getter.setInt(1, Integer.parseInt(user));
                        getter.setString(2, pass);
                        ResultSet result = getter.executeQuery();
                        if (result.next()) {
                            JFrame frame = new Mahasiswa(user);
                            frame.setVisible(true);
                            this.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Id atau Password salah");
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                } else if (adminRadioButton.isSelected()) {
                    String sql = "select * from admin where code = ? and password = ?";
                    try {
                        PreparedStatement getter = DataBase.getDatafromDataBase().prepareStatement(sql);
                        getter.setInt(1, Integer.parseInt(user));
                        getter.setString(2, pass);
                        ResultSet result = getter.executeQuery();
                        if (result.next()) {
                            JFrame frame = new Admin(user);
                            frame.setVisible(true);
                            this.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Id atau Password salah");
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                } else if (dosenRadioButton.isSelected()) {
                    String sql = "select * from dosen where no_induk = ? and password = ?";
                    try {
                        PreparedStatement getter = DataBase.getDatafromDataBase().prepareStatement(sql);
                        getter.setInt(1, Integer.parseInt(user));
                        getter.setString(2, pass);
                        ResultSet result = getter.executeQuery();
                        if (result.next()) {
                            JFrame frame = new Dosen(user);
                            frame.setVisible(true);
                            this.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Id atau Password salah");
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new Login();
        frame.setVisible(true);
    }
}

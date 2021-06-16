package realfz;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends JFrame{
    private JPanel panel1;
    private JPanel login_pane;
    private JTextField txt_user;
    private JPasswordField txt_pass;
    private JButton btn_login;
    private JRadioButton mahasiswaRadioButton;
    private JRadioButton adminRadioButton;
    private JRadioButton dosenRadioButton;


    public Login(){
        this.setContentPane(login_pane);
        this.setSize(450, 300);

        btn_login.addActionListener(event -> {
            if (!mahasiswaRadioButton.isSelected() && !adminRadioButton.isSelected() && !dosenRadioButton.isSelected()){
                JOptionPane.showMessageDialog(null, "Pilih status anda");
            } else {

                if (mahasiswaRadioButton.isSelected()){
                    try {
                        String sql = "select * from mahasiswa where nim='" + txt_user.getText() + "' and password='" + txt_pass.getText() + "'";
                        Statement statement = DataBase.getDatafromDataBase().createStatement();
                        ResultSet resultSet = statement.executeQuery(sql);
                        while (resultSet.next()){
                            if (txt_user.getText().equals(String.valueOf(resultSet.getInt("nim"))) && txt_pass.getText().equals(resultSet.getString("password"))){
                                JFrame frame = new Mahasiswa(txt_user.getText());
                                frame.setVisible(true);
                                this.dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, "Id atau Password salah");
                            }
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                } else if (adminRadioButton.isSelected()){
                    try {
                        String sql = "select * from admin where code='" + txt_user.getText() + "' and password='" + txt_pass.getText() + "'";
                        Statement statement = DataBase.getDatafromDataBase().createStatement();
                        ResultSet resultSet = statement.executeQuery(sql);
                        while (resultSet.next()){
                            if (txt_user.getText().equals(String.valueOf(resultSet.getInt("code"))) && txt_pass.getText().equals(resultSet.getString("password"))){
                                JFrame frame = new Admin();
                                frame.setVisible(true);
                                this.dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, "Id atau Password salah");
                            }
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                } else {
                    try {
                        String sql = "select * from dosen where no_induk='" + txt_user.getText() + "' and password='" + txt_pass.getText() + "'";
                        Statement statement = DataBase.getDatafromDataBase().createStatement();
                        ResultSet resultSet = statement.executeQuery(sql);
                        while (resultSet.next()){
                            if (txt_user.getText().equals(String.valueOf(resultSet.getInt("no_induk"))) && txt_pass.getText().equals(resultSet.getString("password"))){
                                JFrame frame = new Dosen();
                                frame.setVisible(true);
                                this.dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, "Id atau Password salah");
                            }
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

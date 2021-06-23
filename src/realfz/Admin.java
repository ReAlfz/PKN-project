package realfz;

import realfz.admin.Approval;
import realfz.admin.NilaiPkn;
import realfz.admin.PendaftaranUjian;

import javax.swing.*;
import java.awt.*;

public class Admin extends JFrame {
    private JPanel main;
    private JButton btn_approval;
    private JButton btn_nilai;
    private JButton btn_ujian;
    private JButton logoutButton;

    public Admin (String code){
        this.setContentPane(main);
        this.setSize(400, 200);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2-this.getSize().height/2, dim.height/2-this.getSize().height/2);

        btn_approval.addActionListener(event ->{
            JFrame frame = new Approval(code);
            frame.setVisible(true);
            this.dispose();
        });

        btn_nilai.addActionListener(event ->{
            JFrame frame = new NilaiPkn(code);
            frame.setVisible(true);
            this.dispose();
        });

        btn_ujian.addActionListener(event ->{
            JFrame frame = new PendaftaranUjian(code);
            frame.setVisible(true);
            this.dispose();
        });

        logoutButton.addActionListener(event -> {
            JFrame frame = new Login();
            frame.setVisible(true);
            this.dispose();
        });
    }
}

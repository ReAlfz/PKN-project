package realfz;

import realfz.mahasiswa.Pendaftaran;
import realfz.mahasiswa.Proposal;

import javax.swing.*;
import java.awt.*;

public class Mahasiswa extends JFrame {
    private JPanel main;
    private JButton btn_proposal;
    private JButton btn_pendaftaran;
    private JButton logoutButton;

    public Mahasiswa(String nim){
        this.setContentPane(main);
        this.setSize(400, 200);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2-this.getSize().height/2, dim.height/2-this.getSize().height/2);
        btn_pendaftaran.addActionListener(event ->{
            JFrame frame = new Pendaftaran(nim);
            frame.setVisible(true);
            this.dispose();
        });

        btn_proposal.addActionListener(event -> {
            JFrame frame = new Proposal(nim);
            frame.setVisible(true);
            this.dispose();
        });

        logoutButton.addActionListener(event -> {
            JFrame login = new Login();
            login.setVisible(true);
            this.dispose();
        });
    }
}

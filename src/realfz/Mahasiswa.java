package realfz;

import realfz.mahasiswa.Pendaftaran;
import realfz.mahasiswa.Proposal;

import javax.swing.*;

public class Mahasiswa extends JFrame {
    private JPanel main;
    private JButton btn_proposal;
    private JButton btn_pendaftaran;

    public Mahasiswa(String nim){
        this.setContentPane(main);
        this.setSize(400, 200);
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
    }
}

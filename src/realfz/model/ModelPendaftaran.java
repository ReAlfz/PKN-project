package realfz.model;

import java.sql.Statement;

public class ModelPendaftaran {
    int nim;
    String nilai;

    public ModelPendaftaran(int nim, String nilai){
        this.nim = nim;
        this.nilai = nilai;
    }

    public int getNim() {
        return nim;
    }

    public String getNilai() {
        return nilai;
    }
}

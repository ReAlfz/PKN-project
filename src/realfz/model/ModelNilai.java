package realfz.model;

public class ModelNilai {
    int nim, no_induk;
    String nilai_perusahaan, nilai_ujian;

    public ModelNilai(int nim, int no_induk, String nilai_perusahaan, String nilai_ujian) {
        this.nim = nim;
        this.no_induk = no_induk;
        this.nilai_perusahaan = nilai_perusahaan;
        this.nilai_ujian = nilai_ujian;
    }

    public int getNim() {
        return nim;
    }

    public int getNo_induk() {
        return no_induk;
    }

    public String getNilai_perusahaan() {
        return nilai_perusahaan;
    }

    public String getNilai_ujian() {
        return nilai_ujian;
    }
}

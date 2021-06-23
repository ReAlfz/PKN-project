package realfz.model;

public class ModelJadwal {
    int nim, no_induk;
    String nilai, tanggal, waktu, tempat;

    public ModelJadwal(int nim, int no_induk, String nilai, String tanggal, String waktu, String tempat){
        this.nim = nim;
        this.no_induk = no_induk;
        this.nilai = nilai;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.tempat = tempat;
    }

    public int getNim() {
        return nim;
    }

    public int getNo_induk() {
        return no_induk;
    }

    public String getNilai() {
        return nilai;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public String getTempat() {
        return tempat;
    }
}

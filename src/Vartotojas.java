public class Vartotojas {
    private static int vartototojuKiekis = 0;
    private int id;
    private String vardas;
    private String slaptazodis;
    private String email;
    private Lytis lytis;

    public Vartotojas() {
        this.id = vartototojuKiekis;
        vartototojuKiekis++;
    }

    public Vartotojas(String vardas, String slaptazodis, String email, Lytis lytis) {
        this.id = vartototojuKiekis;
        this.vardas = vardas;
        this.slaptazodis = slaptazodis;
        this.email = email;
        this.lytis = lytis;
        vartototojuKiekis++;
    }

    //<editor-fold desc="Setters/Getters/Etc">

    public static int getVartototojuKiekis() {
        return vartototojuKiekis;
    }

    public static void setVartototojuKiekis(int vartototojuKiekis) {
        Vartotojas.vartototojuKiekis = vartototojuKiekis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVardas() {
        return vardas;
    }

    public void setVardas(String vardas) {
        this.vardas = vardas;
    }

    public String getSlaptazodis() {
        return slaptazodis;
    }

    public void setSlaptazodis(String slaptazodis) {
        this.slaptazodis = slaptazodis;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Lytis getLytis() {
        return lytis;
    }

    public void setLytis(Lytis lytis) {
        this.lytis = lytis;
    }

    @Override
    public String toString() {
        return String.format("Id: %d | Vardas: %s | Slaptazodis: %s | Email: %s | Lytis: %s",
                id, vardas, slaptazodis, email, lytis);
    }

    //</editor-fold>
}

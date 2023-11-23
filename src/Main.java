import java.util.Scanner;

public class Main {

    static final int MAX_PASKYRU = 100;

    static Scanner in = new Scanner(System.in);

    static Vartotojas[] vartotojai = new Vartotojas[MAX_PASKYRU];

    public static void main(String[] args) {

        // Test data
        vartotojai[0] = new Vartotojas("Jonas", "asd", "jonas@gmail.com", Lytis.VYRAS);
        vartotojai[1] = new Vartotojas("Ana", "qwe", "ana@gmail.com", Lytis.MOTERIS);
        // End of test data

        menu:
        while (true) {
            System.out.print("""
                                        
                    ┌──────────────────────────┐
                    │           MENIU          │
                    ├──────────────────────────┤
                    │ 1 - Ivesti vartotoja     │
                    │ 2 - Pakeisti esama       │
                    │ 3 - Spaudinti vartotojus │
                    │ 4 - Baigti programa      │
                    │ Ka norite daryti:\s""");
            int pasirinkimas = in.nextInt();
            System.out.println("└──────────────────────────┘");
            switch (pasirinkimas) {
                case 1 -> {
                    if (Vartotojas.getVartototojuKiekis() >= MAX_PASKYRU) {
                        System.out.println("Daugiau paskyru sukurti nebegalima!");
                        break;
                    }
                    vartotojoIvedimas(Vartotojas.getVartototojuKiekis());
                }
                case 2 -> {
                    System.out.print("Kuri vartotoja norite keisti: ");
                    int i = in.nextInt();
                    if (i < 1 || i > Vartotojas.getVartototojuKiekis()) {
                        System.out.println("Indeksas neteisingas!");
                        break;
                    }

                    vartotojoKeitimasMeniu(i - 1);
                }
                case 3 -> {

                    // Zemiau esantis enhanced for ciklas is esmes atstoja sitoki for cikla
                    /*for (int i = 0; i < vartotojai.length; i++) {
                        Vartotojas v = vartotojai[i];
                        // CIKLO VIDUS
                    }*/

                    for (int i = 0; i < Vartotojas.getVartototojuKiekis(); i++)
                        System.out.println(vartotojai[i]);
                }

                case 4 -> {
                    System.out.println("Programa baigia darba");
                    break menu;
                }
                default -> System.out.println("Blogas pasirinkimas");
            }
        }
        in.close();
    }

    public static void vartotojoIvedimas(int i) {
        System.out.println("Iveskite " + (i + 1) + " vartotoja:");

        String vardas = vardoIvestis();
        String slaptazodis = slaptazodzioIvestis();
        String email = emailIvestis();
        Lytis lytis = lytiesIvestis();

        vartotojai[i] = new Vartotojas(vardas, slaptazodis, email, lytis);
    }

    @SuppressWarnings("unused")
    public static void vartotojoKeitimas(int i) {
        String ats;
        Vartotojas vart = vartotojai[i];
        System.out.println("Iveskite " + (i + 1) + " vartotoja:");

        System.out.print("Dabartinis id: " + vart.getId() + ". Ar norite keisti id(t/n): ");
        ats = in.next();
        if (ats.equalsIgnoreCase("t"))
            vart.setId(idIvestis());

        System.out.print("Dabartinis vardas: " + vart.getVardas() + ". Ar norite keisti varda(t/n): ");
        ats = in.next();
        if (ats.equalsIgnoreCase("t"))
            vart.setVardas(vardoIvestis());

        System.out.print("Ar norite keisti slaptazodi(t/n): ");
        ats = in.next();
        if (ats.equalsIgnoreCase("t"))
            vart.setSlaptazodis(slaptazodzioIvestis());

        System.out.print("Dabartinis email: " + vart.getEmail() + ". Ar norite keisti email(t/n): ");
        ats = in.next();
        if (ats.equalsIgnoreCase("t"))
            vart.setEmail(emailIvestis());

        System.out.print("Dabartine lytis: " + vart.getLytis() + ". Ar norite keisti lyti(t/n): ");
        ats = in.next();
        if (ats.equalsIgnoreCase("t"))
            vart.setLytis(lytiesIvestis());
    }

    public static void vartotojoKeitimasMeniu(int i) {
        Vartotojas vart = vartotojai[i];
        menu:
        while (true) {
            System.out.print("""
                                        
                    ┌──────────────────────────┐
                    │           MENIU          │
                    ├──────────────────────────┤
                    │ Ka norite keisti?        │
                    │ 1 - id                   │
                    │ 2 - varda                │
                    │ 3 - slaptazodi           │
                    │ 4 - emaila               │
                    │ 5 - lyti                 │
                    │ 6 - gryzti i pradzia     │
                    │ Jusu pasirinkimas:\s""");
            int laukas = in.nextInt();
            System.out.println("└──────────────────────────┘");
            switch (laukas) {
                case 1 -> vart.setId(idIvestis());
                case 2 -> vart.setVardas(vardoIvestis());
                case 3 -> vart.setSlaptazodis(slaptazodzioIvestis());
                case 4 -> vart.setEmail(emailIvestis());
                case 5 -> vart.setLytis(lytiesIvestis());
                case 6 -> {
                    break menu;
                }
                default -> System.out.println("Blogas pasirinkimas!");
            }
        }
    }

    public static int idIvestis() {
        // TODO id validacija
        System.out.print("\tIveskite id: ");
        return in.nextInt();
    }

    public static String vardoIvestis() {
        String vardas;
        while (true) {
            System.out.print("\tIveskite varda: ");
            vardas = in.next();
            if (vardas.length() < 3)
                System.out.println("\tVardas per trumpas!");
            else if (vardas.length() > 15)
                System.out.println("\tVardas per ilgas!");
            else break;
        }
        return vardas;
    }

    public static String slaptazodzioIvestis() {
        String slaptazodis;
        String slaptazodis2;

        while (true) {
            System.out.print("\tIveskite slaptazodi: ");
            slaptazodis = in.next();

            System.out.print("\tIveskite slaptazodi(dar karta): ");
            slaptazodis2 = in.next();
            if (!slaptazodis.equals(slaptazodis2))
                System.out.println("\tSlaptazodziai nesutampa!");
            else break;
        }
        return slaptazodis;
    }

    public static String emailIvestis() {
        String email;
        while (true) {
            System.out.print("\tIveskite email: ");
            email = in.next();
            if (email.indexOf('@') < 1 || email.indexOf('@') != email.lastIndexOf('@'))
                System.out.println("\tNeteisingas email formatas!");
            else break;
        }
        return email;
    }

    public static Lytis lytiesIvestis() {
        System.out.print("\tIveskite lyti: ");
        String lytisStr = in.next().toUpperCase();
        Lytis lytis = switch (lytisStr) {
            case "VYRAS" -> Lytis.VYRAS;
            case "MOTERIS" -> Lytis.MOTERIS;
            default -> Lytis.NEZINOMA;
        };

        if (lytis.equals(Lytis.NEZINOMA))
            System.out.println("\tNeteisinga lytis");

        return lytis;
    }
}
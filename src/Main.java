import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    static Scanner in = new Scanner(System.in);

    static LinkedList<Vartotojas> vartotojaiLL = new LinkedList<>();

    public static void main(String[] args) {

        // Test data
        vartotojaiLL.add(
                new Vartotojas("Jonas", "asd", "jonas@gmail.com", Lytis.VYRAS, LocalDate.now().minusYears(26))
        );
        vartotojaiLL.add(
                new Vartotojas("Ana", "qwe", "ana@gmail.com", Lytis.MOTERIS, LocalDate.now().minusDays(126).minusYears(56))
        );
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
                    │ 4 - Trinti vartotoja     │
                    │ 5 - Baigti programa      │
                    │ Ka norite daryti:\s""");

            int pasirinkimas = 0;
            try {
                pasirinkimas = in.nextInt();
            } catch (InputMismatchException e) {
                in.nextLine();//eat buffer

            }
            System.out.println("└──────────────────────────┘");
            switch (pasirinkimas) {
                case 1 -> vartotojoIvedimas();
                case 2 -> vartotojoKoregavimas();
                case 3 -> vartotojuSpausdinimas();
                case 4 -> trintiVartotoja();

                case 5 -> {
                    System.out.println("Programa baigia darba");
                    break menu;
                }
                default -> System.out.println("Blogas pasirinkimas");
            }
        }
        in.close();
    }


    public static void vartotojoKoregavimas() {
        System.out.println("Aktyvus vartotojai");
        vartotojuSpausdinimas();

        System.out.println("Neaktyvus vartotojai:");
        vartotojaiLL.forEach(x -> {
            if (!x.isActive()) System.out.println(x);
        });

        System.out.print("Kuri vartotoja norite keisti? ID: ");
        int id = -1;


        try {
            id = in.nextInt();
        } catch (InputMismatchException e) {
            in.nextLine(); //eat the rich
            System.out.println("Ivestas ne integer numeris! exception");
        }


        for (Vartotojas v : vartotojaiLL) {
            if (id == v.getId()) {

                if (v.isActive())
                    vartotojoKeitimasMeniu(v);
                else
                    activateVartotojas(v);
                return;
            }
        }

        System.out.println("Nerastas vartotojas su ID: " + id);


    }

    public static void activateVartotojas(Vartotojas v) {

        System.out.printf("""
                Vartotojas id: %d yra neaktyvus. Todel negalima keisti jo info.
                1. Aktyvuoti vartotoja
                2. Grizti atgal
                """, v.getId());
        int pasirinkimas = 0;
        try {
            pasirinkimas = in.nextInt();
        } catch (InputMismatchException e) {
            in.nextLine();//eat the rich
            System.out.println("bloga ivestis");
        }

        switch (pasirinkimas) {
            case 1 -> v.setActive(true);
            case 2 -> {
                return;
            }
            default -> System.out.println("Blogas pasirinkimas, griztame i pradini menu");
        }


    }


    public static void vartotojuSpausdinimas() {
        vartotojaiLL.forEach(x -> {
            if (x.isActive()) System.out.println(x);
        });
    }


    public static void trintiVartotoja() {
        vartotojuSpausdinimas();
        System.out.print("Kuri vartotoja norite trinti? ID: ");
        int id = -1;
        try {
            id = in.nextInt();
        } catch (InputMismatchException e) {
            in.nextLine(); //eat buffer
            System.out.println("Blogas formatas, iveskite skaiciu!");
            return;
        }


        for (Vartotojas v : vartotojaiLL) {
            if (v.getId() == id)
                vartotojaiLL.remove(v);
            System.out.printf("Vartotojas su ID %d sekmingai istrintas\n", id);
            return;
        }

        System.out.printf("Toks id, %d, nerastas, ir niekas nebuvo istrinta", id);
//
//        Vartotojas delThis = new Vartotojas(id);
//
//        boolean delSuccess = vartotojaiLL.remove(delThis);
//        System.out.println("delSuccess: " + delSuccess);
//        if (delSuccess)
//            System.out.printf("Vartotojas su ID %d sekmingai istrintas\n", id);
//        else
//            System.out.printf("Toks id, %d, nerastas, ir niekas nebuvo istrinta", id);

    }

    public static void vartotojoIvedimas() {
        System.out.println("Iveskite " + (vartotojaiLL.size() + 1) + " vartotoja:");

        in.nextLine();//eat the rich
        String vardas = vardoIvestis();
        String slaptazodis = slaptazodzioIvestis();
        String email = emailIvestis();
        Lytis lytis = lytiesIvestis();
        LocalDate gimimoData = gimimoDienosIvestis();

        vartotojaiLL.add(new Vartotojas(vardas, slaptazodis, email, lytis, gimimoData));
    }


    public static void vartotojoKeitimasMeniu(Vartotojas vart) {

        menu:
        while (true) {
            System.out.print("""
                                        
                    ┌─────────────────────────────┐
                    │           MENIU             │
                    ├─────────────────────────────┤
                    │ Ka norite keisti?           │
                    │ 1 - varda                   │
                    │ 2 - slaptazodi              │
                    │ 3 - emaila                  │
                    │ 4 - lyti                    │
                    │ 5 - gimimo data             │
                    │ 6 - gryzti i pradzia        │
                    │ 10 - DEACTIVUOTI vertotoja  │
                    │ Jusu pasirinkimas:\s""");
            int laukas;
            try {
                laukas = in.nextInt();
            } catch (InputMismatchException e) {
                in.nextLine();
                System.out.println("Look what you've done!!! turejote ivesti skaiciu");
                break;
            }

            System.out.println("└──────────────────────────┘");
            in.nextLine();//eat the rich
            switch (laukas) {
                case 1 -> vart.setVardas(vardoIvestis());
                case 2 -> vart.setSlaptazodis(slaptazodzioIvestis());
                case 3 -> vart.setEmail(emailIvestis());
                case 4 -> vart.setLytis(lytiesIvestis());
                case 5 -> vart.setGimimoData(gimimoDienosIvestis());
                case 10 -> vart.setActive(false);
                case 6 -> {
                    break menu;
                }
                default -> System.out.println("Blogas pasirinkimas!");
            }
        }
    }


    public static String vardoIvestis() {
        String vardas;
        while (true) {
            System.out.print("\tIveskite varda: ");
            vardas = in.nextLine();
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

    public static LocalDate gimimoDienosIvestis() {
        LocalDate DOB;
        while (true) {
            System.out.print("\tIveskite gimimo diena metai-men-diena: ");
            String gimimoDataStr = in.next();
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
                DOB = LocalDate.parse(gimimoDataStr, dtf);
                if (DOB.isAfter(LocalDate.now())) {
                    System.out.println("\tGimimo data velesne nei siandiena!");
                } else break;
            } catch (DateTimeParseException e) {
                System.out.println("Neteisingas datos formatas");
            }

        }


        return DOB;
    }
}
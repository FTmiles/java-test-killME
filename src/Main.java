import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Main {
    static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyy_MM_dd-HH_mm_ss");
    static Scanner in = new Scanner(System.in);

    static LinkedList<Vartotojas> vartotojaiLL = new LinkedList<>();

    public record Busena(LinkedList<Vartotojas> vartotojai,
                         int sekantisId) implements Serializable {

    }

    public static void main(String[] args) {

        //load database
/*        try {
            FileReader fr = new FileReader("userDB.csv");
            BufferedReader br = new BufferedReader(fr);


            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);

                String[] arr = line.split(",");

                int id = Integer.parseInt(arr[0]);
                String vardas = arr[1];
                String slaptazodis = arr[2];
                String email = arr[3];
                Lytis lytis = arr[4].equals("VYRAS") ? Lytis.VYRAS : Lytis.MOTERIS;
                LocalDateTime regData = LocalDateTime.parse(arr[5]);
                LocalDate gimimoData = LocalDate.parse(arr[6]);
                boolean isActive = Boolean.parseBoolean(arr[7]);

                int vartototojuKiekis = Integer.parseInt(arr[8]);
                vartotojaiLL.add(
                        new Vartotojas(id, vardas, slaptazodis, email, lytis, regData, gimimoData, isActive, vartototojuKiekis)
                );
            }


        } catch (FileNotFoundException e) {
            System.out.println("Klaida loadinant data");
        } catch (IOException e) {
        }*/

        //////////////OBJECT LOADING///////////

/*

        try {
            FileInputStream fis = new FileInputStream("busena.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
//cia lauryno records
            Busena busena = (Busena) ois.readObject();


            vartotojaiLL = busena.vartotojai();

            Vartotojas.setVartototojuKiekis(busena.sekantisId());
            System.out.println("HIT ME?");
//            Object busena = ois.readObject();

//            vartotojaiLL = (LinkedList<Vartotojas>) ois.readObject();


            ois.close();

            System.out.println("Success loading state");

        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("ioexception");
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("classnotfound");
            throw new RuntimeException(e);
        }
*/


//        // Test data
//        vartotojaiLL.add(
//                new Vartotojas("Jonas", "asd", "jonas@gmail.com", Lytis.VYRAS, LocalDate.now().minusYears(26))
//        );
//        vartotojaiLL.add(
//                new Vartotojas("Ana", "qwe", "ana@gmail.com", Lytis.MOTERIS, LocalDate.now().minusDays(126).minusYears(56))
//        );
//        // End of test data

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
                    │ 5 - Save to database     │
                    │ 6 - save state           │
                    │ 7 - Baigti programa      │
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
                case 3 -> spausdintiPasirinkimas();
                case 4 -> trintiVartotoja();
                case 5 -> toFile(true, "userDB.csv");
                case 6 -> busenosIrasimas();

                case 7 -> {
                    System.out.println("Programa baigia darba");
                    break menu;
                }
                default -> System.out.println("Blogas pasirinkimas");
            }
        }
        in.close();
    }


    public static void spausdintiPasirinkimas() {
        System.out.println("1. Spausdinti i ekrana");
        System.out.println("2. Spausdinti i file");

        int pasirinkimas = 0;
        try {
            pasirinkimas = in.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("blogas pasirinkimas");
        }

        switch (pasirinkimas) {
            case 1 -> vartotojuSpausdinimas();
            case 2 -> toFile(false, "printedUsers.txt");
        }

    }


    //Lauryno load from csv busenos uzkrovimastxt
    private static void busenosUzkrovimasText() {
        //1. nuskaityti visus files, readAllBites, ir padaryti stringa
        try {
            FileInputStream fis = new FileInputStream("busena.ssaav");
            String content = new String(fis.readAllBytes());

            String[] vartotojaiStr = content.split("\n");

            for (String vartStr : vartotojaiStr) {
                Vartotojas vart = Vartotojas.fromCSV(vartStr);
                vartotojaiLL.add(vart);
            }


            fis.close();
            System.out.println("loaded OK success");
        } catch (Exception e) {
            System.out.println("Klaida");
        }

    }


    //Lauryno save to CSV
    public static void busenosIrasimasText() {

        try {

            ArrayList<String> xlines = new ArrayList<>();
            for (Vartotojas v : vartotojaiLL) {
                xlines.add(v.toCSVline());
            }
            Files.write(null, xlines);

            ////////////////////////////
            StringBuilder sb = new StringBuilder();
            for (Vartotojas x : vartotojaiLL) {
                sb.append(x.toCSVline()).append('\n');
            }
////////////////////////////////////////////////////

            List<String> lines = vartotojaiLL.stream().map(Vartotojas::toCSVline).toList();
//            Files.write(null, lines);
            Files.write(new File("busena.ssaav").toPath(), lines);

            //////////////////////////////////
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("busena.sav"));
            for (Vartotojas vartotojas : vartotojaiLL) {
                bos.write(vartotojas.toCSVline().getBytes());
                bos.write('\n');
            }
            System.out.println("Busena irasyta i faila.");
            bos.flush();
            bos.close();
        } catch (IOException e) {
            System.out.println("Nepavyko issaugoti busenos!");
        }

    }

    //Lauryno implementation
    public static void vartotojuSpausdinimasIFaila() {
        try {
            String fileName = String.format("vartotojai-%s.log", LocalDateTime.now().format(dtf));
            BufferedOutputStream bs = new BufferedOutputStream(new FileOutputStream(fileName));

            for (int i = 0; i < vartotojaiLL.size(); i++) {
                Vartotojas vart = vartotojaiLL.get(i);
                String line = i + " | " + vart + "\n";
                bs.write(line.getBytes());
            }

            bs.close(); //check documentation it closes
        } catch (IOException e) {
            System.out.println("nepavyko irasyti i file");
        }
    }


    public static void busenosIrasimas() {
        try {
            FileOutputStream fs = new FileOutputStream("busena.dat");
            ObjectOutputStream os = new ObjectOutputStream(fs);
            //cia jau record uzkomentuotas
            Busena busena = new Busena(vartotojaiLL, Vartotojas.getVartototojuKiekis());

            os.writeObject(busena);
            System.out.println("saved state");

            os.close();
            fs.flush();
            fs.close(); //Nezinom tikrai ar wrapper .close() uzdaro fs, ir flushina?
        } catch (IOException e) {
            System.out.println("failed to save state");
        }

    }


    public static void toFile(boolean onlyCSV, String fileName) {
        try {
            FileWriter fw = new FileWriter(fileName);

            for (Vartotojas vartotojas : vartotojaiLL) {
                if (onlyCSV)
                    fw.write(vartotojas.toCSVline());
                else
                    fw.write(vartotojas.toString());

                fw.write('\n');
            }
            fw.flush();
            fw.close();

            System.out.println("I'm done my chores!!");
        } catch (IOException e) {
            System.out.println("ivyko IOException, fun fun fun! \n" + e.toString());
        }
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
            in.next();
        }
        for (Vartotojas vartotojas : vartotojaiLL) {
            if (vartotojas.getId() == id) {
                System.out.print("""
                        1 - Trinti
                        2 - Deaktivuoti
                        3 - Atsaukti
                        Jusu pasirinkimas:\s""");
                int pasirinkimas = 3;
                try {
                    pasirinkimas = in.nextInt();
                } catch (InputMismatchException e) {
                    in.next();
                    System.out.println("Ivestas ne skaicius!");
                }
                switch (pasirinkimas) {
                    case 1 -> {
                        vartotojaiLL.remove(vartotojas);
                        System.out.println("\tVartotojas istrintas!");
                    }
                    case 2 -> {
                        vartotojas.setActive(false);
                        System.out.println("\tVartotojas deaktivuotas!");
                    }
                }
                break;
            }
        }
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
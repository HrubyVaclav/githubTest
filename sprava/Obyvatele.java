/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprava;

import enums.EnumKraj;
import enums.EnumPozice;
import generator.Generator;
import gui.ProgObyvatele;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import kolekce.AbstrDoubleList;
import kolekce.ListException;
import util.ObecDecoder;
import util.ObecEncoder;

/**
 *
 * @author hruby
 */
public class Obyvatele implements IObyvatele {

    public static final int POCET_KRAJU = 14;
    private static AbstrDoubleList<Obec>[] pole;
    Consumer<Obec> add = (obec) -> ProgObyvatele.addToList(obec);

    public static Comparator<Obec> obecComparator = new Comparator<Obec>() {

        @Override
        public int compare(Obec o1, Obec o2) {
            String obec1 = o1.getObec().toUpperCase();
            String obec2 = o2.getObec().toUpperCase();

            return obec1.compareTo(obec2);
        }
    };

    @Override
    public int importData(String soubor) {
        generatePole();
        int pocetZaznamu = 0;
        try {
            File file = new File("src/util/" + soubor);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                Scanner scanner = new Scanner(line);
                scanner.useDelimiter(";");
                int cisloKraje = scanner.nextInt();
                scanner.next();
                int psc = scanner.nextInt();
                String nazevObce = scanner.next();
                int pocetMuzu = scanner.nextInt();
                int pocetZen = scanner.nextInt();
                int celkem = scanner.nextInt();
                Obec obec = new Obec(psc, nazevObce, pocetMuzu, pocetZen, celkem);
                pole[cisloKraje - 1].vlozPrvni(obec);
                scanner.close();
                pocetZaznamu++;
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return pocetZaznamu;
    }

    @Override
    public void vlozObec(Obec obec, EnumPozice pozice, EnumKraj kraj) throws ListException {
        int index = kraj.value - 1;
        if (pole == null) {
            generatePole();
        }
        if (index > -1) {
            switch (pozice) {
                case PRVNI:
                    pole[index].vlozPrvni(obec);
                    break;
                case POSLEDNI:
                    pole[index].vlozPosledni(obec);
                    break;
                case NASLEDNIK:
                    pole[index].vlozNaslednika(obec);
                    break;
                case PREDCHUDCE:
                    pole[index].vlozPredchudce(obec);
                    break;
                case AKTUALNI:
                    throw new ListException("Nelze vložit obec na pozici aktuální!");
            }
        }
    }

    @Override
    public Obec zpristupniObec(EnumPozice pozice, EnumKraj Kraj) {
        int index = Kraj.value - 1;
        Obec obec = null;
        if (index > -1) {
            try {
                switch (pozice) {
                    case PRVNI:
                        obec = (Obec) pole[index].zpristupniPrvni();
                        break;
                    case POSLEDNI:
                        obec = (Obec) pole[index].zpristupniPosledni();
                        break;
                    case NASLEDNIK:
                        obec = (Obec) pole[index].zpristupniNaslednika();
                        break;
                    case PREDCHUDCE:
                        obec = (Obec) pole[index].zpristupniPredchudce();
                        break;
                    case AKTUALNI:
                        obec = (Obec) pole[index].zpristupniAktualni();
                        break;
                }
            } catch (ListException ex) {
                Logger.getLogger(Obyvatele.class.getName()).log(Level.SEVERE, null, ex);
            }
            return obec;
        }
        return null;
    }

    @Override
    public Obec odeberObec(EnumPozice pozice, EnumKraj Kraj) throws ListException {
        int index = Kraj.value - 1;
        Obec obec = null;
        if (index > -1) {
            switch (pozice) {
                case PRVNI:
                    obec = (Obec) pole[index].odeberPrvni();
                    break;
                case POSLEDNI:
                    obec = (Obec) pole[index].odeberPosledni();
                    break;
                case NASLEDNIK:
                    obec = (Obec) pole[index].odeberNaslednika();
                    break;
                case PREDCHUDCE:
                    obec = (Obec) pole[index].odeberPredchudce();
                    break;
                case AKTUALNI:
                    obec = (Obec) pole[index].odeberAktualni();
                    break;
            }
            return obec;
        }
        return null;
    }

    @Override
    public float zjistiPrumer(EnumKraj Kraj) {
        float prumer = 0;
        int counter = 0;
        if (Kraj.value == 0) {
            for (int i = 0; i < POCET_KRAJU; i++) {
                Iterator<Obec> it = pole[i].iterator();
                while (it.hasNext()) {
                    prumer += it.next().getCelkem();
                    counter++;
                }
            }
            return prumer / counter;
        } else {
            int index = Kraj.value - 1;
            Iterator<Obec> it = pole[index].iterator();
            while (it.hasNext()) {
                prumer += it.next().getCelkem();
                counter++;
            }
            return prumer / counter;
        }
    }

    @Override
    public void zobrazObce(EnumKraj Kraj) {
        if (pole == null) {
            generatePole();
        }
        if (Kraj.value == 0) {
            for (int i = 0; i < POCET_KRAJU; i++) {
                Iterator<Obec> it = pole[i].iterator();
                while (it.hasNext()) {
                    add.accept(it.next());
                }
            }
        } else {
            int index = Kraj.value - 1;
            Iterator<Obec> it = pole[index].iterator();
            while (it.hasNext()) {
                add.accept(it.next());
            }
        }
    }

    @Override
    public void zobrazObceNadPrumer(EnumKraj Kraj) {
        if (pole == null) {
            generatePole();
        }
        float prumer = zjistiPrumer(Kraj);
        if (Kraj.value == 0) {
            for (int i = 0; i < POCET_KRAJU; i++) {
                Iterator<Obec> it = pole[i].iterator();
                while (it.hasNext()) {
                    Obec obec = it.next();
                    if (obec.getCelkem() > (int) prumer) {
                        add.accept(obec);
                    }
                }
            }
        } else {
            int index = Kraj.value - 1;
            Iterator<Obec> it = pole[index].iterator();
            while (it.hasNext()) {
                Obec obec = it.next();
                if (obec.getCelkem() > (int) prumer) {
                    add.accept(obec);
                }
            }
        }
    }

    @Override
    public void zrus(EnumKraj Kraj) {
        if (pole != null) {
            if (Kraj.value == 0) {
                for (int i = 0; i < POCET_KRAJU; i++) {
                    pole[i].zrus();
                }
                pole = null;
            } else {
                int index = Kraj.value - 1;
                pole[index].zrus();
            }
        }

    }

    public void generujObce(int pocetObci) {
        if (pole == null) {
            generatePole();
        }
        for (int i = 0; i < pocetObci; i++) {
            Obec obec = Generator.generujObec();
            int cisloKraje = Generator.generujCisloKraje();
            pole[cisloKraje].vlozPosledni(obec);
        }
    }

    public void ulozTextSoubor(String soubor) {
        try {
            File file = new File("src/util/" + soubor);
            FileWriter fw = new FileWriter(file);
            for (int i = 0; i < POCET_KRAJU; i++) {
                Iterator<Obec> it = pole[i].iterator();
                while (it.hasNext()) {
                    fw.write(ObecEncoder.encodeObec(it.next(), i));
                }
            }
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Obyvatele.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void nactiTextSoubor(String soubor) throws FileNotFoundException {
        try {
            if (pole == null) {
                generatePole();
            }
            File file = new File("src/util/" + soubor);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] arr;
                arr = line.split(",");
                String nazevKraje = arr[0];
                int cisloKraje = ObecDecoder.getCisloKraje(nazevKraje);
                Obec obec = ObecDecoder.decodeObec(arr);
                pole[cisloKraje].vlozPosledni(obec);
            }
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException();
        }

    }

    public void nactiTestData() {
        Obec obec1 = new Obec(6219, "Vy", 25017, 26040, 51057);
        Obec obec2 = new Obec(6019, "Ha", 300, 200, 500);
        Obec obec3 = new Obec(5732, "Bř", 766, 234, 1000);
        Obec obec4 = new Obec(4152, "Až", 721, 21, 742);
        Obec obec5 = new Obec(6841, "Zi", 1000, 3000, 4000);
        Obec obec6 = new Obec(1212, "Ho", 10, 20, 30);
        Obec obec7 = new Obec(6978, "Os", 212, 212, 424);
        Obec obec8 = new Obec(4201, "Ka", 2500, 1500, 4000);
        Obec obec9 = new Obec(7321, "Se", 999, 1, 1000);
        Obec obec10 = new Obec(9832, "Op", 30000, 21000, 51000);
        try {
            vlozObec(obec1, EnumPozice.PRVNI, EnumKraj.PRAHA);
            vlozObec(obec2, EnumPozice.PRVNI, EnumKraj.PRAHA);
            vlozObec(obec3, EnumPozice.PRVNI, EnumKraj.PRAHA);
            vlozObec(obec4, EnumPozice.PRVNI, EnumKraj.PRAHA);
            vlozObec(obec5, EnumPozice.PRVNI, EnumKraj.PRAHA);
            vlozObec(obec6, EnumPozice.PRVNI, EnumKraj.PRAHA);
            vlozObec(obec7, EnumPozice.PRVNI, EnumKraj.PRAHA);
            vlozObec(obec8, EnumPozice.PRVNI, EnumKraj.PRAHA);
            vlozObec(obec9, EnumPozice.PRVNI, EnumKraj.PRAHA);
            vlozObec(obec10, EnumPozice.PRVNI, EnumKraj.PRAHA);
        } catch (ListException ex) {
            Logger.getLogger(Obyvatele.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generatePole() {
        pole = new AbstrDoubleList[POCET_KRAJU];
        for (int i = 0; i < POCET_KRAJU; i++) {
            pole[i] = new AbstrDoubleList();
        }
    }

    public AbstrDoubleList<Obec>[] getPole() {
        return pole;
    }

    public static List getObce() {
        List<Obec> obce = new ArrayList<>();
        for (int i = 0; i < POCET_KRAJU; i++) {
            Iterator it = pole[i].iterator();
            while (it.hasNext()) {
                obce.add((Obec) it.next());
            }
        }
        Collections.sort(obce, obecComparator);
        return obce;

    }

    public static Obec[] getObceArray() {
        Obec[] obce = new Obec[getPocetObci()];
        int j = 0;
        for (int i = 0; i < POCET_KRAJU; i++) {
            Iterator it = pole[i].iterator();
            while (it.hasNext()) {
                obce[j] = (Obec) it.next();
                j++;
            }
        }
        return obce;
    }

    private static int getPocetObci() {
        int pocet = 0;
        for (int i = 0; i < POCET_KRAJU; i++) {
            Iterator it = pole[i].iterator();
            while (it.hasNext()) {
                it.next();
                pocet++;
            }
        }
        return pocet;

    }

}

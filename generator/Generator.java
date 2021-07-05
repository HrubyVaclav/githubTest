/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

import java.util.Random;
import sprava.Obec;

/**
 *
 * @author hruby
 */
public class Generator {

    private static final Random random = new Random();

    public static Obec generujObec() {
        int psc = generujPsc();
        String nazevObce = generujNazevObce();
        int pocetMuzu = generujPocetMuzu();
        int pocetZen = generujPocetZen();
        int pocetCelkem = pocetMuzu + pocetZen;
        Obec obec = new Obec(psc, nazevObce, pocetMuzu, pocetZen, pocetCelkem);
        return obec;
        
    }

    public static int generujCisloKraje() {
        return random.nextInt(14);
    }

    private static int generujPsc() {
        return random.nextInt(9000) + 1000;
    }

    private static String generujNazevObce() {
        return "Obec " + random.nextInt(100000) + 1;
    }

    private static int generujPocetMuzu() {
        return random.nextInt(100000);
    }

    private static int generujPocetZen() {
        return random.nextInt(100000);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import sprava.Obec;

/**
 *
 * @author hruby
 */
public class ObecDecoder {

    public static Obec decodeObec(String[] arr) {
        int psc = Integer.parseInt(arr[1].trim());
        String nazevObce = arr[2].trim();
        int pocetMuzu = Integer.parseInt(arr[3].trim());
        int pocetZen = Integer.parseInt(arr[4].trim());
        int pocetObyvatel = Integer.parseInt(arr[5].trim());
        Obec obec = new Obec(psc, nazevObce, pocetMuzu, pocetZen, pocetObyvatel);
        return obec;
    }

    public static int getCisloKraje(String nazevKraje) {
        switch (nazevKraje) {
            case "Praha":
                return 0;
            case "Jihočeský kraj":
                return 1;
            case "Jihomoravský kraj":
                return 2;
            case "Karlovarský kraj":
                return 3;
            case "Kraj Vysočina":
                return 4;
            case "Královehradecký kraj":
                return 5;
            case "Liberecký kraj":
                return 6;
            case "Moravskoslezský kraj":
                return 7;
            case "Olomoucký kraj":
                return 8;
            case "Pardubický kraj":
                return 9;
            case "Plzeňský kraj":
                return 10;
            case "Středočeský kraj":
                return 11;
            case "Ústecký kraj":
                return 12;
            case "Zlínský kraj":
                return 13;
            default:
                return -1;
        }
    }

}

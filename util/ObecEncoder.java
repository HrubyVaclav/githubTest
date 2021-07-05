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
public class ObecEncoder {

    public static String encodeObec(Obec obec, int index) {
        String output = "";
        output += getKrajString(index);
        output += ", " + obec.getPsc() + ", " + obec.getObec() + ", " + obec.getPocetMuzu() + ", " + obec.getPocetZen() + ", " + obec.getCelkem() +"\n";
        return output;

    }

    private static String getKrajString(int index) {
        switch (index) {
            case 0:
                return "Praha";
            case 1:
                return "Jihočeský kraj";
            case 2:
                return "Jihomoravský kraj";
            case 3:
                return "Karlovarský kraj";
            case 4:
                return "Kraj Vysočina";
            case 5:
                return "Královehradecký kraj";
            case 6:
                return "Liberecký kraj";
            case 7:
                return "Moravskoslezský kraj";
            case 8:
                return "Olomoucký kraj";
            case 9:
                return "Pardubický kraj";
            case 10:
                return "Plzeňský kraj";
            case 11:
                return "Středočeský kraj";
            case 12:
                return "Ústecký kraj";
            case 13:
                return "Zlínský kraj";
            default:
                return null;
        }
    }

}

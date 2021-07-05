/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

/**
 *
 * @author hruby
 */
public enum EnumPozice {
    PRVNI, POSLEDNI, PREDCHUDCE, NASLEDNIK, AKTUALNI;

    public static Enum[] getPozice() {
        Enum[] pozice = {PRVNI, POSLEDNI, PREDCHUDCE, NASLEDNIK, AKTUALNI};
        return pozice;
    }

}

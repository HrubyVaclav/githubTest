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
public enum EnumKraj {
    VSECHNY(0), PRAHA(1), JIHOCESKY(2), JIHOMORAVSKY(3), KARLOVARSKY(4), VYSOCINA(5), KRALOVEHRADECKY(6), LIBERECKY(7), MORAVSKOSLEZSKY(8), OLOMOUCKY(9), PARDUBICKY(10),
    PLZENSKY(11), STREDOCESKY(12), USTECKY(13), ZLINSKY(14);

    public final int value;

    private EnumKraj(int value) {
        this.value = value;
    }

    public static Enum[] getKraje() {
        Enum[] kraje = {PRAHA, JIHOCESKY, JIHOMORAVSKY, KARLOVARSKY, VYSOCINA, KRALOVEHRADECKY, LIBERECKY, MORAVSKOSLEZSKY, OLOMOUCKY, PARDUBICKY,
            PLZENSKY, STREDOCESKY, USTECKY, ZLINSKY};
        return kraje;
    }

}

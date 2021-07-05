/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import enums.EnumKraj;
import enums.EnumPozice;
import enums.eTypProhl;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import kolekce.AbstrHeap;
import kolekce.ListException;
import sprava.Obec;
import sprava.Obyvatele;

/**
 *
 * @author hruby
 */
public class Main {

    private static Comparator<Obec> komparatorNazev = (o1, o2) -> o1.getObec().compareTo(o2.getObec());
    private static Comparator<Obec> komparatorPocetObyvatel = (o1, o2) -> o1.getCelkem() - (o2.getCelkem());

    public static void main(String[] args) {

        PriorityQueue pq = new PriorityQueue(10);
        pq.add(123);
        Obyvatele obyv = new Obyvatele();
        Obec obec1 = new Obec(123, "B", 1, 2, 121);
        Obec obec2 = new Obec(123, "X", 1, 2, 500);
        Obec obec3 = new Obec(123, "Z", 1, 2, 124);
        Obec obec4 = new Obec(123, "B", 1, 2, 698);
        Obec obec5 = new Obec(123, "J", 1, 2, 315);
        Obec obec6 = new Obec(123, "K", 1, 2, 674);
        Obec obec7 = new Obec(123, "O", 1, 2, 17);
        Obec obec8 = new Obec(123, "P", 1, 2, 89);
        Obec obec9 = new Obec(123, "Q", 1, 2, 156);
        Obec obec10 = new Obec(123, "W", 1, 2, 39);
        Obec obec11 = new Obec(123, "A", 1,2, 10);
        Obec[] aaa = {obec1, obec2, obec3, obec4, obec5, obec6, obec7, obec8, obec9, obec10};

        try {
            obyv.vlozObec(obec1, EnumPozice.PRVNI, EnumKraj.PRAHA);
            obyv.vlozObec(obec2, EnumPozice.POSLEDNI, EnumKraj.PRAHA);
            obyv.vlozObec(obec3, EnumPozice.POSLEDNI, EnumKraj.PRAHA);
            obyv.vlozObec(obec4, EnumPozice.POSLEDNI, EnumKraj.PRAHA);
            obyv.vlozObec(obec5, EnumPozice.POSLEDNI, EnumKraj.PRAHA);
            obyv.vlozObec(obec6, EnumPozice.POSLEDNI, EnumKraj.PRAHA);
            obyv.vlozObec(obec7, EnumPozice.POSLEDNI, EnumKraj.PRAHA);
            obyv.vlozObec(obec8, EnumPozice.POSLEDNI, EnumKraj.PRAHA);
            obyv.vlozObec(obec9, EnumPozice.POSLEDNI, EnumKraj.PRAHA);
            obyv.vlozObec(obec10, EnumPozice.POSLEDNI, EnumKraj.PRAHA);

        } catch (ListException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        AbstrHeap halda = new AbstrHeap(komparatorPocetObyvatel);
        halda.vybuduj(aaa);
        halda.nastavKomparator(komparatorNazev);
        halda.reorganizace();
        halda.vloz(obec11);
        

//        abstrHeaP.vloz(obec1);
//
//        abstrHeaP.vloz(obec7);
        
        //   Obec obec11 = new Obec(123, "C", 1, 2, 99);
        // halda.odeberMax();
        halda.vypis(eTypProhl.DO_SIRKY);
    }

}

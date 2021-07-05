/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprava;

import enums.eTypProhl;
import generator.Generator;
import gui.ProgObyvatele;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import kolekce.AbstrTable;
import kolekce.TableException;

/**
 *
 * @author hruby
 */
public class AgendaKraj implements IAgendaKraj {

    private AbstrTable<String, Obec> strom = new AbstrTable<>();
    private List<Obec> obce;
    boolean rootJeVlozeny = false;
    Consumer<Obec> add = (obec) -> ProgObyvatele.addToList(obec);

    @Override
    public Obec najdi(String key) {
        return strom.najdi(key);
    }

    @Override
    public void vloz(String key, Obec value) throws TableException {
        strom.vloz(key, value);
    }

    @Override
    public Obec odeber(String key) {
        return strom.odeber(key);
    }

    @Override
    public void vybuduj() {
        strom = new AbstrTable<>();
        getObce();
        vlozObce();

    }

    @Override
    public Iterator vytvorIterator(eTypProhl typ) {
        return strom.vytvorIterator(typ);
    }

    @Override
    public void generuj(int pocetObci) {
        for (int i = 0; i < pocetObci; i++) {
            Obec obec = Generator.generujObec();
            try {
                strom.vloz(obec.getObec(), obec);
            } catch (TableException ex) {
                Logger.getLogger(AgendaKraj.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void getObce() {
        obce = Obyvatele.getObce();
    }

    private boolean sortedArrayToBST(List<Obec> obce, int start, int end) {

        try {
            if (start > end) {
                return false;
            }
            int mid = (start + end) / 2;
            if (rootJeVlozeny && obce.get(mid) == obce.get((obce.size() - 1) / 2)) {
                return false;
            }
            rootJeVlozeny = true;
            strom.vloz(obce.get(mid).getObec(), obce.get(mid));
            sortedArrayToBST(obce, start, mid - 1);
            sortedArrayToBST(obce, mid + 1, end);
            return true;
        } catch (TableException ex) {
            Logger.getLogger(AgendaKraj.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    public void zobrazInOrder() {
        Iterator it = vytvorIterator(eTypProhl.DO_HLOUBKY);
        while (it.hasNext()) {
            add.accept((Obec) it.next());
        }
    }

    public void zobrazDoSirky() {
        Iterator it = vytvorIterator(eTypProhl.DO_SIRKY);
        while (it.hasNext()) {
            add.accept((Obec) it.next());
        }
    }

    private void vlozObce() {
        rootJeVlozeny = false;
        while (sortedArrayToBST(obce, 0, obce.size() - 1));

    }

}

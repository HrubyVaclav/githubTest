    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kolekce;

import java.util.Iterator;
import java.util.Objects;

/**
 *
 * @author hruby
 */
public class AbstrDoubleList<T> implements IAbstrDoubleList<T> {

    private class Prvek {

        Prvek dalsi;
        Prvek predchozi;
        T data;

        public Prvek(T data) {
            this.data = data;
        }

    }

    private Prvek prvni;
    private Prvek aktualni;
    private Prvek posledni;
    private int size = 0;

    @Override
    public void zrus() {
        prvni = null;
        aktualni = null;
        posledni = null;
    }

    @Override
    public boolean jePrazdny() {
        return prvni == null;
    }

    @Override
    public void vlozPrvni(T data) {
        Objects.requireNonNull(data);
        if (jePrazdny()) { // Pokud vkládám první prvek
            prvni = new Prvek(data);
            prvni.dalsi = prvni;
            prvni.predchozi = prvni;
            posledni = prvni;
            aktualni = prvni;
            size++;
        } else {
            Prvek novy = new Prvek(data);
            novy.dalsi = prvni;
            novy.predchozi = posledni;
            prvni.predchozi = novy;
            prvni = novy;
            posledni.dalsi = novy;
            size++;
        }
    }

    @Override
    public void vlozPosledni(T data) {
        Objects.requireNonNull(data);
        if (jePrazdny()) { // Pokud vkládám první prvek
            posledni = new Prvek(data);
            prvni = posledni;
            prvni.dalsi = prvni;
            prvni.predchozi = prvni;
            aktualni = posledni;
            size++;
            return;
        }
        Prvek novy = new Prvek(data);
        novy.predchozi = posledni;
        novy.dalsi = prvni;
        posledni.dalsi = novy;
        posledni = novy;
        prvni.predchozi = posledni;
        size++;
    }

    @Override
    public void vlozNaslednika(T data) throws ListException {
        Objects.requireNonNull(data);
        if (jePrazdny()) {
            throw new ListException("List je prázdný");
        }
        Prvek temp = aktualni.dalsi;
        Prvek novy = new Prvek(data);
        aktualni.dalsi = novy;
        novy.predchozi = aktualni;
        aktualni.dalsi.dalsi = temp;
        size++;
        if (aktualni == posledni) {
            posledni = novy;
        }

    }

    @Override
    public void vlozPredchudce(T data) throws ListException {
        Objects.requireNonNull(data);
        if (jePrazdny()) {
            throw new ListException("List je prázdný");
        }
        Prvek temp = aktualni.predchozi;
        Prvek novy = new Prvek(data);
        novy.dalsi = aktualni;
        aktualni.predchozi = novy;
        temp.dalsi = novy;
        novy.predchozi = temp;
        size++;
        if (aktualni == prvni) {
            prvni = novy;
        }

    }

    @Override
    public T zpristupniAktualni() throws ListException {
        if (jePrazdny()) {
            throw new ListException("List je prázdný");
        }
        return aktualni.data;
    }

    @Override
    public T zpristupniPrvni() throws ListException {
        if (jePrazdny()) {
            throw new ListException("List je prázdný");
        }
        aktualni = prvni;
        return prvni.data;
    }

    @Override
    public T zpristupniPosledni() throws ListException {
        if (jePrazdny()) {
            throw new ListException("List je prázdný");
        }
        aktualni = posledni;
        return posledni.data;
    }

    @Override
    public T zpristupniNaslednika() throws ListException {
        if (jePrazdny()) {
            throw new ListException("List je prázdný");
        }
        aktualni = aktualni.dalsi;
        return aktualni.data;
    }

    @Override
    public T zpristupniPredchudce() throws ListException {
        if (jePrazdny()) {
            throw new ListException("List je prázdný");
        }
        aktualni = aktualni.predchozi;
        return aktualni.data;
    }

    @Override
    public T odeberAktualni() throws ListException {
        if (jePrazdny()) {
            throw new ListException("List je prázdný");
        }
        Prvek mazanyPrvek = aktualni;
        if (aktualni.dalsi != aktualni) {
            if (aktualni == prvni) {
                odeberPrvni();
            } else if (aktualni == posledni) {
                odeberPosledni();
            } else {
                aktualni.predchozi.dalsi = aktualni.dalsi;
                aktualni.dalsi.predchozi = aktualni.predchozi;
                aktualni = prvni;
            }
        } else { // aktualni.dalsi by se rovnal aktuálnímu prvku -> v seznamu je jen jeden prvek -> seznam se zruší
            zrus();
        }
        size--;
        return mazanyPrvek.data;
    }

    @Override
    public T odeberPrvni() throws ListException {
        if (jePrazdny()) {
            throw new ListException("List je prázdný");
        }
        if (prvni.dalsi == prvni) { // Pokud je v seznamu pouze jeden prvek
            Prvek mazanyPrvek = prvni;
            zrus();
            size--;
            return mazanyPrvek.data;
        } else {
            if (prvni == aktualni) {
                aktualni = prvni.dalsi;
            }
        }
        Prvek mazanyPrvek = prvni;
        prvni = prvni.dalsi;
        prvni.predchozi = posledni;
        posledni.dalsi = prvni;
        size--;
        return mazanyPrvek.data;

    }

    @Override
    public T odeberPosledni() throws ListException {
        if (jePrazdny()) {
            throw new ListException("List je prázný");
        }
        if (posledni.dalsi == posledni) { // Pokud je v seznamu pouze jeden prvek
            Prvek mazanyPrvek = posledni;
            zrus();
            size--;
            return mazanyPrvek.data;
        } else {
            if (posledni == aktualni) { // Pokud je aktuální prvek zároveň poslední (Odstranil by se i aktuální)
                aktualni = prvni; // Aktuální se nastaví na první
            }
            Prvek mazanyPrvek = posledni;
            posledni = posledni.predchozi;
            posledni.dalsi = prvni;
            prvni.predchozi = posledni;
            size--;
            return mazanyPrvek.data;
        }
    }

    @Override
    public T odeberNaslednika() throws ListException {
        if (jePrazdny()) {
            throw new ListException("List je prázdný");
        }
        if (aktualni.dalsi == aktualni) { // Pokud je v seznamu pouze jeden prvek
            Prvek mazanyPrvek = aktualni.dalsi;
            zrus();
            size--;
            return mazanyPrvek.data;
        } else {
            if (aktualni.dalsi == prvni) {
                return odeberPrvni();
            } else if (aktualni.dalsi == posledni) {
                return odeberPosledni();
            } else {
                Prvek mazanyPrvek = aktualni.dalsi;
                aktualni.dalsi = mazanyPrvek.dalsi;
                mazanyPrvek.dalsi.predchozi = aktualni;
                size--;
                return mazanyPrvek.data;
            }
        }

    }

    @Override
    public T odeberPredchudce() throws ListException {
        if (jePrazdny()) {
            throw new ListException("List je prázdný");
        }
        if (aktualni.predchozi == aktualni) { // Pokud je v seznamu pouze jeden prvek
            Prvek mazanyPrvek = aktualni.predchozi;
            zrus();
            size--;
            return mazanyPrvek.data;
        } else {
            if (aktualni.predchozi == prvni) {
                return odeberPrvni();
            } else if (aktualni.predchozi == posledni) {
                return odeberPosledni();
            } else {
                Prvek mazanyPrvek = aktualni.predchozi;
                aktualni.predchozi = mazanyPrvek.predchozi;
                mazanyPrvek.predchozi.dalsi = aktualni;
                size--;
                return mazanyPrvek.data;
            }
        }
    }
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Prvek pom = prvni;
            private int pocetPrvku = size;

            @Override
            public boolean hasNext() {
                if (jePrazdny()) {
                    return false;
                }
                if (pocetPrvku == size) { // Imunita pro úplně první prvek
                    pocetPrvku--;
                    return true;
                } else {
                    return pom != prvni; // Klasická podmínka
                }
            }

            @Override
            public T next() {
                Prvek ppred = pom; // Uložení předchozího prvku, jehož data se returnují
                pom = pom.dalsi;
                return ppred.data;
            }
        };
    }

}

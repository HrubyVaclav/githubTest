/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kolekce;

import enums.EnumPrioritaTyp;
import enums.eTypProhl;
import gui.ProgObyvatele;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import sprava.Obec;

/**
 *
 * @author hruby
 */
public class AbstrHeap<E> implements IAbstrHeap<E> {

    Prvek[] halda;
    Comparator comp;
    int pocetPrvku = 0;
    Consumer<E> add = (E) -> ProgObyvatele.addToList((Obec) E);

    public AbstrHeap(Comparator<E> komparatorPocetObyvatel) {
        comp = komparatorPocetObyvatel;
    }

    private class Prvek<E> {

        E data;

        public Prvek(E data) {
            this.data = data;
        }

    }

    @Override
    public void vybuduj(E[] pole) {
        halda = new Prvek[pole.length];
        pocetPrvku = pole.length;

        for (int i = 0; i < pole.length; i++) {
            halda[i] = new Prvek(pole[i]);
        }

        for (int i = (halda.length / 2) - 1; i >= 0; i--) {
            makeHeap(i);
        }

    }

    private void makeHeap(int i) {

        int aktualni = i;
        int leftIndex = 2 * i + 1;
        int rightIndex = 2 * i + 2;

        if (leftIndex < halda.length && comp.compare(halda[leftIndex].data, halda[aktualni].data) < 0) {
            aktualni = leftIndex;
        }
        if (rightIndex < halda.length && comp.compare(halda[rightIndex].data, halda[aktualni].data) < 0) {
            aktualni = rightIndex;
        }

        if (aktualni != i) {
            Prvek temp = halda[i];
            halda[i] = halda[aktualni];
            halda[aktualni] = temp;

            makeHeap(aktualni);
        }
    }

    @Override
    public void reorganizace() {
        for (int i = (halda.length / 2) - 1; i >= 0; i--) {
            makeHeap(i);
        }
    }

    @Override
    public void zrus() {
        halda = null;
    }

    @Override
    public boolean jePrazdny() {
        return halda == null || halda.length == 0;
    }

    @Override
    public E odeberMax() {
        if (jePrazdny()) {
            return null;
        }
        Prvek odebiranyPrvek = halda[0];
        if (halda[1] == null) {
            zrus();
            return (E) odebiranyPrvek.data;
        }
        boolean prvekRemoved = false;
        for (int i = 0; i < halda.length; i++) {
            if (halda[i] == null) {
                halda[0] = halda[i - 1];
                halda[i - 1] = null;
                prvekRemoved = true;
                break;
            }
        }
        if (!prvekRemoved) {
            halda[0] = halda[halda.length - 1];
            halda[halda.length - 1] = null;
        }
        pocetPrvku--;
        if (pocetPrvku < halda.length / 2) { // Zmenšení pole na polovinu aby nebylo tolik null prvků
            Prvek[] novaHalda = new Prvek[halda.length / 2];
            System.arraycopy(halda, 0, novaHalda, 0, novaHalda.length);
            halda = novaHalda;
        }
        int indexPrvku = 0;
        while (indexPrvku < halda.length) {

            if (indexPrvku * 2 + 1 >= halda.length || indexPrvku * 2 + 2 >= halda.length) { // Out of bound array exception fix
                break;
            }
            if (halda[indexPrvku * 2 + 1] == null && halda[indexPrvku * 2 + 2] == null) { // Both null exception fix
                break;
            }
            int indexMensihoPotomka = 0;

            if (halda[indexPrvku * 2 + 2] == null) { // pravý potomek je roven null a levý je nenullový
                indexMensihoPotomka = indexPrvku * 2 + 1;
            } else if (comp.compare(halda[indexPrvku * 2 + 1].data, halda[indexPrvku * 2 + 2].data) < 0) { // nalezení menšího prvku - menší prvek je ten levý
                indexMensihoPotomka = indexPrvku * 2 + 1;
            } else { // menší prvek je pravý potomek
                indexMensihoPotomka = indexPrvku * 2 + 2;
            }

            if (comp.compare(halda[indexPrvku].data, halda[indexMensihoPotomka].data) > 0) { // má prvek nižší prioritu než potomek s vyšší prioritou?
                Prvek temp = halda[indexPrvku];
                halda[indexPrvku] = halda[indexMensihoPotomka];
                halda[indexMensihoPotomka] = temp;
            } else {
                break;
            }

            indexPrvku = indexMensihoPotomka; // bublání

        }

        return (E) odebiranyPrvek.data;
    }

    @Override
    public E zpristupniMax() {
        if (jePrazdny()) {
            return null;
        }
        return (E) halda[0].data;
    }

    @Override
    public void vloz(E data) {
        Objects.requireNonNull(data);
        int indexPrvku = 0;
        if (halda == null) {
            halda = new Prvek[10];
            halda[0] = new Prvek(data);
        } else {
            if (halda[halda.length - 1] != null) {
                Prvek[] novaHalda = new Prvek[halda.length * 2];
                System.arraycopy(halda, 0, novaHalda, 0, halda.length);
                novaHalda[halda.length] = new Prvek(data);
                indexPrvku = halda.length;
                halda = novaHalda;
            } else {
                for (int i = 0; i < halda.length; i++) {
                    if (halda[i] == null) {
                        halda[i] = new Prvek(data);
                        indexPrvku = i;
                        break;
                    }
                }
            }
            pocetPrvku++;

            while (indexPrvku > 0) {
                int indexRodice = 0;
                if (indexPrvku % 2 == 0) {
                    indexRodice = (indexPrvku / 2) - 1;
                } else {
                    indexRodice = indexPrvku / 2;
                }
                if (comp.compare(halda[indexPrvku].data, halda[indexRodice].data) < 0) {
                    Prvek temp = halda[indexPrvku];
                    halda[indexPrvku] = halda[indexRodice];
                    halda[indexRodice] = temp;
                } else {
                    break;
                }

                indexPrvku = indexRodice;
            }
        }
    }

    @Override
    public void vypis(eTypProhl typ) {
        Iterator<E> it;

        switch (typ) {

            case DO_SIRKY:
                it = iteratorDoSirky();
                while (it.hasNext()) {
                    add.accept(it.next());
                }
                break;

            case DO_HLOUBKY:
                it = iteratorDoHloubky();
                while (it.hasNext()) {
                    add.accept(it.next());
                }

                break;
        }
    }

    public void nastavKomparator(Comparator<? super E> comparator) {
        Objects.requireNonNull(comparator);
        this.comp = comparator;
    }

    private Iterator<E> iteratorDoHloubky() {
        Iterator<E> iterator = new Iterator<E>() {
            AbstrLIFO<Integer> zasobnik = new AbstrLIFO<>();
            boolean flag = true;

            @Override
            public boolean hasNext() {
                if (jePrazdny()) {
                    return false;
                }
                if (flag) {
                    flag = false;
                    zasobnik.vloz(0);
                    return true;
                }
                return !(zasobnik.jePrazdny());

            }

            @Override
            public E next() {
                int pom = -1;
                try {
                    pom = zasobnik.odeber();
                    if (2 * pom + 2 < pocetPrvku) {
                        zasobnik.vloz(2 * pom + 2);
                    }
                    if (2 * pom + 1 < pocetPrvku) {
                        zasobnik.vloz(2 * pom + 1);
                    }
                } catch (ListException ex) {
                    Logger.getLogger(AbstrHeap.class.getName()).log(Level.SEVERE, null, ex);
                }
                return (E) halda[pom].data;

            }
        };
        return iterator;

    }

    private Iterator<E> iteratorDoSirky() {
        Iterator<E> iterator = new Iterator<E>() {
            AbstrFIFO<Integer> fronta = new AbstrFIFO<>();
            boolean flag = true;

            @Override
            public boolean hasNext() {
                if (jePrazdny()) {
                    return false;
                }
                if (flag) {
                    flag = false;
                    fronta.vloz(0);
                    return true;
                }
                return !(fronta.jePrazdny());

            }

            @Override
            public E next() {
                try {
                    int pom = fronta.odeber();
                    if (2 * pom + 1 < pocetPrvku) {
                        fronta.vloz(2 * pom + 1);
                    }
                    if (2 * pom + 2 < pocetPrvku) {
                        fronta.vloz(2 * pom + 2);
                    }
                    return (E) halda[pom].data;

                } catch (ListException ex) {
                    Logger.getLogger(AbstrTable.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }
        };
        return iterator;

    }

}

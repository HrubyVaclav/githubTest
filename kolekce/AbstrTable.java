/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kolekce;

import enums.eTypProhl;
import java.util.Iterator;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import sprava.Obec;

/**
 *
 * @author hruby
 */
public class AbstrTable<K extends Comparable<K>, V> implements IAbstrTable<K, V> {

    private class Prvek {

        Prvek leftChild;
        Prvek rightChild;
        Prvek parent;
        V data;
        K key;

        public Prvek(V data, K key) {
            this.data = data;
            this.key = key;
            this.leftChild = null;
            this.rightChild = null;
            this.parent = null;
        }

    }

    Prvek root = null;

    @Override
    public void zrus() {
        root = null;
    }

    @Override
    public boolean jePrazdny() {
        return root == null;
    }

    @Override
    public V najdi(K key) {
        Objects.requireNonNull(key);
        if (jePrazdny()) {
            return null;
        }
        Prvek current = root;
        while (true) {
            if (key.compareTo(current.key) > 0) {
                if (current.rightChild == null) {
                    return null;
                }
                current = current.rightChild;
            } else if (key.compareTo(current.key) == 0) {
                return current.data;
            } else {
                if (current.leftChild == null) {
                    return null;
                }
                current = current.leftChild;
            }

        }
    }

    @Override
    public void vloz(K key, V value) throws TableException {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        if (jePrazdny()) {
            root = new Prvek(value, key);
        } else {
            Prvek current = root;
            while (true) {
                if (key.compareTo(current.key) > 0) {
                    if (current.rightChild == null) {
                        Prvek novy = new Prvek(value, key);
                        novy.parent = current;
                        current.rightChild = novy;
                        break;
                    } else {
                        current = current.rightChild;
                    }
                } else if (key.compareTo(current.key) == 0) {
                    throw new TableException("Kl???? u?? je v tabulce p??itomen!");
                } else {
                    if (current.leftChild == null) {
                        Prvek novy = new Prvek(value, key);
                        novy.parent = current;
                        current.leftChild = novy;
                        break;
                    } else {
                        current = current.leftChild;
                    }
                }
            }
        }
    }

    @Override
    public V odeber(K key) {
        Prvek odebiranyPrvek = najdiPrvek(key);
        if (odebiranyPrvek == null) {
            return null;
        }
        if (jeList(odebiranyPrvek)) { // Odeb??ran?? prvek je listem
            odeberList(odebiranyPrvek);

        } else if (odebiranyPrvek.leftChild != null && odebiranyPrvek.rightChild != null) { // Odeb??ran?? prvek m?? 2 potomky
            odeberPrvekSDvemaPotomky(odebiranyPrvek);

        } else {
            odeberPrvekSJednimPotomkem(odebiranyPrvek);
        }
        return odebiranyPrvek.data;
    }

    private void odeberPrvekSJednimPotomkem(Prvek odebiranyPrvek) {
        // Odeb??ran?? prvek m?? pouze 1 potomka
        if (odebiranyPrvek.leftChild == null) { // Odeb??ran?? prvek m?? pouze prav??ho potomka
            if (odebiranyPrvek == root) {
                root = odebiranyPrvek.rightChild;
                root.parent = null;
            } else {
                odebiranyPrvek.rightChild.parent = odebiranyPrvek.parent;
                if (odebiranyPrvek.parent.leftChild == odebiranyPrvek) { // Kontrola, ??e lev?? potomek otce je roven odeb??ran??mu prvku
                    odebiranyPrvek.parent.leftChild = odebiranyPrvek.rightChild;
                } else {
                    odebiranyPrvek.parent.rightChild = odebiranyPrvek.rightChild;
                }
            }
        } else { // Odeb??ran?? prvek m?? pouze lev??ho potomka
            if (odebiranyPrvek == root) {
                root = odebiranyPrvek.leftChild;
                root.parent = null;
            } else {
                odebiranyPrvek.leftChild.parent = odebiranyPrvek.parent;
                if (odebiranyPrvek.parent.leftChild == odebiranyPrvek) { // Kontrola, ??e lev?? potomek otce je roven odeb??ran??mu prvku
                    odebiranyPrvek.parent.leftChild = odebiranyPrvek.leftChild;
                } else {
                    odebiranyPrvek.parent.rightChild = odebiranyPrvek.leftChild;
                }
            }
        }
    }

    private void odeberPrvekSDvemaPotomky(Prvek odebiranyPrvek) {
        Prvek nahradniPrvek = odebiranyPrvek.rightChild;
        boolean jeNejlevejsi = jeList(nahradniPrvek);
        while (!jeNejlevejsi) { // nalezen?? prvku nejv??ce vlevo v prav??m podstromu
            if (nahradniPrvek.leftChild != null) {
                nahradniPrvek = nahradniPrvek.leftChild;
            } else {
                jeNejlevejsi = true;
            }
        }
        if (odebiranyPrvek.rightChild == nahradniPrvek) { // pokud je nejlev??j???? prvek z prav??ho stromu p????m?? potomek odeb??ran??ho prvku
            if (nahradniPrvek.rightChild != null) { // p??epojen?? prav??ho potomka n??hradn??ho prvku na prav??ho potomka odeb??ran??ho prvku
                odebiranyPrvek.rightChild = nahradniPrvek.rightChild;
                odebiranyPrvek.rightChild.parent = odebiranyPrvek;
                nahradniPrvek.rightChild = null;
            } else { // n??hradn?? prvek nem?? ????dn??ho dal????ho prav??ho potomka
                odebiranyPrvek.rightChild = null;
            }

        } else { // prvek nejv??ce vlevo v prav??m podstromu nen?? p????m??m potomkem odeb??ran??ho prvku
            if (nahradniPrvek.rightChild != null) { // tento prvek m?? je??t?? prav??ho potomka
                nahradniPrvek.parent.leftChild = nahradniPrvek.rightChild;
                nahradniPrvek.rightChild.parent = nahradniPrvek.parent;
            } else { // tento prvek nem?? ????dn??ho prav??ho potomka
                nahradniPrvek.parent.leftChild = null;
                nahradniPrvek.parent = null;
            }

        }

        switchPrvky(odebiranyPrvek, nahradniPrvek);
    }

    private void odeberList(Prvek odebiranyPrvek) {
        if (odebiranyPrvek == root) {
            zrus();
        } else {
            if (odebiranyPrvek.parent.leftChild == odebiranyPrvek) { // Kontrola, ??e lev?? potomek otce je roven odeb??ran??mu prvku
                odebiranyPrvek.parent.leftChild = null;
            } else {
                odebiranyPrvek.parent.rightChild = null;
            }
        }
    }


    @Override
    public Iterator vytvorIterator(eTypProhl typ) {
        switch (typ) {
            case DO_HLOUBKY:
                return new Iterator<V>() {
                    AbstrLIFO<Prvek> zasobnik = new AbstrLIFO<>();
                    Prvek pom = null;
                    boolean flag = true;

                    @Override
                    public boolean hasNext() {
                        if (root == null) {
                            return false;
                        }
                        if (flag) {
                            pom = root;
                            flag = false;
                            return true;
                        }
                        return !(zasobnik.jePrazdny() && pom == null);
                    }

                    @Override
                    public V next() {
                        while (pom != null) {
                            zasobnik.vloz(pom);
                            pom = pom.leftChild;
                        }
                        try {
                            Prvek temp = zasobnik.odeber();
                            pom = temp.rightChild;
                            return temp.data;

                        } catch (ListException ex) {
                            Logger.getLogger(AbstrTable.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }

                        return null;
                    }
                };
            case DO_SIRKY:
                return new Iterator() {
                    AbstrFIFO<Prvek> fronta = new AbstrFIFO<>();
                    boolean flag = true;

                    @Override
                    public boolean hasNext() {
                        if (root == null) {
                            return false;
                        }
                        if (flag) {
                            fronta.vloz(root);
                            flag = false;
                        }
                        return !fronta.jePrazdny();
                    }

                    @Override
                    public V next() {
                        try {
                            Prvek odebiranyPrvek = fronta.odeber();
                            if (odebiranyPrvek.leftChild != null) {
                                fronta.vloz(odebiranyPrvek.leftChild);
                            }
                            if (odebiranyPrvek.rightChild != null) {
                                fronta.vloz(odebiranyPrvek.rightChild);
                            }
                            return odebiranyPrvek.data;

                        } catch (ListException ex) {
                            Logger.getLogger(AbstrTable.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                        return null;

                    }
                };

        }
        return null;
    }

    private Prvek najdiPrvek(K key) {
        Objects.requireNonNull(key);
        if (jePrazdny()) {
            return null;
        }
        Prvek current = root;
        while (true) {
            if (key.compareTo(current.key) > 0) {
                if (current.rightChild == null) {
                    return null;
                }
                current = current.rightChild;
            } else if (key.compareTo(current.key) == 0) {
                return current;
            } else {
                if (current.leftChild == null) {
                    return null;
                }
                current = current.leftChild;
            }

        }
    }

    private boolean jeList(Prvek prvek) {
        return (prvek.leftChild == null && prvek.rightChild == null);
    }

    private void switchPrvky(Prvek odebiranyPrvek, Prvek nahradniPrvek) {
        if (odebiranyPrvek != root) { // pokud nen?? odeb??ran?? prvek ko??en, tak zm??n??m potomky rodi??e odeb??ran??ho prvku
            if (odebiranyPrvek.parent.leftChild == odebiranyPrvek) { // odeb??ran?? prvek je lev?? potomek
                odebiranyPrvek.parent.leftChild = nahradniPrvek;
            } else { // prav?? potomek, m????e b??t jen jeden z nich
                odebiranyPrvek.parent.rightChild = nahradniPrvek;
            }
            /*
            Prohozen?? rodi???? obou prvk??
             */
            nahradniPrvek.parent = odebiranyPrvek.parent;
            odebiranyPrvek.parent = null;
        } else {
            nahradniPrvek.parent = null;
        }
        /*
        Z??m??na potomk?? n??hradn??ho prvku za potomky odeb??ran??ho prvku
         */
        nahradniPrvek.leftChild = odebiranyPrvek.leftChild;
        nahradniPrvek.rightChild = odebiranyPrvek.rightChild;
        if (odebiranyPrvek == root) {
            root = nahradniPrvek;
        }
        odebiranyPrvek = null;

    }

}

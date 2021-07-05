/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kolekce;

import enums.eTypProhl;
import java.util.Iterator;

/**
 *
 * @author hruby
 */
public interface IAbstrTable<K extends Comparable<K>, V> {

    void zrus(); // zrušení celé tabulky

    boolean jePrazdny(); // test prázdnosti tabulky

    V najdi(K key); // vyhledá prvek dle klíče

    void vloz(K key, V value) throws TableException; // vloží prvek do tabulky

    V odeber(K key); // odebere prvek dle klíče z tabulky

    Iterator vytvorIterator(eTypProhl typ);
    /*vytvoří iterátor, který umožňuje 
      procházení stromu do šířky/hloubky (in-order)
     */

}

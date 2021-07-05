/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kolekce;

import java.util.Iterator;

/**
 *
 * @author hruby
 */
public class AbstrFIFO<T> {

    private AbstrDoubleList<T> linkedList;

    public AbstrFIFO() {
        this.linkedList = new AbstrDoubleList();
    }

    void zrus() {
        linkedList.zrus();
    }

    boolean jePrazdny() {
        return linkedList.jePrazdny();
    }

    void vloz(T data) {
        linkedList.vlozPrvni(data);
    }

    T odeber() throws ListException {
        return linkedList.odeberPosledni();
    }

    Iterator vytvorIterator() {
        return linkedList.iterator();
    }

}

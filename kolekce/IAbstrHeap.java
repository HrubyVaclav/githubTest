/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kolekce;

import enums.EnumPrioritaTyp;
import enums.eTypProhl;
import sprava.Obec;

/**
 *
 * @author hruby
 */
public interface IAbstrHeap<E> {
    
    
    void vybuduj(E[] pole);
    
    void reorganizace();
    
    void zrus();
    
    boolean jePrazdny();
    
    void vloz(E data);
    
    E odeberMax();
    
    E zpristupniMax();
    
    void vypis(eTypProhl typ);
    
    
    
}

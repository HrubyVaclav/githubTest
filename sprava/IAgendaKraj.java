/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprava;

import enums.eTypProhl;
import java.util.Iterator;
import kolekce.TableException;

/**
 *
 * @author hruby
 */
public interface IAgendaKraj {

    Obec najdi(String key);

    void vloz(String key, Obec value) throws TableException;

    Obec odeber(String key);

    void vybuduj();

    Iterator vytvorIterator(eTypProhl typ);

    void generuj(int pocetObci);

}

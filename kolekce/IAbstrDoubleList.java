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
public interface IAbstrDoubleList<T> extends Iterable<T> {

    void zrus(); // zrušení celého seznamu,

    boolean jePrazdny(); // test naplněnosti seznamu,

    void vlozPrvni(T data); // vložení prvku do seznamu na první místo

    void vlozPosledni(T data); // vložení prvku do seznamu na poslední místo

    void vlozNaslednika(T data) throws ListException; // vložení prvku do seznamu jakožto následníka aktuálního prvku

    void vlozPredchudce(T data) throws ListException; // vložení prvku do seznamu jakožto předchůdce aktuálního prvku

    T zpristupniAktualni() throws ListException; // zpřístupnění aktuálního prvku seznamu

    T zpristupniPrvni() throws ListException; // zpřístupnění prvního prvku seznamu,

    T zpristupniPosledni() throws ListException; // zpřístupnění posledního prvku seznamu,

    T zpristupniNaslednika() throws ListException; // zpřístupnění následníka aktuálního prvku,

    T zpristupniPredchudce() throws ListException; // zpřístupnění předchůdce aktuálního prvku, Pozn. Operace typu zpřístupni, přenastavují pozici aktuálního prvku

    T odeberAktualni() throws ListException; // odebrání (vyjmutí) aktuálního prvku ze seznamu poté je aktuální prvek nastaven na první prvek

    T odeberPrvni() throws ListException; // odebrání prvního prvku ze seznamu,

    T odeberPosledni() throws ListException; // odebrání posledního prvku ze seznamu,

    T odeberNaslednika() throws ListException; // odebrání následníka aktuálního prvku ze seznamu,

    T odeberPredchudce() throws ListException; // odebrání předchůdce aktuálního prvku ze seznamu,

    Iterator<T> iterator(); // vytvoří iterátor (dle rozhraní Iterable)

}

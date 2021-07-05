/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprava;

import enums.EnumKraj;
import enums.EnumPozice;
import kolekce.ListException;

/**
 *
 * @author hruby
 */
public interface IObyvatele {

    int importData(String soubor);

    /**
     * provede import dat z datového souboru kraje.csv, kde číslo kraje odpovídá
     * indexu pole-1. Návratová hodnota přestavuje počet úspěšně načtených
     * záznamů.
     */
    void vlozObec(Obec obec, EnumPozice pozice, EnumKraj kraj) throws ListException;

    /**
     * vloží novou obec do seznamu obcí na příslušnou pozici (první, poslední,
     * předchůdce, následník), v odpovídajícím kraji
     */
    Obec zpristupniObec(EnumPozice pozice, EnumKraj Kraj);

    /**
     * zpřístupní obec z požadované pozice (první, poslední, předchůdce,
     * následník, aktuální), v odpovídajícím kraji Obec odeberObec(enumPozice
     * pozice, enumKraj Kraj) - odebere obec z požadované pozice (první,
     * poslední, předchůdce, následník, aktuální), v odpovídajícím kraji
     */
    Obec odeberObec(EnumPozice pozice, EnumKraj Kraj) throws ListException;

    /**
     * odebere obec z požadované pozice (první, poslední, předchůdce, následník,
     * aktuální), v odpovídajícím kraji
     */
    float zjistiPrumer(EnumKraj Kraj);

    /**
     * zjistí průměrný počet obyvatel v kraji, pokud je hodnota kraje rovna
     * nule, pak je průměr spočítán pro všechny kraje.
     */
    void zobrazObce(EnumKraj Kraj);

    /**
     * pomocí iterátoru provede výpis obcí v daném kraji, pokud je hodnota kraje
     * rovna nule, pak jsou vypsány všechny kraje.
     */
    void zobrazObceNadPrumer(EnumKraj Kraj);

    /**
     * pomocí iterátoru provede výpis obcí, které mají v daném kraji nadprůměrný
     * počet obyvatel. Pokud je hodnota kraje rovna nule, pak je průměr spočítán
     * pro všechny kraje.
     */
    void zrus(EnumKraj Kraj);
    /**
     * zruší všechny obce v kraji. Pokud je hodnota kraje rovna nule, pak zruší
     * všechny obce.
     */
}

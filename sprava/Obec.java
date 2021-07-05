/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprava;

/**
 *
 * @author hruby
 */
public class Obec {

    private int psc;
    private String obec;
    private int pocetMuzu;
    private int pocetZen;
    private int celkem;

    public Obec() {

    }

    public Obec(int psc, String obec, int pocetMuzu, int pocetZen, int celkem) {
        this.psc = psc;
        this.obec = obec;
        this.pocetMuzu = pocetMuzu;
        this.pocetZen = pocetZen;
        this.celkem = celkem;
    }

    public int getPsc() {
        return psc;
    }

    public String getObec() {
        return obec;
    }

    public int getPocetMuzu() {
        return pocetMuzu;
    }

    public int getPocetZen() {
        return pocetZen;
    }

    public int getCelkem() {
        return celkem;
    }

    public void setPsc(int psc) {
        this.psc = psc;
    }

    public void setObec(String obec) {
        this.obec = obec;
    }

    public void setPocetMuzu(int pocetMuzu) {
        this.pocetMuzu = pocetMuzu;
    }

    public void setPocetZen(int pocetZen) {
        this.pocetZen = pocetZen;
    }

    public void setCelkem(int celkem) {
        this.celkem = celkem;
    }

    @Override
    public String toString() {
        return "Název obce: " + obec + ", psč=" + psc + ", počet mužů=" + pocetMuzu + ", počet žen=" + pocetZen + ", celkem obyvatel=" + celkem;
    }

}

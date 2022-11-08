package it.fi.meucci;

import java.util.ArrayList;
//creo una classe biglietto in cui ci vado a fare un array di tipo biglietto
public class Messaggio {
    ArrayList<Biglietto> biglietti = new ArrayList<>();

    public Messaggio(ArrayList<Biglietto> biglietti) {
        this.biglietti = biglietti;
    }

    public Messaggio() {
    }

    public ArrayList<Biglietto> getBiglietti() {
        return biglietti;
    }

    public void setBiglietti(ArrayList<Biglietto> biglietti) {
        this.biglietti = biglietti;
    }
    

}


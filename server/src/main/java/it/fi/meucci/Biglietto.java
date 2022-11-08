package it.fi.meucci;

 //creo la classe biglietto ha cui do un id e un nome al biglietto
public class Biglietto {
    int id;
    String nomebiglietto;
    
    public Biglietto(int id, String nomebiglietto) {
        this.id = id;
        this.nomebiglietto = nomebiglietto;
    }

    public Biglietto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomebiglietto() {
        return nomebiglietto;
    }
    
}

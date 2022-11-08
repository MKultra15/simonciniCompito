package it.fi.meucci;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;

//creo le variabili come la porta a cui mi dovro connettere e le stringhe che mandero e che ricevero dal server
public class ClientStr {
    String nomeServer = "localhost";
    int portaServer = 1510;
    Socket miosocket;
    BufferedReader tastiera;
    String stringaInviata;
    String stringaRicevutaDalServer;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;

    //metodo che mi permette di connettermi al server e ricevere da lui la lista di biglietti disponibili
    public Socket connetti()
    {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            //imposto l'input da tastiera e setto il socket
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            miosocket = new Socket(nomeServer, portaServer);
            outVersoServer = new DataOutputStream(miosocket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader( miosocket.getInputStream() ));

            //creo una lista vuota e dopo la metto in un nuovo ogeto di tipo messaggio
            ArrayList<Biglietto> listaVuota = new ArrayList<>();
            Messaggio m = new Messaggio(listaVuota);
            //mando il messaggio con dentro la lista vuota al server
            outVersoServer.writeBytes(objectMapper.writeValueAsString(m) + "\n");
            //mi salvo i biglietti disponibili che mi ha inviato dal server
            String bigliettiDisponibili = inDalServer.readLine();
            Messaggio messaggio = objectMapper.readValue(bigliettiDisponibili, Messaggio.class);

            System.out.println("prova");

            System.out.println("Biglietti disponibili:");
            for (int i = 0; i < messaggio.biglietti.size(); i++) {
                System.out.println("id: " + messaggio.biglietti.get(i).id + " nome biglietto: " + messaggio.biglietti.get(i).nomebiglietto);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("errore connessione");
        }

        return miosocket;
    }


    //metodo che mi permette di comunicare i biglietti che voglio acquistare al server
    public void comunica(){
        try {
            
                ArrayList<Biglietto> bigliettiDaComprare = new ArrayList<>();
                System.out.print("Quali biglietti vuoi acquistare?");
                stringaRicevutaDalServer = tastiera.readLine();
                String messaggio = stringaRicevutaDalServer;

                for (int i = 0 ; i<messaggio.length(); i++){
                    Biglietto b = new Biglietto( Integer.parseInt(messaggio), " ");
                    bigliettiDaComprare.add(b);
                }

                //manmdo la lista dei biglietti che voglio comprare al server
                ObjectMapper objectMapper = new ObjectMapper();
                Messaggio m = new Messaggio(bigliettiDaComprare);
                outVersoServer.writeBytes(objectMapper.writeValueAsString(m) + "\n");

                //ricevo indietro la stringa dei biglietti che sono riuscito ad acquistare
                String risposta = inDalServer.readLine();
                Messaggio messaggioricevuto = objectMapper.readValue(risposta, Messaggio.class);
                System.out.println("biglietti comprati:");
                for (int i = 0; i < messaggioricevuto.biglietti.size(); i++) {
                    System.out.println("id: " + messaggioricevuto.biglietti.get(i).id + " nome biglietto: " + messaggioricevuto.biglietti.get(i).getNomebiglietto());
                }
                
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


}
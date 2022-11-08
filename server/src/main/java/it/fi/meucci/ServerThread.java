package it.fi.meucci;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServerThread extends Thread{
    Socket client = null;
    BufferedReader inDalClient = null;
    DataOutputStream outVersoIlClient = null;

    ServerThread(Socket c){
        this.client = c;
    }

    //metodo run obbgliatorio per la classe che estende thread
    public void run(){
        try {
            this.comunica();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void comunica() throws Exception{
        inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outVersoIlClient = new DataOutputStream(client.getOutputStream());
        
        ObjectMapper objectMapper = new ObjectMapper();

        //creo un ciclo infinito in cui ricevo il messaggio dall'utente
        for(;;){
            String stringaRicevuta = inDalClient.readLine();
            Messaggio messaggio = objectMapper.readValue(stringaRicevuta, Messaggio.class);
            //se il mio array di biglietti è vuoto aggiungo i biglietti al messaggio e li mando
            if (messaggio.getBiglietti().size() == 0){
                Messaggio m = new Messaggio(MainServer.biglietti);
                outVersoIlClient.writeBytes(objectMapper.writeValueAsString(m) + "\n");
            } else {

                ArrayList<Biglietto> bigliettiAcquistati = new ArrayList<>();

                //scorro i due array e scopro quale biglietti vuole aqcquistare il client e li tolgo dalla mia lista
                for (int i = 0; i < messaggio.getBiglietti().size(); i++) {
                    for (int j = 0; j < MainServer.biglietti.size(); j++) {
                        if (messaggio.getBiglietti().get(i).id == MainServer.biglietti.get(j).id){
                            bigliettiAcquistati.add(messaggio.getBiglietti().get(i));
                            MainServer.biglietti.remove(j);
                            j--;
                            System.out.println("SERVER: biglietti comprati sono: " + bigliettiAcquistati.get(i).getId());
                        }
                    }
                }
                //mando al cliente la lista dei biglietti che ha acquistato
                Messaggio m = new Messaggio(bigliettiAcquistati);
                outVersoIlClient.writeBytes(objectMapper.writeValueAsString(m) + "\n");
            }
        }
    } 
}
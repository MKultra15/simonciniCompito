package it.fi.meucci;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

//classe che gestisce l'accensione del server e crea una lista di ServerThread
public class MainServer {
    Socket socket = null;
    ServerThread serverThread = null;
    private ServerSocket serverSocket;
    private ArrayList<ServerThread> lista = new ArrayList<>();

    public static ArrayList<Biglietto> biglietti = new ArrayList<>();

    //cosruttore creo tre biglietti da inserire nell'array di biglietti
    public MainServer() {
        Biglietto uno = new Biglietto(1, "spalti");
        Biglietto due = new Biglietto(2, "tribuna");
        Biglietto tre = new Biglietto(3, "palco");

        biglietti.add(uno);
        biglietti.add(due);
        biglietti.add(tre);
    }

    //creo la funzione avvio
    public void avvio(){
        try{
            //scrivo dei messaggi per fare capire se il server è on e metto la porta
            System.out.println("Avvio server");
            System.out.println("SERVER: acceso");
            this.serverSocket = new ServerSocket(1510);

            //faccio l'accept e un ciclo infinito in maniera da continuare a aggiungere client all'infinito
            for(;;){
                socket = serverSocket.accept();
                serverThread = new ServerThread(socket);
                this.lista.add(serverThread);
                serverThread.run();
            }

        //catturo l'errore in caso e mando un masseaggio    
        }catch(Exception e){
            System.out.println("ERRORE accensione server");
        }
    }

    
}
    
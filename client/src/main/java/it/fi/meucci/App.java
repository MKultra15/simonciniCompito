package it.fi.meucci;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ){
        ClientStr client = new ClientStr();
        client.connetti();
        client.comunica();
    }
}

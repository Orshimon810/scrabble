package model.logic;


import model.GameClientHandler;
import model.GameState;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MyServer extends Thread{

    private int port;
    private ClientHandler ch;
    private volatile boolean stop;
    private ServerSocket server;
    private PrintWriter out;
    private BufferedReader in;
    private static int numOfPlayers= 0;

    public MyServer(int port, ClientHandler ch) {
        this.port = port;
        this.ch = ch;

    }

    @Override
    public void run()
    {
        start();
    }
    public void start()
    {
        stop = false;
        new Thread(()-> {
            try {
                startServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    private void startServer() throws Exception
    {
        server = new ServerSocket(port);
        server.setSoTimeout(1000);
        while(!stop) {

            try {
                Socket aClient = server.accept();
                if(numOfPlayers < 3) {
                    numOfPlayers++;
                    String clientName = "Client #"+numOfPlayers;
                    // socket object to receive incoming client
                    // requests

                    // Displaying that new client is connected
                    // to server
                    System.out.println("\nNew client connected from: "
                            + aClient.getInetAddress()
                            .getHostAddress()+" number of clients: "+numOfPlayers);
                }
                         else{
                                 System.out.println("too much clients!");
                                   aClient.close();
                               }

                out = new PrintWriter(aClient.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(aClient.getInputStream()));

                try {
                    ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
                    ch.close();
                    aClient.getInputStream().close();
                    aClient.getOutputStream().close();
                    aClient.close();


                } catch (IOException e) {
                    e.getMessage();
                 ;}
            } catch (SocketTimeoutException e) {
                e.getMessage();

            }
        }
        server.close();
    }

    public void close()
    {
        stop = true;
    }

}

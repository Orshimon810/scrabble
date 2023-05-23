package model;

import model.logic.BookScrabbleHandler;
import model.logic.DictionaryManager;
import model.logic.MyServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class HostPlayer extends Player{
    private MyServer queryServer;
    private MyServer gameServer;
    private int port = 9997;


    public HostPlayer() {
        queryServer = new MyServer(port,new BookScrabbleHandler());
        gameServer = new MyServer(port,new sendQueryHandler());
    }


    public boolean dictLegal(String query ){
        queryServer.start();
        // [*] get input(tiles) from client(button's gui?) make it to a string so we can
        // send it as a query to the BookScrabbleHandler
        // [*] put tests here like eli did in mainTrain -> testBSCH
        try {
            DictionaryManager dm = DictionaryManager.get();
            dm.query("TOKEN");
            Socket server = new Socket("localhost", port);
            PrintWriter out = new PrintWriter(server.getOutputStream());
            Scanner in = new Scanner(server.getInputStream());
            out.println(query);
            out.flush();
            String res = in.next();
            System.out.println(res);
            if (( !res.equals("true")))
                  System.out.println("problem getting the right answer from the server (-10)");
            in.close();
            out.close();
            server.close();
        } catch (IOException e) {
            System.out.println("your code ran into an IOException (-10)");
            e.printStackTrace();

        }
        queryServer.close();
        return true;
    }
    public MyServer getGameServer() {
        return gameServer;
    }
    public MyServer getQueryServer() {
        return queryServer;
    }


    //Method which close all the connection that the host have
    public void closeConnection(){
      queryServer.close();
      gameServer.close();
    }

}

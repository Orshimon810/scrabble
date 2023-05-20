package model;

import model.logic.ClientHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameClientHandler implements ClientHandler{
    public static List<GameClientHandler> handlerList = new ArrayList<>();
    private ObjectOutputStream writer;
    public static GameState gameStateInstance = GameState.getGameState();



    @Override
    public void handleClient(InputStream inFromClient, OutputStream outToClient) {
        try{
            handlerList.add(this);
            writer = new ObjectOutputStream(outToClient);
            broadcastMessage();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastMessage() {
        for(GameClientHandler guestPlayer : handlerList){
            try{
                    guestPlayer.writer.writeObject(gameStateInstance);
                    guestPlayer.writer.flush();

            } catch (IOException e) {
               e.printStackTrace();
            }
        }
    }
}

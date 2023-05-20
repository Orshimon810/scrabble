package model;

import model.concrete.Word;
import model.logic.MyServer;

import java.util.List;
import java.util.Observable;

public class Model extends Observable implements ScrabbleFacade {
    HostPlayer host;
    GameState gameState; // controls all the objects of the game, whenever something changes the game state updates.


    public Model() {
        gameState = GameState.getGameState();
    }

    public void initGame(){
        //System.out.println(host.isMoveLegal("Q,mobydick.txt,"+"TOKEN"));
    }

    //Getting the host from players list, if null: Host not found
    public Player getHost(){
        for( Player p: gameState.getPlayersList()) {
            if (p.getClass().equals(HostPlayer.class)) {
                return p;
            }
        }
        return null;
    }
    @Override
    public void hostGame(int port) {
        gameState.getPlayersList().add(host);
        host.getGameServer().start();
    }
    @Override
    public void joinGame(String ip, int port) {
        GuestPlayer client = new GuestPlayer(ip,port);
        client.start();
        gameState.getPlayersList().add(client);

    }
    @Override
    public void disconnect(Player to_disconnected) {
        if (to_disconnected.getClass().equals(HostPlayer.class)){
            host.closeConnection();
        }
        else{
            for(Player p : gameState.getPlayersList()){
                if(p.getId() == to_disconnected.getId()){
                    GuestPlayer gp = (GuestPlayer) to_disconnected;
                    gp.closeConnection();
                }
            }
        }
    }
    @Override
    public boolean move(Word w) {
        boolean isMoveValid;
        int move = gameState.getPlayersList().get(gameState.getCurPlayerInd()).makeMove(w);

        if(move != 0){                  //Checks if player move is valid
            return false;
        }

        isMoveValid = host.dictLegal(w.DisplayWord());     //Checks if the word is legal
        return isMoveValid;
    }



}


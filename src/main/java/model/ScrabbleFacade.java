package model;

import model.concrete.Word;

public interface ScrabbleFacade {
    void hostGame(int port);


    void joinGame(String ip, int port);


    void disconnect(Player to_disconnected);


    boolean move(Word w);


}

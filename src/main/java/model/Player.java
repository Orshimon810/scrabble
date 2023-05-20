package model;

import model.concrete.Board;
import model.concrete.Tile;
import model.concrete.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    private static int playersCounter = 1; // NOTE: with always the playersCounter is +1 from real playersNum
    String playerName;
    int id;
    List<Tile> pack;
    int packSize; // physical size of tiles
    int sumScore;
    String query;

    public Player(){
        // players id is from 1-4
        this.id = playersCounter++;
        this.pack = new ArrayList<>();
        this.packSize = 7;
        this.sumScore = 0;
    }

    public int makeMove(Word w){
        int tmpMoveScore = 0;
        // if tiles are over
        if(packSize == 0){
            System.out.println("Tiles are over");
            return tmpMoveScore;
        }
        // if the player wants to place a word with no enough tiles
        else if(w.getTiles().length > packSize){
            System.out.println("Tiles are over");
            return tmpMoveScore;
        }
        // if the player don't have all the tiles for word
        else if(!isContainTilesForWord(w)){
            System.out.println("Not all word tiles are existed");
            return tmpMoveScore;
        }
        tmpMoveScore += Board.getBoard().tryPlaceWord(w);
        // after all checks,decline the words size from pack and init pack back to 7.
        if(tmpMoveScore != 0){
            packSize -= w.getTiles().length;
            initPackAfterMove(w);
        }
        sumScore += tmpMoveScore;
        return tmpMoveScore;
    }

    private boolean isContainTilesForWord(Word w) {
        for(Tile t: pack){
            if(!(Arrays.stream(w.getTiles()).toList().contains(t))){
                return false;
            }
        }
        return true;
    }


    //Functions for managing players tiles pack

    // func for re-packing the player hand with tiles after placing word on board
    public void initPackAfterMove(Word w) {
        List<Tile>tmpWordList = Arrays.stream(w.getTiles()).toList();
        pack = pack.stream().filter((t)->!tmpWordList.contains(t)).collect(Collectors.toList());
        while(!packIsFull()){
            pack.add(Tile.Bag.getBag().getRand());
            packSize++;
        }
    }

    // first initialization of players pack.
    public void initPack(){
        for(int i =0; i < packSize; i++){
            pack.add(Tile.Bag.getBag().getRand());
        }
    }


    //Getters
    public boolean packIsFull()
    {
        return pack.size() == 7;
    }

    public int getPackSize()
    {
        return packSize;
    }

    public List<Tile> getPack()
    {
        return this.pack;
    }

    public int getId() {return id;}

    public String getQuery()
    {
        return query;
    }


    public void setQuery(String q)
    {
        this.query = q;
    }

}
package model;

import model.concrete.Board;
import model.concrete.Tile;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public  class GameState {
     public Tile.Bag bag;
     List<Player> playersList;
    public   Board board;
     public boolean isGameOver;

    //CTOR
    public  GameState() {
      board = Board.getBoard();
       bag = Tile.Bag.getBag();
        playersList = new ArrayList<>();
      isGameOver = false;
    }

    //Getters


    public  List<Player> getPlayersList() {
        return playersList;
    }


    public  boolean getIsGameOver(){return isGameOver;}

    public void initHands(){
        for(int i = 0; i < playersList.size(); i++){
            for(int j=0;j<playersList.get(i).handSize;j++)
                playersList.get(i).playerHand.add(bag.getRand());
        }
    }
    // Functions
    public  void setTurns(){
        //extracting randomly tile for each player, setting is id, returning to bag
        int id = 1;

        System.out.println("inside set turns");
        for(Player p : playersList){
            Tile tempTile = bag.getRand();
            p.id = tempTile.score;
            bag.put(tempTile);
        }
        // sorting the list from big id to small id with sorting & reversing the order
        playersList = playersList.stream().sorted(Comparator.comparingInt(Player::getId).reversed())
                .collect(Collectors.toList());

        for(Player p : playersList)
        {
            p.id = id;
            id++;
        }
        //first player at list is now playing first randomly
    }


    public  void addPlayer(Player player)
    {
        playersList.add(player);
    }

    public  Player isWinner(){
        int max = 0;
        Player tmpPlayer = null;
        //Winner: when the tiles bag is empty and the winner finished his pack
        if(bag.getTilesCounter() == 0){
            for(Player p : playersList){
                if(max < p.sumScore && p.handSize == 0){
                    max = p.sumScore;
                    tmpPlayer =  p;
                }
            }
            isGameOver = true;
            return tmpPlayer;
        }
        return null;
    }

    public String getTextFiles(){
        String folderPath = "search_folder";
        StringBuilder textFilesBuilder = new StringBuilder();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if(files != null){
            for(File file: files){
                textFilesBuilder.append(file.getName());
                textFilesBuilder.append(',');
            }
        }
        return textFilesBuilder.toString();
    }

}

package model;

import model.logic.ClientHandler;

import java.io.*;
import java.net.Socket;

public class sendQueryHandler implements ClientHandler{
    GameState gs = GameState.getGameState();
    BufferedReader reader;
    PrintWriter writer;


    @Override
    public void handleClient(InputStream inFromClient, OutputStream outToClient) {
         reader = new BufferedReader(new InputStreamReader(inFromClient));
         writer = new PrintWriter(outToClient, true);

        // Read the query from the client
        String query = null;
        try {
            query = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Process the query and send the answer back to the client
        String answer = processQuery(query);
        writer.println(answer);
    }

    public String processQuery(String query) {
        // Process the query and generate the answer
        // Implement your logic here based on the specific requirements
        return "Answer to the query: " + query;
    }


    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.close();
    }


    public static void main(String [] args) throws IOException {
        Socket a_client = new Socket("localhost" , 1234);
        InputStream in = new 
    }


}

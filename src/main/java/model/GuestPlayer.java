package model;
import java.io.*;
import java.net.*;
import java.util.*;

//TODO: why we need GameClient? why just not consider GuestPlayer as GameClinet and just open the sovket overthere?

// Client class
public class GuestPlayer extends Player{
    String ip;
    int port;
    String name;
    Socket socket;

    public GuestPlayer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    // driver code
    public void start()
    {
        // establish a connection by providing host and port
        // number
        try (Socket socket = new Socket(ip, port))
        {
            this.socket = socket;
            // writing to server
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            // reading from server
            BufferedReader in
                    = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));

            // object of scanner class
            Scanner sc = new Scanner(System.in);
            String line = null;
            System.out.println("Please enter your name: ");
            name = sc.nextLine();
            System.out.println("Server replied: hi "
                    + name);

            while (!"exit".equalsIgnoreCase(line)) {

                // reading from user
                line = sc.nextLine();

                // sending the user input to server
                out.println(line);
                out.flush();

                // displaying server reply
                System.out.println("Server replied "
                        + in.readLine());
            }

            // closing the scanner object
            sc.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean sendQueryToHost(String query){
        return false;
    }



    public String getName()
    {
        return name;
    }


    //Method which close the connection to the host
    public void closeConnection(){
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

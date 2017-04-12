
import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;
public final class ChatClient {

    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket("codebank.xyz", 38001)) {

            //Used to create a thread that will solely read the incoming messages from the server 
            Runnable rn = () -> {
            try{
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            
            String line = br.readLine();

            //Loop checks for input and prints it out
            while(line != null){
            System.out.print("\nServer> ");
            System.out.println(line);
            System.out.print("Client> ");
            line = br.readLine();
            
            }

            //After the connection with the server closes, the program closes
            System.out.println("Disconnected");
            System.exit(0);

            }catch (Exception e){
             System.out.println(e);
            }
            };

            //Listening thread is started
            Thread listening = new Thread(rn);
            listening.start();

            //Following sends the username and any other message inputed by the user to the server
            Scanner sc = new Scanner(System.in);
            OutputStream os = socket.getOutputStream();
            PrintStream out = new PrintStream(os, true, "UTF-8");

            System.out.print("Please enter a user name: ");
            String line = sc.nextLine();
            out.println(line);
 
            System.out.print("Client> ");
             
            while(sc.hasNextLine()){
            line = sc.nextLine();
            out.println(line);
            }

        sc.close();
      }
    }
    
}
















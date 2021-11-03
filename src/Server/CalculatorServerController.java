package Server;

import Client.CalculatorView;
import Server.CalculatorModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This ties the view and model together
 */
public class CalculatorServerController {

    CalculatorModel theModel;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ExecutorService pool;

    public CalculatorServerController(int portNumber){
        try {
            serverSocket = new ServerSocket(portNumber);
            pool = Executors.newCachedThreadPool();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void runServer(){
        while(true){
            try{
                // accept clients request
                clientSocket = serverSocket.accept();

                theModel = new CalculatorModel(clientSocket);
                pool.execute(theModel);
            }catch(IOException e){
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args){
        CalculatorServerController server = new CalculatorServerController(9090);
        server.runServer();

    }

}

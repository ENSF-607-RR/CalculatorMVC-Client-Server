package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is responsible for establishing connections with the client
 * and running the Calculator application
 */
public class CalculatorServerController {

    CalculatorModel theModel;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ExecutorService pool;
    private CalculatorController theController;

    public CalculatorServerController(int portNumber){
        try {
            serverSocket = new ServerSocket(portNumber);
            pool = Executors.newCachedThreadPool();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void runServer(){

        try{
            while(true) {
                // accept clients request
                clientSocket = serverSocket.accept();

                theModel = new CalculatorModel();
                theController = new CalculatorController(theModel, clientSocket);
                // run the application on a thread
                pool.execute(theController);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        // close the thread pool
        pool.shutdown();
    }

    public static void main(String[] args){
        CalculatorServerController server = new CalculatorServerController(9090);
        server.runServer();

    }

}

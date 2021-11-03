package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Model has the data
 */
public class CalculatorModel implements Runnable{
    private PrintWriter socketOut;
    private BufferedReader socketIn;
    private int result;

    public CalculatorModel(Socket socket){
        try{
            socketOut = new PrintWriter(socket.getOutputStream(), true);
            socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void addTwoNumber(int a, int b){
        result = a + b;
    }

    public int getResult() {
        return result;
    }

    @Override
    public void run() {
        String[] buffer;
        String line = null;
        String response = null;
        int a;
        int b;
        while(true){
            try {
                line = socketIn.readLine();
                if(line != null){
                    buffer = line.split(",");
                    a = Integer.parseInt(buffer[0]);
                    b = Integer.parseInt(buffer[1]);
                    addTwoNumber(a, b);
                    response = Integer.toString(getResult());
                    socketOut.println(response);
                    socketOut.flush();
                    line = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

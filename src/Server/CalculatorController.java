package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This class is responsible for receiving data from the client, use the model to do operations on the data,
 * and then sending the result of the communication back to the client.
 */
public class CalculatorController implements Runnable{

    private CalculatorModel theModel;
    private PrintWriter socketOut;
    private BufferedReader socketIn;

    public CalculatorController(CalculatorModel model, Socket socket){
        theModel = model;

        try{
            socketOut = new PrintWriter(socket.getOutputStream(), true);
            socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        String[] buffer;
        String line = null;
        String response = null;
        int a;
        int b;
        String op;

        while(true){
            try {
                line = socketIn.readLine();
                // client quit
                if(line.equals("QUIT")){
                    socketIn.close();
                    socketOut.close();
                    return;
                }
                // line from client
                if(line != null){
                    buffer = line.split(",");
                    a = Integer.parseInt(buffer[0]);
                    op = buffer[1];
                    b = Integer.parseInt(buffer[2]);

                    switch (op) {
                        case "+" -> theModel.addTwoNumber(a, b);
                        case "-" -> theModel.subtractTwoNumbers(a, b);
                    }

                    response = Integer.toString(theModel.getResult());
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

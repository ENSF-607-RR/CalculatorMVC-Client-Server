package Client;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This class is responsible for getting input from the view and communicating it to the server
 */
public class CalculatorClientController {

    private Socket aSocket;
    private PrintWriter socketOut;
    private BufferedReader socketIn;
    private CalculatorView theView;

    /**
     * Connects to server and instantiates the view
     */
    public CalculatorClientController(String serverName, int portNumber, CalculatorView theView){
        this.theView = theView;
        try{
            // request connection to server
            aSocket = new Socket(serverName, portNumber);
            // open i/o streams to server
            socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
            socketOut = new PrintWriter(aSocket.getOutputStream(), true);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void communicate(){

        // user wants to add
        theView.addAddListener(e ->{
            int firstNumber = 0;
            int secondNumber = 0;
            String line = "";

            try {
                // data read from view
                firstNumber = theView.getFirstNumber();
                secondNumber = theView.getSecondNumber();

                // package data in a string
                line += firstNumber + ",+," + secondNumber; // a,+,b
                socketOut.println(line); // sending data to server

                // result from the server
                int solution = Integer.parseInt(socketIn.readLine());

                // update the view
                theView.setTheSolution(solution);
            }
            catch (IOException e1){
                e1.printStackTrace();
            }catch(NumberFormatException ex){
                theView.displayErrorMessage("Error! You must enter a number!");
            }
        });

        theView.addSubtractListener(e->{
            int firstNumber = 0;
            int secondNumber = 0;
            String line = "";

            try {

                firstNumber = theView.getFirstNumber();
                secondNumber = theView.getSecondNumber();

                line += firstNumber + ",-," + secondNumber;
                socketOut.println(line);

                int solution = Integer.parseInt(socketIn.readLine());
                theView.setTheSolution(solution);
            }
            catch (IOException e1){
                e1.printStackTrace();
            }catch(NumberFormatException ex){
                theView.displayErrorMessage("Error! You must enter a number!");
            }
        });

        // user quits the application
        theView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                // tell server that user quit
                socketOut.println("QUIT");
                try {
                    // close the sockets
                    socketOut.close();
                    socketIn.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }


    public static void main(String[] args){
        CalculatorView theView = new CalculatorView();
        CalculatorClientController client = new CalculatorClientController("localHost", 9090, theView);
        client.communicate();
        theView.setVisible(true);
    }
}

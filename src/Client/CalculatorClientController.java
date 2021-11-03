package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CalculatorClientController {

    private Socket aSocket;
    private PrintWriter socketOut;
    private BufferedReader socketIn;

    public CalculatorClientController(String serverName, int portNumber){
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

    public void communicate(CalculatorView theView){

        theView.addAddListener(e ->{
            int firstNumber = 0;
            int secondNumber = 0;
            String line = "";
            try {
                // We are reading data from the view
                firstNumber = theView.getFirstNumber(); // send to server
                secondNumber = theView.getSecondNumber(); // send to server

                line += firstNumber + ",+," + secondNumber;
                socketOut.println(line); // sending data to server

                // Reading result from server
                int solution = Integer.parseInt(socketIn.readLine());

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
                // We are reading data from the view
                firstNumber = theView.getFirstNumber(); // send to server
                secondNumber = theView.getSecondNumber(); // send to server

                line += firstNumber + ",-," + secondNumber;
                socketOut.println(line); // sending data to server

                // Reading result from server
                int solution = Integer.parseInt(socketIn.readLine());

                theView.setTheSolution(solution);
            }
            catch (IOException e1){
                e1.printStackTrace();
            }catch(NumberFormatException ex){
                theView.displayErrorMessage("Error! You must enter a number!");
            }
        });
    }


    public static void main(String[] args){
        CalculatorView theView = new CalculatorView();
        CalculatorClientController client = new CalculatorClientController("localHost", 9090);
        client.communicate(theView);


        theView.setVisible(true);
    }
}

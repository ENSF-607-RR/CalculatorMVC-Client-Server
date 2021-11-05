package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This call is responsible for carrying out the calculations on the data
 */
public class CalculatorModel{

    private int result;

    public void addTwoNumber(int a, int b){
        result = a + b;
    }

    public void subtractTwoNumbers(int a, int b){
        result = a - b;
    }

    public int getResult() {
        return result;
    }
}

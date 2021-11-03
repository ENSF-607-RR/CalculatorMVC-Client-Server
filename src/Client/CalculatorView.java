package Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorView extends JFrame {

    private JTextField firstNumber = new JTextField(10);
    private JLabel addLabel = new JLabel("+");
    private JTextField secondNumber = new JTextField(10);
    private JButton calculateButton = new JButton("Calculate");
    private JTextField theSolution = new JTextField(10);
  


    public CalculatorView(){
        JPanel calulatorPanel = new JPanel();

        calulatorPanel.setSize(300, 300);
        calulatorPanel.add(firstNumber);
        calulatorPanel.add(addLabel);
        calulatorPanel.add(secondNumber);
        calulatorPanel.add(calculateButton);
        calulatorPanel.add(theSolution);

        this.add(calulatorPanel);


    }

    public int getFirstNumber(){
        return Integer.parseInt(firstNumber.getText());
    }

    public int getSecondNumber(){
        return Integer.parseInt(secondNumber.getText());
    }

    public void setTheSolution(int theSolution){
        this.theSolution.setText(Integer.toString(theSolution));
    }

    public void addCalculateListener(ActionListener listenForCalculateButton){
        calculateButton.addActionListener(listenForCalculateButton);
    }

    public void displayErrorMessage(String errorMessage){
        JOptionPane.showMessageDialog(this, errorMessage);
    }

}

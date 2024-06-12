package GUI;
import java.io.IOException;

import javax.swing.*;
import vm252simulation.*;
import vm252simulation.VM252Model.StoppedCategory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class StepButton{
     //
    //  private instance variables
    //

    private JButton myStepButton;
    private VM252Model myVM252;
    private VM252Stepper mySimulation;

    //
    //  Button acc/mod
    //

    public JButton getPlayButton(){
        return myStepButton;
    }
    public void setJButton(JButton other){
        myStepButton = other;
    }

    //
    //  Simulated machine acc/mod 
    //

    public VM252Model getVM252(){
        return myVM252;
    }
    public void setVM252(VM252Model other){
        myVM252 = other;
    }

    //
    //  Stepper acc/mod
    //

    public VM252Stepper VM252Stepper(){
        return mySimulation;
    }
    public void setVM252Stepper(VM252Stepper other){
        mySimulation = other;
    }

    //
    //  ctor 
    //


    public StepButton(VM252Model simulatedMachine, VM252Stepper machineStepper){
        setVM252(simulatedMachine);
        setVM252Stepper(machineStepper);
        setJButton(new JButton("Step"));
        getPlayButton().addActionListener(new ActionListener() {
        @Override
            public void actionPerformed(ActionEvent e){
                
                    try{
                        unpause();
                    } catch(IOException e1) {
                        e1.printStackTrace();
                        System.out.print(e1);
                    } 
                }
            });
        }
    
    private void unpause() throws IOException{
        
        getVM252().setStoppedStatus(StoppedCategory.notStopped);
        VM252Stepper().step();
        getVM252().setStoppedStatus(StoppedCategory.stopped);

    }

}

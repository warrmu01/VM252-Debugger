package GUI;
import java.io.IOException;

import javax.swing.*;
import vm252simulation.*;
import vm252simulation.VM252Model.StoppedCategory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class PlayButton implements VM252Observer{

    //
    //  private instance variables
    //

    private JButton myPlayButton;
    private VM252Model myVM252;
    private VM252Stepper mySimulation;

    //
    //  Button acc/mod
    //

    public JButton getPlayButton(){
        return myPlayButton;
    }
    public void setJButton(JButton other){
        myPlayButton = other;
    }

    //
    //  Simulated machine acc/mod 
    //

    public VM252Model getVM252(){
        return myVM252;
    }
    public void setVM252(VM252Model other){
        
        if (getVM252() != null)
            getVM252().detach(this);

        myVM252 = other;

        if (getVM252() != null)
            getVM252().attach(this);
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
    public PlayButton(){
        setVM252(null);
        
    }

    public PlayButton(VM252Model simulatedMachine, VM252Stepper simulatedEnvironment){
            setVM252(simulatedMachine);
            setJButton(new JButton("PLAY"));
            getPlayButton().addActionListener(new ActionListener() {
            @Override
                public void actionPerformed(ActionEvent e){
                    if (getPlayButton().getText() == "PAUSE") {
                        simulatedMachine.setStoppedStatus(StoppedCategory.stopped);
                
                    } 
                    else if (getPlayButton().getText() == "PLAY") {
                        simulatedMachine.setStoppedStatus(StoppedCategory.notStopped);
                        getPlayButton().setText("PAUSE");
                    
                        try{
                            unpause();
                        } catch(IOException e1) {
                            e1.printStackTrace();
                            System.out.print(e1);
                        } 
                    }
                }
            });

        }
    
    private void unpause() throws IOException{

        while (getVM252().stoppedStatus()
                == VM252Model.StoppedCategory.notStopped ){
            
            VM252Stepper().step();
            }

    }






    public void update(){
        ;//do nothing
    }
    public void updateAccumulator(){
        ;//do nothing
    }
    public void updateProgramCounter(){
        ;//do nothing
    }
    public void updateMemory(int changeAddress){
        ;//do nothing
    }
    public void updateStoppedStatus(){
        if (getVM252().stoppedStatus() == StoppedCategory.stopped){
            getPlayButton().setText("PLAY");  
        }
        else{
            getPlayButton().setText("PAUSE");
        }
    }
        

   
    


}

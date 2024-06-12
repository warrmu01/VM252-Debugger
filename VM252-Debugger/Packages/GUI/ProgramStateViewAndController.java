package GUI;
//author , Alan Zaring (rgb ProgramStateViewAndController.java), used by Kurt Lebakken 11/5/2023
import java.util.*;
import vm252simulation.*;
import observation.Observer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProgramStateViewAndController extends JPanel implements VM252Observer{

    //data fields

    private static final int OUR_DEFAULT_FRAME_WIDTH = 300;
    private static final int OUR_DEFAULT_FRAME_HEIGHT = 300;
    private static final int OUR_DEFAULT_COMPONENT_FIELD_AND_AREA_WIDTH = 5;

    private JPanel myPanel;
    private VM252Model myVM252;
    private JTextField accumulatorTextField;
    private JTextField programCounterTextfield;

    public JPanel getPanel()
    {

        return myPanel;

        }

    private VM252Model getVM252()
    {

        return myVM252;

        }
    private JTextField getAccumulatorTextField()
    {

        return accumulatorTextField;

        }
    private JTextField getProgramCounterTextField()
    {

        return programCounterTextfield;

    }

    //
    //mutators
    //

    private void setPanel(JPanel other)
    {

        myPanel = other;

        }
    private void setSubjectModel(VM252Model other)
    {

        if (getVM252() != null)
            getVM252().detach(this);

        myVM252 = other;

        if (getVM252() != null)
            getVM252().attach(this);

        }
    private void setAccumulatorTextField(JTextField other)
    {

        accumulatorTextField = other;

        }

    private void setProgramCounterTextField(JTextField other)
    {

        programCounterTextfield = other;

        }

    public ProgramStateViewAndController()
    {

        this(null);

    }
    public ProgramStateViewAndController(VM252Model defaultProgramState)
    {

        setSize(OUR_DEFAULT_FRAME_WIDTH, OUR_DEFAULT_FRAME_HEIGHT);

        setSubjectModel(defaultProgramState);

        //
        // Create text fields for displaying and altering the color levels of the model
        //

        setAccumulatorTextField(
                new JTextField(
                        "" + getVM252().accumulator(),
                        OUR_DEFAULT_COMPONENT_FIELD_AND_AREA_WIDTH
                )
        );
        setProgramCounterTextField(
                new JTextField(
                        "" + getVM252().programCounter(),
                        OUR_DEFAULT_COMPONENT_FIELD_AND_AREA_WIDTH
                )
        );


        //
        // Create labels for the text fields
        //

        JLabel accumulatorLabel = new JLabel("ACC:", JLabel.RIGHT);
        JLabel programCounterLabel = new JLabel("PC:", JLabel.RIGHT);


        //
        // Create a panel to hold the labels and the text fields
        //

        setPanel(new JPanel());
        getPanel().setLayout(new GridLayout(1, 2, 40, 20));

        //
        // Add the labels and the text fields, in the order they should appear, to the
        // panel
        //

        getPanel().add(accumulatorLabel,BorderLayout.NORTH);
        getPanel().add(getAccumulatorTextField(),BorderLayout.NORTH);
        getPanel().add(programCounterLabel,BorderLayout.NORTH);
        getPanel().add(getProgramCounterTextField(),BorderLayout.NORTH);

        //padding
        accumulatorLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
        programCounterLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
        getPanel().setBorder(new EmptyBorder(30, 40, 40, 40));
        getProgramCounterTextField().setBorder(new EmptyBorder(10, 10, 10, 10));
        getAccumulatorTextField().setBorder(new EmptyBorder(10, 10, 10, 10));

        // color
        getPanel().setBackground( new Color(173, 216, 230));



        //
        // Add the panel to the container
        //

        add(getPanel());

        //
        // Create an action to alter the model's color when input is entered
        //

        ProgramStateInputAction inputAction = new ProgramStateInputAction();

        //
        // Associate the action with the text fields
        //

        getAccumulatorTextField().addActionListener(inputAction);
        getProgramCounterTextField().addActionListener(inputAction);

        //
        // Change the layout of the container of swatch panel so that swatch is maximally
        // sized (this layout change is unnecessary when the container of the swatch panel
        // is a JFrame but necessary when the container is a JPanel)
        //

        setLayout(new GridLayout(1, 1));

    }




    @Override 
    public void update(){
        updateProgramCounter();
        updateAccumulator();
    }
    @Override
    public void updateProgramCounter(){

        //
        // Set the text in the text fields to display the color levels of the updated
        // color specified by the model
        //
        

        
        getProgramCounterTextField().setText("" + getVM252().programCounter());


    }
    @Override
    public void updateAccumulator(){

        //
        // Set the text in the text fields to display the color levels of the updated
        // color specified by the model
        //
       

        getAccumulatorTextField().setText("" + getVM252().accumulator());
        


    }
    public void updateMemory(int changeAddress){
        ;//do nothing
    }


    public void updateStoppedStatus(){
        ;//do nothing
    }
        

    

    private class ProgramStateInputAction implements ActionListener
    {
        private final int accumulatorUpperRange = 32767;
        private final int accumulatorLowerRange = -32768;
        private final int programCounterUpperRange = 8191;
        private final int programCounterLowerRange = 0;



        //
        // Ctors
        //

        public ProgramStateInputAction()
        {

        }

        //
        // Event handlers
        //

        @Override
        public void actionPerformed(ActionEvent event)
        {

            if (getVM252() != null) {

                Scanner accumulatorValueScanner =
                        new Scanner(getAccumulatorTextField().getText());
                Scanner programCounterValueScanner =
                        new Scanner(getProgramCounterTextField().getText());


                int accValue =
                        accumulatorValueScanner.hasNextInt()
                                ? Math.max(accumulatorLowerRange, Math.min(accumulatorUpperRange, accumulatorValueScanner.nextInt()))
                                : 0;
                int pcValue =
                        programCounterValueScanner.hasNextInt()
                                ? Math.max(programCounterLowerRange, Math.min(programCounterUpperRange, programCounterValueScanner.nextInt()))
                                : 0;




                getVM252().setAccumulator(accValue);
                getVM252().setProgramCounter(pcValue);

            }

        }

    }

}

package GUI;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import demonstration.Runner;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import vm252simulation.VM252Model;
import vm252simulation.VM252Model.StoppedCategory;
import demonstration.DemonstrationController;
import vm252simulation.VM252View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;


/*
class AccumulatorPrinter extends VM252View
{
    
    private final VM252Model myModel;
    
    public AccumulatorPrinter(VM252Model model)
    {       
        myModel = model;       
        }
    
    @Override
    public void updateAccumulator()
    {       
        System.out.println("accumulator is now " + myModel.accumulator());        
        }
    
    }


class ProgramCounterPrinter extends VM252View
{
    
    private final VM252Model myModel;
    
    public ProgramCounterPrinter(VM252Model model)
    {        
        myModel = model;        
        }
    
    @Override
    public void updateProgramCounter()
    {        
        System.out.println("program counter is now " + myModel.programCounter());        
        }
    
    }


class MemoryBytePrinter extends VM252View
{
    
    private final VM252Model myModel;
    
    public MemoryBytePrinter(VM252Model model)
    {        
        myModel = model;        
        }
    
    @Override
    public void updateMemory(int address)
    {        
        System.out.printf("memory byte at address %d is now %02x\n", address, myModel.memoryByte(address));        
        }
    
    }


class StopAnnouncer extends VM252View
{
    
    private final VM252Model myModel;
    
    public StopAnnouncer(VM252Model model)
    {        
        myModel = model;        
        }
    
    @Override
    public void updateStoppedStatus()
    {        
        System.out.printf(
            "machine stops with accumulator %d and program counter %d\n",
                myModel.accumulator(),
                myModel.programCounter()
            );        
        }
    
    }
*/
public class VM252GUI {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        // Create new instance of the VM252 model
        VM252Model simulatedMachine = new VM252Model();
        /*
        simulatedMachine.attach(new AccumulatorPrinter(simulatedMachine));
        simulatedMachine.attach(new ProgramCounterPrinter(simulatedMachine));
        simulatedMachine.attach(new MemoryBytePrinter(simulatedMachine));
        simulatedMachine.attach(new StopAnnouncer(simulatedMachine));
        */
        DemonstrationController simulator = new DemonstrationController(simulatedMachine);

        // Create the main frame
        JFrame frame = new JFrame("CS252 Project");
        frame.setSize(1200, 800);

    
    // Create a panel for the main content

        // Using BorderLayout fro the layout of the panels and the frame

        JPanel contentPanel1 = new JPanel(new BorderLayout());
        JPanel contentPanel2 = new JPanel(new BorderLayout());
        frame.setLayout(new BorderLayout());
        frame.add(contentPanel2, BorderLayout.WEST);
        frame.add(contentPanel1, BorderLayout.CENTER);

        // create empty borders around the panels

        contentPanel1.setBorder(new EmptyBorder(70, 50, 70, 50));
        contentPanel2.setBorder(new EmptyBorder(20, 80, 20, 80));

    // Create a text area for the terminal-style display

        JTextArea terminaltextArea = new JTextArea(8, 30);
        terminaltextArea.setEditable(true);

        // Make it scroll pane so that a lot of data can be viewed  
        JScrollPane scrollPane = new JScrollPane(terminaltextArea);
        // Add text area to the center of the contentPanel
        contentPanel1.add(scrollPane, BorderLayout.CENTER); 


    // Initializing buttons for run file and play button

        JButton Runnerbutton = new JButton("Open File");
        //JButton Playbutton = new JButton("PLAY");
        
        PlayButton ObserverPlayButton = new PlayButton(simulatedMachine, simulator.machineStepper() );
        JButton Playbutton = ObserverPlayButton.getPlayButton();
        StepButton ObserverStepButton = new StepButton(simulatedMachine, simulator.machineStepper());
        // Button to run a vm252 file

        Runnerbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fileChooser = new JFileChooser();

                FileNameExtensionFilter filter = new FileNameExtensionFilter("VM252obj Files", "vm252obj");
                fileChooser.setFileFilter(filter);

                if (e.getSource() == Runnerbutton) {
                    terminaltextArea.append("Please Choose a File to Run" + "\n");
                    System.out.println("Choosing a File");

                    int returnValue = fileChooser.showOpenDialog(terminaltextArea);

                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File ourFile = fileChooser.getSelectedFile();

                        terminaltextArea.append("Opening: " + ourFile.getName() + "\n");
                        System.out.println("Opening: " + ourFile.getName());

                        String chosenFilePath = ourFile.getAbsolutePath();
                        System.out.println(chosenFilePath);
                        terminaltextArea.append(chosenFilePath + "\n");
                
                        Scanner inputStream = new Scanner(System.in);
                        try {
                            simulator.loadAndRun(chosenFilePath, inputStream, System.out);
                            ObserverPlayButton.setVM252Stepper(simulator.machineStepper());
                            ObserverStepButton.setVM252Stepper(simulator.machineStepper());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                            System.out.print(e1);
                        }

                        } else {
                            terminaltextArea.append("No File Found" + "\n");
                            System.out.println("No File Found");
                        }

                    } else {
                        terminaltextArea.append("File Application Closed by User" + "\n");
                        System.out.println("File Application Closed by User");
                    }
                }
            });

         // Button to switch play button to pause once presses and then back to play when presses again
       /* 
       Playbutton.addActionListener(new ActionListener() {
        @Override
            public void actionPerformed(ActionEvent e){
                if (Playbutton.getText() == "PAUSE") {
                    simulatedMachine.setStoppedStatus(StoppedCategory.stopped);
                    Playbutton.setText("PLAY");
                    terminaltextArea.append("VM252 Debugger Stopped" + "\n");
                    System.out.println("VM252 Debugger Stopped");

                } else if (Playbutton.getText() == "PLAY") {
                    simulatedMachine.setStoppedStatus(StoppedCategory.notStopped);
                    Playbutton.setText("PAUSE");
                    terminaltextArea.append("VM252 Resumed" + "\n");
                    System.out.println("VM252 Debugger Resumed");
                    try{
                        simulator.unpause();
                    } catch(IOException e1) {
                      e1.printStackTrace();
                      System.out.print(e1);
                    } 
                }
            }
        });
        */

    // Creating a panel using gridlayout to store the run file and play button

        JPanel buttonPanel2 = new JPanel(new GridLayout(1,4, 50, 30));
        buttonPanel2.add(Runnerbutton);
        buttonPanel2.add(Playbutton);
        buttonPanel2.add(ObserverStepButton.getPlayButton());

        //  Setting a desired sign for the buttons
        Runnerbutton.setPreferredSize(new Dimension(10, 30));
        Playbutton.setPreferredSize(new Dimension(10, 30));
        ObserverStepButton.getPlayButton().setPreferredSize(new Dimension(10, 30));

    // Create headers for textArea4 and textArea5

    /* 
        JLabel headerLabel4 = new JLabel("ACC", SwingConstants.CENTER);
        JLabel headerLabel5 = new JLabel("PC", SwingConstants.CENTER);
    
        // Create two small text areas
        JTextArea textArea4 = new JTextArea(3, 6);
        textArea4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JTextArea textArea5 = new JTextArea(3, 6);
        textArea5.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    */
        ProgramStateViewAndController ACCPCview = new ProgramStateViewAndController(simulatedMachine);
        
   
        // Add the text areas and headers to a new panel
        JPanel textAreaPanel = new JPanel(new GridLayout(1, 2, 40, 20));
        JPanel panel4 = new JPanel(new BorderLayout());
        panel4.add(ACCPCview.getPanel());
        /* 
        panel4.add(headerLabel4, BorderLayout.NORTH);
        panel4.add(textArea4, BorderLayout.CENTER);
        JPanel panel5 = new JPanel(new BorderLayout());
        panel5.add(headerLabel5, BorderLayout.NORTH);
        panel5.add(textArea5, BorderLayout.CENTER);
        */
        textAreaPanel.add(panel4);
        //textAreaPanel.add(panel5);

    // Add empty borders aroud the items mentioned below

        //headerLabel5.setBorder(new EmptyBorder(5, 10, 5, 10));
        //headerLabel4.setBorder(new EmptyBorder(5, 10, 5, 10));
        textAreaPanel.setBorder(new EmptyBorder(30, 40, 40, 40));
        //textArea4.setBorder(new EmptyBorder(10, 10, 10, 10));
        //textArea5.setBorder(new EmptyBorder(10, 10, 10, 10));

    // Creete the small text areas to store values in memoery in different types
    /*
        JTextArea textArea3 = new JTextArea(8, 30);
        textArea3.setEditable(true);
        JScrollPane scrollPane3 = new JScrollPane(textArea3);
    */
        MemoryView machineMemory = new MemoryView(simulatedMachine);
        JPanel memoryWindow = machineMemory.getPanel();
        JScrollPane scrollPane3 = machineMemory.getJScrollPane();

    // Create a panel to store buttons used in contentpanel2

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 20,20));
        
        //JButton button1 = new JButton("Cmd 1");
        //JButton button2 = new JButton("Cmd 2");
        //JButton button3 = new JButton("Cmd 3");
         

        JButton clearButton = new JButton("Clear");
        JButton infoButton = new JButton("?");

        //  Action listener for all the buttons defined above

        /* 
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //textArea3.append("Command 1 executed.\n");
            }
        });
        

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //textArea3.append("Command 2 executed.\n");
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //textArea3.append("Command 3 executed.\n");
            }
        });
        */


        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the text area
                terminaltextArea.setText("");
            }
        });

        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                terminaltextArea.append("Command 5 executed.\n");
            }
        });

        // Give the buttons a prefered size that would look good and fit well

        
        //button1.setPreferredSize(new Dimension(10, 30));
        //button2.setPreferredSize(new Dimension(10, 30));
        //button3.setPreferredSize(new Dimension(10, 30));
        clearButton.setPreferredSize(new Dimension(10, 30));
        infoButton.setPreferredSize(new Dimension(10, 30));

        // inside the button panel buttons are in two rows top and bottom
            
            // Top button panel is defined and buttons to be placed there assigned
                JPanel topButtonPanel = new JPanel(new GridLayout(1, 3));
                //topButtonPanel.add(button1);
                //topButtonPanel.add(button2);
                //topButtonPanel.add(button3);
            
            // bottom button panel is defined and buttons to be placed there assigned
                JPanel bottomButtonPanel = new JPanel(new GridLayout(1, 2));
                bottomButtonPanel.add(clearButton);
                bottomButtonPanel.add(infoButton);
               
        // buttons the diferent button panels in together 
        buttonPanel.add(topButtonPanel);
        buttonPanel.add(bottomButtonPanel);

        // Add compnents are places in the desired content panel
        contentPanel1.add(buttonPanel2, BorderLayout.NORTH);
        contentPanel2.add(textAreaPanel, BorderLayout.NORTH);
        contentPanel2.add(memoryWindow, BorderLayout.CENTER);
        contentPanel2.add(buttonPanel, BorderLayout.SOUTH);

        // Assign empty borders to the following items
        buttonPanel.setBorder(new EmptyBorder(40, 20, 40, 20));
        buttonPanel2.setBorder(new EmptyBorder(10, 10, 20, 10));
        scrollPane3.setBorder(new EmptyBorder(20, 10, 20, 10));
        scrollPane.setBorder(new EmptyBorder(20, 10, 20, 10));
        
        // Define colours that will be used for the GUI
        Color lightBlue = new Color(173, 216, 230);  // Light Blue
        Color mediumBlue = new Color(70, 130, 180);   // Medium Blue

        // Background colors given
        contentPanel1.setBackground(lightBlue);
        contentPanel2.setBackground(lightBlue);
        buttonPanel2.setBackground(mediumBlue);
        textAreaPanel.setBackground(mediumBlue);
        buttonPanel.setBackground(mediumBlue);
        topButtonPanel.setBackground(mediumBlue);
        bottomButtonPanel.setBackground(mediumBlue);
        panel4.setBackground(lightBlue);
        //panel5.setBackground(lightBlue);

        frame.setResizable(true);
        frame.setVisible(true);
    }
}

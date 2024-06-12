package GUI;
//
// This is a skeleton of a class MemoryView that could be used for displaying the memory of
// a VM252 so that its contents can be viewed/altered and breakpoints set.
//


import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Element;
import javax.swing.text.PlainDocument;
import vm252simulation.*;
import vm252simulation.VM252Model.StoppedCategory;
import observation.*;
import vm252architecturespecifications.*;


import tuples.*;

/*
public class MemoryView
{


    public static void main(String [] commandLineArguments)
    {

        EventQueue.invokeLater(
            () ->
                {


                    //
                    // Create program frame
                    //

                        ProgramFrame frame = new ProgramFrame();
                        frame.setSize(450, 450);

                    //
                    // Set frame's visibility and closing behavior
                    //

                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setVisible(true);

                    }

            );

        }

    }



class ProgramFrame extends JFrame
{

    private JPanel myPanel;

    //
    // Accessors
    //

    private JPanel getPanel()
    {
        return myPanel;
        }

    //
    // Mutators
    //

    private void setPanel(JPanel other)
    {
        myPanel = other;
        }

    //
    // Ctors
    //

    public ProgramFrame()
    {

        setTitle("Memory View");

        //
        // Create view panel
        //

            MemoryView text = new MemoryView(new VM252Model());

        //
        // Create panel to hold all views and controllers
        //

            setPanel(new JPanel());

        //
        // Add views and controllers to panel
        //

            //getPanel().setLayout(new BorderLayout());
            getPanel().add(text);

        //
        // Add panel to program frame
        //

            add(getPanel());

        }

    }

*/
public class MemoryView extends JPanel implements VM252Observer
{
    //
    //    Jpanel (get used by main class)
    //
    private JPanel myPanel;
    //
    //
    //
    private JScrollPane myJScrollPane;
    //
    //    Simulated Machine
    //
    private VM252Model myVM252;
    //
    //    Highlight data
    //
    private HashMap< Integer, Object > myHighlights;
    //
    //    Breakpoint painter
    //
    private DefaultHighlighter.DefaultHighlightPainter myHighlighterPainter;
    //
    //    Text Area
    //
    private JTextArea myTextArea;
    //
    //    Line numbers Text Area
    //
    private JTextArea myLineNumbers;
    //
    //    Popup menu
    //
    private JPopupMenu myPopupMenu;
    //
    //    Popup triggers
    //
    private int myPopupTriggerX;
    private int myPopupTriggerY;
    //
    //   Popup menu items
    //
    private JMenuItem mySetBreakpointItem;
    private JMenuItem myClearBreakpointItem;
    //
    //    Memory display style
    //
    private int myBytesToAddressRatio;
    private int myBytesAtOnce;
    //
    //    Button panel
    //
    private JPanel myButtonPanel;
   



    //
    // Accessors and Mutators
    //

//
//    Highlighter acc/mod
//
    public HashMap< Integer, Object > highlights()
    {
        return myHighlights;
        }

    public void setHighlights(HashMap< Integer, Object > highlights)
    {
        myHighlights = highlights;
        }

    public DefaultHighlighter.DefaultHighlightPainter highlighterPainter()
    {
        return myHighlighterPainter;
        }

    public void setHighlighterPainter(
        DefaultHighlighter.DefaultHighlightPainter highlighterPainter
        )
    {
        myHighlighterPainter = highlighterPainter;
        }
//
//    Jpanel acc/mod
//
    public JPanel getPanel()
    {
        return myPanel;
            }
    public void setPanel( JPanel other)
    {
        myPanel = other;
        }
//
//    JScrollPane acc/mod
//
    public JScrollPane getJScrollPane()
    {
        return myJScrollPane;
            }
    public void setJScrollPane( JScrollPane other)
    {
        myJScrollPane = other;
        }
//
//    Buton panel acc/mod
//
    public JPanel buttonPanel()
        {
            return myButtonPanel;
                }
        public void setButtonPanel( JPanel other)
        {
            myButtonPanel = other;
            }

//
//    Simulated machine acc/mod
//
    public VM252Model getVM252()
    {
        return myVM252;
        }
    public void setVM252(VM252Model other)
        {
            if (getVM252() != null)
                getVM252().detach(this);

            myVM252 = other;

            if (getVM252() != null)
                getVM252().attach(this);
            }
//
//    TextArea acc/mod
//
    public JTextArea textArea()
    {
        return myTextArea;
        }

    public void setTextArea(JTextArea textArea)
    {
        myTextArea = textArea;
        }
//
//    Line Number TextArea acc/mod
//
    public JTextArea lineNumbers()
    {
        return myLineNumbers;
        }

    public void setLineNumbers(JTextArea textArea)
    {
        myLineNumbers = textArea;
        }
//
//    Popup menu acc/mod
//
    public JPopupMenu popupMenu()
    {
        return myPopupMenu;
        }

    public void setPopupMenu(JPopupMenu popupMenu)
    {
        myPopupMenu = popupMenu;
        }

    public int popupTriggerX()
    {
        return myPopupTriggerX;
        }

    public void setPopupTriggerX(int popupTriggerX)
    {
        myPopupTriggerX = popupTriggerX;
        }

    public int popupTriggerY()
    {
        return myPopupTriggerY;
        }

    public void setPopupTriggerY(int popupTriggerY)
    {
        myPopupTriggerY = popupTriggerY;
        }
//
//    Breakpoint item acc/mod
//
    public JMenuItem setBreakpointItem()
    {
        return mySetBreakpointItem;
        }

    public void setSetBreakpointItem(JMenuItem setBreakpointItem)
    {
        mySetBreakpointItem = setBreakpointItem;
        }

    public JMenuItem clearBreakpointItem()
    {
        return myClearBreakpointItem;
        }

    public void setClearBreakpointItem(JMenuItem clearBreakpointItem)
    {
        myClearBreakpointItem = clearBreakpointItem;
        }
//
//    Memory output style acc/mod
//
    public int getByteToAddressRatio()
    {
        return myBytesToAddressRatio;
        }
    public void setByteToAddressRatio(int other)
    {
        if(other > 0)
            myBytesToAddressRatio = other;
        }
    public int getBytesAtOnce()
    {
        return myBytesAtOnce;
        }
    public void setBytesAtOnce(int other)
    {
        if(other > 0)
            myBytesAtOnce = other;
        }

    //
    // Ctors
    //

    public MemoryView(VM252Model simulatedMachine)
    {
        setPanel(new JPanel());
        setVM252(simulatedMachine);
        setHighlights(new HashMap< Integer, Object >());

        setHighlighterPainter(
            new DefaultHighlighter.DefaultHighlightPainter(new Color(255, 192, 192))
            );

       setTextArea(new JTextArea(16, 32));
       setLineNumbers( new JTextArea(16,4));

        MemoryViewChanger memoryPainter = new MemoryViewChanger();
        setBytesAtOnce(1);
        setByteToAddressRatio(1);
        memoryPainter.updateViewStyle(getBytesAtOnce(),getByteToAddressRatio());


        textArea().setEditable(false);
        lineNumbers().setBackground(Color.LIGHT_GRAY);
        lineNumbers().setEditable(false);
        lineNumbers().setSelectionColor(Color.LIGHT_GRAY);
        lineNumbers().setCaretColor(Color.LIGHT_GRAY);

        setPopupMenu(new JPopupMenu());

        JMenuItem alterMemoryByteItem = new JMenuItem("Alter byte contents");
        alterMemoryByteItem.addActionListener(new AlterMemoryByteAction());

        setSetBreakpointItem(new JMenuItem("Set breakpoint"));
        setBreakpointItem().addActionListener(new SetBreakpointAction());

        setClearBreakpointItem(new JMenuItem("Clear breakpoint"));
        clearBreakpointItem().addActionListener(new ClearBreakpointAction());

        popupMenu().add(alterMemoryByteItem);
        popupMenu().add(setBreakpointItem());

        textArea().add(popupMenu());

        textArea().addMouseListener(new PopupInitiator());
       // JTextArea testarea = new JTextArea();
        //testarea.append();
        JScrollPane scrollPane = new JScrollPane(textArea());
        scrollPane.setRowHeaderView(lineNumbers());
        setJScrollPane(scrollPane);
        
        getPanel().add(scrollPane);
        //add(scrollPane);

        //
        //    Radio buttons for memory view
        //
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 20,20));
        JButton button1 = new JButton("MI");
        JButton button2 = new JButton("MB");
        JButton button3 = new JButton("MB2");

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                memoryPainter.updateViewStyle(1,1);
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 memoryPainter.updateViewStyle(10,1);
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                memoryPainter.updateViewStyle(10,2);
            }
        });
        setButtonPanel(buttonPanel);

        }


    //
    // Highlighting Operations
    //

    public boolean isHighlighted(int lineNumber)
    {
        
        return highlights().containsKey(lineNumber);

        }

    public void addHighlight(int lineNumber, int startOfLine, int endOfLine)
    {
        
        try {

            Object newHighlight = textArea().getHighlighter().addHighlight(
                startOfLine,
                endOfLine,
                highlighterPainter()
                );
           
            highlights().put(lineNumber, newHighlight);

            }
        catch (BadLocationException e)
        {
            ; // do nothing:  should never happen
            }

        }

    public void removeHighlight(int lineNumber)
    {

        textArea().getHighlighter().removeHighlight(highlights().get(lineNumber));

        highlights().remove(lineNumber);

        }

    //
    // Line-Location Operations
    //

    private static Triple< Integer, Integer, Integer > determineClickedLineLocation(
        JTextArea textAreaClicked, int xCoordinateOfClick, int yCoordinateOfClick
        )
    {

        int textPosition = textAreaClicked.viewToModel2D(
            new Point2D.Double(xCoordinateOfClick, yCoordinateOfClick)
            );
        PlainDocument document = ((PlainDocument) (textAreaClicked.getDocument()));
        Element paragraphElement = document.getParagraphElement(textPosition);
        int startingOffsetOfLine = paragraphElement.getStartOffset();
        int endingOffsetOfLine = paragraphElement.getEndOffset();

        int lineNumber;

        try {
            lineNumber = textAreaClicked.getLineOfOffset(startingOffsetOfLine);
            }
        catch (BadLocationException e)
        {
            lineNumber = 0;
            }

        return
            new Triple< Integer, Integer, Integer >(
                lineNumber, startingOffsetOfLine, endingOffsetOfLine
                );

        }

    class MemoryViewChanger
    {

        public void updateViewStyle(int bytesAtOnce, int byteToAddressRatio)
        {
            

            //build string to change the content of textArea()
            String newMemory = "";
            String newLineNumbers = "";
            //for (int address = 1; address <= 8191;  address++)
           int address = 1;
            while(address <= 8191)
              {
               newLineNumbers += (address)+"";
               int bytesWrittenToCurrentLine = 0;
                while( bytesWrittenToCurrentLine < byteToAddressRatio && address <= 8191)
                {

                    
                    int bytesWrittenTogether = bytesAtOnce;
                    while(bytesWrittenTogether >0 && address <= 8191)
                    {
                        newMemory += getVM252().memoryByte(address)+"";
                        ++address;
                        --bytesWrittenTogether;

                    }
                    ++bytesWrittenToCurrentLine;
                    newMemory += "  ";
                }
                if (address != 8192)
                {
                    
                    newMemory += "\n";
                    newLineNumbers += "\n";
                }
            
            }
            textArea().setText(newMemory);
            lineNumbers().setText(newLineNumbers);
            



        }

    }



    class PopupInitiator extends MouseAdapter
    {

        @Override
        public void mousePressed(MouseEvent event)
        {

            showPopupIfTriggered(event);

            }

        @Override
        public void mouseReleased(MouseEvent event)
        {

            showPopupIfTriggered(event);

            }

        private void showPopupIfTriggered(MouseEvent event)
        {

            if (event.isPopupTrigger()) {

                setPopupTriggerX(event.getX());
                setPopupTriggerY(event.getY());

                Triple< Integer, Integer, Integer > clickedLineSpecifications
                    = determineClickedLineLocation(
                        textArea(), popupTriggerX(), popupTriggerY()
                        );
                int lineNumber = clickedLineSpecifications.first()+1;
                

                if (! highlights().containsKey(lineNumber)) {

                    popupMenu().remove(1);

                    popupMenu().add(setBreakpointItem());

                    }

                else {

                    popupMenu().remove(1);

                    popupMenu().add(clearBreakpointItem());

                    }

                popupMenu().show(event.getComponent(), event.getX(), event.getY());

                }

            }

        }


    private class SetBreakpointAction implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent event)
        {

            Triple< Integer, Integer, Integer > clickedLineSpecifications
                = determineClickedLineLocation(
                    textArea(), popupTriggerX(), popupTriggerY()
                    );
            //offset because address does not start at zero
            int lineNumber = clickedLineSpecifications.first()+1;
            int startOfLine = clickedLineSpecifications.second();
            int endOfLine = clickedLineSpecifications.third();

            addHighlight(lineNumber, startOfLine, endOfLine);
            

            }

        }


    private class ClearBreakpointAction implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent event)
        {
           

            Triple< Integer, Integer, Integer > clickedLineSpecifications
                = determineClickedLineLocation(
                    textArea(), popupTriggerX(), popupTriggerY()
                    );
            int lineNumber = clickedLineSpecifications.first()+1;
            

            removeHighlight(lineNumber);

            }

        }


    private class AlterMemoryByteAction implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent event)
        {
                 Triple< Integer, Integer, Integer > clickedLineSpecifications
                    = determineClickedLineLocation(
                    textArea(), popupTriggerX(), popupTriggerY()
                    );

                    //
                    //      we left off here, we need to implement some kind of button with a scanner in it to take in user input, but we can now
                    //      find out where a specific change was made regardless of what kind of action it
                    //      is because popupTriggerX is a class variable of
                    //      The memory! How input works will be hairy, and also getting
                    //      the update function to work properly might take some muscle, Ready by tonight????
                    //

                    //
                    //      Create a dialog that will be evaluated to a string
                    //

                    byte inputAsBytes = 0;

                    

                    Frame dialog = new Frame();
                    dialog.setLocationRelativeTo(getComponentAt(popupTriggerX(),popupTriggerY()));
                    String input = (String)JOptionPane.showInputDialog(
                    dialog,
                    "Please enter new memory byte:\n",
                    "Memory byte input...",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "");

                    try
                    {
                        if(input != null)
                            inputAsBytes = Byte.parseByte(input.toUpperCase());
                        if(inputAsBytes < 0)
                            throw new NumberFormatException();
                    }
                    catch (NumberFormatException conversionToIntFailed)
                    {
                    //
                    //  Attempt to translate into hexidecimal
                    //
                        try
                        {
                            inputAsBytes = Byte.parseByte(input.toUpperCase(),16);
                            if(inputAsBytes < 0)
                                throw new NumberFormatException();
                        }
                        //
                        //  produce error dialog
                        //
                        catch (NumberFormatException conversionToIntAndHexFailed)
                        {
                            JOptionPane.showMessageDialog(
                            new Frame(),
                            "Error: bad input...\n1)    Numeric value cannot be outside of bounds [0-127] (inclusive)"
                            );
                        }
                    }

                    getVM252().setMemoryByte(clickedLineSpecifications.first(), inputAsBytes);


            }
        }



        @Override
        public void update(){
            ;//do nothing
        }

        @Override
        public void updateProgramCounter(){

            ;//scan through the breakpointed lines, if it is cointained in the list update the change in the VM252
            //yess yess yta;slkdfj a;lkdsjf ;laskjdf ;alskjdhf;akl hd;klafjs df;lthis will work 
            if(isHighlighted(getVM252().programCounter()))
            {
                getVM252().setStoppedStatus(StoppedCategory.stopped);
            }

        }

        @Override
        public void updateAccumulator(){

            ;//do nothing

        }

        @Override
        public void updateStoppedStatus(){
            ;
            /* 
            System.out.println("Stopped Status changeged, refreshing contents of memory");
             MemoryViewChanger refreshContents = new MemoryViewChanger();
             refreshContents.updateViewStyle(getByteToAddressRatio(),getBytesAtOnce());
             */

        }

        @Override
        public void updateMemory(int changedAddress){
            
            //
            //  Use newlines to determine the position of the memory (Stirng parsing, really tricky)
            //

            String memoryAsString = textArea().getText();
            int linesSearched = 0;
            int charsSearched = 0;
            int charsThisLine = 0;
            while(linesSearched <= changedAddress){
                
                
                if(memoryAsString.charAt(charsSearched) == '\n') {
                    ++linesSearched;
                    if(linesSearched <= changedAddress)
                        charsThisLine = 0;
                }
                else 
                    ++charsThisLine;

                ++charsSearched;
            }
            //System.out.print(getVM252().memoryByte(changedAddress));
            if (0 <= (int)getVM252().memoryByte(changedAddress)){
            textArea().replaceRange(""+Integer.toHexString((int)getVM252().memoryByte(changedAddress)),
                                    (charsSearched -1)- charsThisLine ,charsSearched-1);
            }
            else {
                String negativeByteString = ""+Integer.toHexString((int)getVM252().memoryByte(changedAddress));
                textArea().replaceRange( negativeByteString.substring(negativeByteString.length()-2),
                                        (charsSearched -1)- charsThisLine ,charsSearched-1);
            }

            

            //
            //  Replace the line at the specified adresss with the new text
            //
            //textArea().replaceRange(Integer.toHexString((int)getVM252().memoryByte(changedAddress)), clickedLineSpecifications.second(),clickedLineSpecifications.third()-1);




        }



}

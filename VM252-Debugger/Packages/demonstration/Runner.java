package demonstration;


import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.function.Consumer;

import vm252simulation.VM252Model;
import vm252simulation.VM252View;


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


public class Runner {
    private static String myString;
    private static PrintStream machineOutputStream;
    private StringBuilder outputBuffer;
    private VM252Model vm252Model;

    private void setString(String other) {
        myString = other;
    }

    private void setOutput(PrintStream other) {
        machineOutputStream = other;
    }

    public static String getString() {
        return myString;
    }

    public static PrintStream getMachineOutput() {
        return machineOutputStream;
    }

    public String getOutput() {
        return outputBuffer.toString();
    }

    public VM252Model getVM252Model() {
        return vm252Model;
    }

    public void run(Consumer<String> outputConsumer) {
        outputConsumer.accept("Running with input: " + myString + "\n");
    }

    public Runner() {
        outputBuffer = new StringBuilder();
        vm252Model = new VM252Model();
    }

    public static void main() throws IOException {
        Scanner inputStream = new Scanner(System.in);

        Runner runner = new Runner();

        VM252Model simulatedMachine = runner.getVM252Model();

        simulatedMachine.attach(new AccumulatorPrinter(simulatedMachine));
        simulatedMachine.attach(new ProgramCounterPrinter(simulatedMachine));
        simulatedMachine.attach(new MemoryBytePrinter(simulatedMachine));
        simulatedMachine.attach(new StopAnnouncer(simulatedMachine));

        DemonstrationController simulator = new DemonstrationController(simulatedMachine);

        String objectFileName;

        objectFileName = inputStream.next();
        System.out.println(objectFileName);

        simulator.loadAndRun(objectFileName, inputStream, System.out);

    }
}
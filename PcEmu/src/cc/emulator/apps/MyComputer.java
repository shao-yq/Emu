package cc.emulator.apps;

import cc.emulator.computer.IBMPC5150;
import cc.emulator.core.computer.Computer;
import cc.emulator.core.computer.ProgramMemoryInfo;
import cc.emulator.core.computer.swing.Display;
import cc.emulator.core.cpu.Instruction;
import cc.emulator.core.cpu.InstructionDecoder;
import cc.emulator.core.cpu.InstructionQueue;
import cc.emulator.x86.i8086.Decoder8086;
import cc.emulator.x86.i8086.instruction.MOV;
import cc.emulator.x86.i8086.instruction.MovImmediateToRegister;
import fr.neatmonster.ibmpc.IBMCGA;
import cc.emulator.ui.swing.InstructionPane;

import javax.swing.*;
import java.util.Vector;

/**
 * @author Shao Yongqing
 * Date: 2017/7/25.
 */
public class MyComputer {
    Computer computer ;
    InstructionPane instructinPane;
    public static void main(final String[] args) {
        MyComputer myComputer = new MyComputer();
        myComputer.initCpuEmuUi();

        String configFile="/IBMPc.properties";
        myComputer.initPcUi(configFile);

    }

     void initPcUi(String configFile){
        computer = new IBMPC5150(configFile);
        // Power supply
        computer.powerUp();

        IBMCGA display = (IBMCGA) computer.getDisplay();

        JFrame pcFrame = new JFrame("IBMPC5150");
        pcFrame.add(display);
        pcFrame.addKeyListener(display);
        pcFrame.pack();
        pcFrame.setVisible(true);
        pcFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        new Thread() {
            @Override
            public void run() {
                computer.start();
            }
        }.start();


         // prepare instructions dump from the computer's memory
         ProgramMemoryInfo programMemoryInfo = computer.getProgramMemoryInfo("bootloader");
         int memoryBase [] = computer.getMainBoard().getMemoryManager().getMemoryBase();
         int count = 10;
         Vector<Instruction> instructions = decode(programMemoryInfo, memoryBase, count);
         instructinPane.setInstructions(instructions);
    }

    private Vector<Instruction> decode(ProgramMemoryInfo programMemoryInfo, int[] memoryBase, int count) {
        Vector<Instruction> instructions = new Vector<Instruction>();
        final int queueSize = computer.getMainBoard().getCpu().getBusInterfaceUnit().getInstructionQueue().getQueueSize();
        InstructionQueue instructionQueue = new InstructionQueue(){
            int[] queue = new int[queueSize];
            @Override
            public void reset(){
                //for (int i = 0; i < 6; i++)
                //    queue[i] = 0;
                current=0;
            }
            int current;
            @Override
            public void fillInstructionQueue(int instruction){
                queue[current]=instruction;
                current++;
            }

            @Override
            public int[] getQueue() {
                return queue;
            }

            @Override
            public int getQueueSize() {
                return queueSize;
            }
        };


        int pc = programMemoryInfo.getBase();
        int programSize = programMemoryInfo.getSize();
        InstructionDecoder decoder= new Decoder8086(); //computer.getMainBoard().getCpu().getInstructionUnit().getDecoder();
        int current = 0;
        int memoryUpper = pc+programSize;
        while(pc<memoryUpper){
            int tempPc = pc;
            // reset the queue fisrt.
            instructionQueue.reset();
            // Fill the queue
            for(int i=0; i<queueSize; i++){
                instructionQueue.fillInstructionQueue(memoryBase[pc]);

                pc++;
                if(pc>=memoryUpper)
                    break;
            }
            // Decode
            Instruction instruction = decoder.decode(instructionQueue);
            pc = tempPc + instruction.getLength();

            instructions.add(instruction);
            current ++;
            if(current==count)
                break;
        }


        return instructions;
    }

    void initCpuEmuUi(){
        JFrame cpuFrame = new JFrame("CPU x86");

        instructinPane = new InstructionPane();
        instructinPane.initUi();

         Vector<Instruction> instructions = new Vector<>();
         MovImmediateToRegister mov = new MovImmediateToRegister();
         mov.decodeMe(new int[]{0xB8,0x40,0x51},0);
         instructions.add(mov);
        instructinPane.setInstructions(instructions);
        //cpuFrame.add(instructinPane);
        cpuFrame.setContentPane(instructinPane);
        // cpuFrame.addKeyListener(instructinPane);
        cpuFrame.pack();
        cpuFrame.setVisible(true);
        cpuFrame.setBounds(100, 100, 300, 200);
        cpuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}

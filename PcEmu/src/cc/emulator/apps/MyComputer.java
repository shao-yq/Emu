package cc.emulator.apps;

import cc.emulator.computer.IBMPC5150;
import cc.emulator.core.computer.Computer;
import cc.emulator.core.computer.ProgramMemoryInfo;
import cc.emulator.core.computer.swing.Display;
import cc.emulator.core.cpu.Instruction;
import cc.emulator.core.cpu.InstructionDecoder;
import cc.emulator.core.cpu.InstructionQueue;
import cc.emulator.core.cpu.register.DividableRegister;
import cc.emulator.ui.swing.MemoryPane;
import cc.emulator.ui.swing.RegisterPane;
import cc.emulator.x86.i8086.Decoder8086;
import cc.emulator.x86.i8086.instruction.MOV;
import cc.emulator.x86.i8086.instruction.MovImmediateToRegister;
import fr.neatmonster.ibmpc.IBMCGA;
import cc.emulator.ui.swing.InstructionPane;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * @author Shao Yongqing
 * Date: 2017/7/25.
 */
public class MyComputer {
    Computer computer ;
    InstructionPane instructionPane;
    MemoryPane memoryPane;
    RegisterPane registerPane;

    public static void main(final String[] args) {
        String configFile="/IBMPc.properties";
        MyComputer myComputer = new MyComputer(configFile);

        // myComputer.initPcUi(configFile);

        myComputer.initCpuEmuUi();

    }

    public MyComputer(String configFile){
        initPcUi(configFile);
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
        instructionPane = new InstructionPane();
        instructionPane.initUi();
        // prepare instructions dump from the computer's memory
        ProgramMemoryInfo programMemoryInfo = computer.getProgramMemoryInfo("bootloader");
        int memoryBase [] = computer.getMainBoard().getMemoryManager().getMemoryBase();
        int count = 20;
        Vector<Instruction> instructions = decode(programMemoryInfo, memoryBase, count);
        instructionPane.setInstructions(instructions);

        JFrame cpuFrame = new JFrame("CPU x86");




//         Vector<Instruction> instructions = new Vector<>();
//         MovImmediateToRegister mov = new MovImmediateToRegister();
//         mov.decodeMe(new int[]{0xB8,0x40,0x51},0);
//         instructions.add(mov);
//        instructionPane.setInstructions(instructions);
        //cpuFrame.add(instructinPane);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(instructionPane, BorderLayout.WEST);

        memoryPane =  new MemoryPane();
        contentPane.add(memoryPane, BorderLayout.EAST);

        registerPane =  new RegisterPane();
        contentPane.add(registerPane, BorderLayout.CENTER);
        registerPane.setAccumulators((DividableRegister[]) computer.getMainBoard().getCpu().getExecutionUnit().getGeneralRegisters());
        registerPane.setIndexerPointers(computer.getMainBoard().getCpu().getExecutionUnit().getPointerIndexers());

        cpuFrame.setContentPane(contentPane);
        // cpuFrame.addKeyListener(instructinPane);
        cpuFrame.pack();
        cpuFrame.setBounds(100, 100, 800, 600);
        cpuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cpuFrame.setVisible(true);

    }

}

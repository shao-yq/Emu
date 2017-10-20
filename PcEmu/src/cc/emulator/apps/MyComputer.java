package cc.emulator.apps;

import cc.emulator.computer.IBMPC5150;
import cc.emulator.core.computer.Computer;
import cc.emulator.core.computer.ProgramMemoryInfo;
import cc.emulator.core.cpu.Instruction;
import cc.emulator.core.cpu.InstructionDecoder;
import cc.emulator.core.cpu.InstructionQueue;
import cc.emulator.core.cpu.register.DividableRegister;
import cc.emulator.core.cpu.register.GeneralRegister;
import cc.emulator.ui.swing.MemoryPane;
import cc.emulator.ui.swing.RegisterPane;
import cc.emulator.x86.i8086.Decoder8086;
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
        JMenuBar menuBar = createMenuBar();
        JToolBar toolBar = createToolBar();
        cpuFrame.setJMenuBar(menuBar);
        
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(toolBar, BorderLayout.NORTH);
        contentPane.add(instructionPane, BorderLayout.WEST);

        memoryPane =  new MemoryPane();
        contentPane.add(memoryPane, BorderLayout.EAST);

        registerPane =  new RegisterPane();
        contentPane.add(registerPane, BorderLayout.CENTER);

        // Dividable Register list
        GeneralRegister[] dividableRegisters = computer.getMainBoard().getCpu().getExecutionUnit().getGeneralRegisters();
        Vector<DividableRegister> registers = new Vector<DividableRegister>();
        for(int i=0; i<dividableRegisters.length; i++){
            if(dividableRegisters[i] instanceof DividableRegister)
                registers.add((DividableRegister)dividableRegisters[i]);
        }

        registerPane.setAccumulators(registers);

        registerPane.setPointerIndexers(computer.getMainBoard().getCpu().getExecutionUnit().getPointerIndexers());
        registerPane.setSegmentRegisters(computer.getMainBoard().getCpu().getBusInterfaceUnit().getSegmentRegisters());

        cpuFrame.setContentPane(contentPane);
        // cpuFrame.addKeyListener(instructinPane);
        cpuFrame.pack();
        cpuFrame.setBounds(100, 100, 800, 600);
        cpuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cpuFrame.setVisible(true);

    }

    JMenuBar createMenuBar(){
        JPopupMenu pop;
        JMenuItem item1;
        JMenuItem item2;
        JMenuBar menuBar = new JMenuBar();
        JMenu menu1=new JMenu("菜单1");
        JMenu menu2=new JMenu("菜单2");
        JMenu menu3=new JMenu("菜单3");
        JMenu menu4=new JMenu("菜单4");
        JMenu menu5=new JMenu("菜单5");

        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(menu3);
        menuBar.add(menu4);
        menuBar.add(menu5);

        item1=new JMenuItem("子菜单1");
        item2=new JMenuItem("子菜单2");

        JMenuItem item3=new JMenuItem("子菜单3");
        JMenuItem item4=new JMenuItem("子菜单4");
        JMenuItem item5=new JMenuItem("子菜单5");
        JMenuItem item6=new JMenuItem("子菜单6");
        JMenuItem item7=new JMenuItem("子菜单7");
        JMenuItem item8=new JMenuItem("子菜单8");
        JMenuItem item9=new JMenuItem("子菜单9");
        JMenuItem item10=new JMenuItem("子菜单10");
        JMenuItem item11=new JMenuItem("子菜单11");
        JMenuItem item12=new JMenuItem("子菜单12");

        menu1.add(item1);
        menu1.addSeparator();
        menu1.add(item2);
        menu1.addSeparator();
        menu1.add(item3);

        menu2.add(item4);
        menu2.addSeparator();
        menu2.add(item5);

        menu3.add(item6);
        menu3.addSeparator();
        menu3.add(item7);

        menu4.add(item8);
        menu4.addSeparator();
        menu4.add(item9);
        menu4.addSeparator();
        menu4.add(item10);

        menu5.add(item11);
        menu5.addSeparator();
        menu5.add(item12);

        return menuBar;
    }

    JToolBar createToolBar(){
        JToolBar toolBar = new JToolBar();
        JButton button1 = new JButton("工具1");
        JButton button2 = new JButton("工具2");
        JButton button3 = new JButton("工具3");

        toolBar.add(button1);
        toolBar.add(button2);
        toolBar.add(button3);
        return toolBar;
    }

}

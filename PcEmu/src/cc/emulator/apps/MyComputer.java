package cc.emulator.apps;

import cc.emulator.computer.IBMPC5150;
import cc.emulator.core.MemoryManager;
import cc.emulator.core.computer.Computer;
import cc.emulator.core.computer.ProgramMemoryInfo;
import cc.emulator.core.cpu.*;
import cc.emulator.core.cpu.register.DividableRegister;
import cc.emulator.core.cpu.register.GeneralRegister;
import cc.emulator.ui.swing.MemoryPane;
import cc.emulator.ui.swing.RegisterPane;
import cc.emulator.x86.i8086.Decoder8086;
import fr.neatmonster.ibmpc.IBMCGA;
import cc.emulator.ui.swing.InstructionPane;
import cc.emulator.core.computer.swing.Keyboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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
        myComputer.initUi();
    }
    void initUi(){
        JFrame cpuFrame = new JFrame("CPU x86");
        JPanel cpuContent = initCpuEmuUi();

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(cpuContent, BorderLayout.CENTER);
        IBMCGA display = (IBMCGA) computer.getDisplay();
//        cpuFrame.addKeyListener(display);
//
//        contentPanel.add(display,BorderLayout.EAST);

        computer.getCpu().setStepModeListener(stepModeListener);

        initAppUi(cpuFrame, contentPanel);

        new Thread() {
            @Override
            public void run() {
                computer.start();
            }
        }.start();


    }
    void initAppUi(JFrame cpuFrame,JPanel contentPane ){

        JMenuBar menuBar = createMenuBar();
        //JToolBar toolBar = createToolBar();
        cpuFrame.setJMenuBar(menuBar);

        cpuFrame.setContentPane(contentPane);
        // cpuFrame.addKeyListener(instructinPane);
        cpuFrame.pack();
        cpuFrame.setBounds(100, 100, 800, 600);
        cpuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cpuFrame.setVisible(true);

    }

    public MyComputer(String configFile){
        initPcUi(configFile);
    }

     void initPcUi(String configFile){
         computer = new IBMPC5150(configFile);
         // Power supply
         computer.powerUp();

        IBMCGA display = (IBMCGA) computer.getDisplay();

        Keyboard keyBoard = (Keyboard) computer.getKeyBoard();

        JFrame pcFrame = new JFrame("IBMPC5150");
        pcFrame.add(display);
        pcFrame.addKeyListener(keyBoard);
        pcFrame.pack();
        pcFrame.setVisible(true);
        pcFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    private Vector<Instruction> decode(Cpu cpu, ProgramMemoryInfo programMemoryInfo, int[] memoryBase, int count) {
        Vector<Instruction> instructions = new Vector<Instruction>();
        final int queueSize = cpu.getBusInterfaceUnit().getInstructionQueue().getQueueSize();
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
        int offset = 0;
        int base = programMemoryInfo.getBase();
        int programSize = programMemoryInfo.getSize();
        int memoryUpper = base+programSize;

        InstructionDecoder decoder= cpu.getInstructionUnit().createDecoder(); //new Decoder8086();
        for(int i=0; i<count; i++) {
            int currentAddr = cpu.currentAddress();
            if(currentAddr+offset+cpu.getBusInterfaceUnit().getInstructionQueue().getQueueSize() >=memoryBase.length )
                break;

            // Featch raw instruction
            cpu.fetchRawInstructions(offset, instructionQueue);
            // Decode
            Instruction instruction = decoder.decode(instructionQueue);
            offset += instruction.getLength();

            instructions.add(instruction);
        }

        return instructions;
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

    StepModeListener stepModeListener=null;

    JPanel initCpuEmuUi(){
        Cpu cpu = computer.getMainBoard().getCpu();


        MemoryManager memoryManager = computer.getMainBoard().getMemoryManager();
        // prepare instructions dump from the computer's memory
        ProgramMemoryInfo programMemoryInfo = computer.getProgramMemoryInfo("bootloader");
        int memoryBase [] = memoryManager.getMemoryBase();
        int count = 20;

        instructionPane = new InstructionPane(cpu, programMemoryInfo, memoryBase);
        instructionPane.initUi();
        instructionPane.refreshUI();

        //Vector<Instruction> instructions = decode(programMemoryInfo, memoryBase, count);

        //Vector<Instruction> instructions = decode(cpu,programMemoryInfo, memoryBase, count);
        //instructionPane.setInstructions(instructions);


//        JFrame cpuFrame = new JFrame("CPU x86");
//        JMenuBar menuBar = createMenuBar();
//        cpuFrame.setJMenuBar(menuBar);
        JToolBar toolBar = createToolBar();

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(toolBar, BorderLayout.NORTH);
        contentPane.add(instructionPane, BorderLayout.WEST);

        memoryPane= new MemoryPane(memoryManager.getMemoryBase(),0, 20);
        contentPane.add(memoryPane, BorderLayout.CENTER);

        registerPane =  new RegisterPane();
        contentPane.add(registerPane, BorderLayout.EAST);

        // Dividable Register list
        GeneralRegister[] dividableRegisters = cpu.getExecutionUnit().getGeneralRegisters();
        Vector<DividableRegister> registers = new Vector<DividableRegister>();
        for(int i=0; i<dividableRegisters.length; i++){
            if(dividableRegisters[i] instanceof DividableRegister)
                registers.add((DividableRegister)dividableRegisters[i]);
        }

        registerPane.setAccumulators(registers);


        registerPane.setPointerIndexers(cpu.getPointerIndexers());
        registerPane.setSegmentRegisters(cpu.getSegmentRegisters());
        registerPane.setProgramCounter(cpu.getProgramCounter());
        registerPane.setStatusRegister(cpu.getStatusRegister());

//        cpuFrame.setContentPane(contentPane);
//        // cpuFrame.addKeyListener(instructinPane);
//        cpuFrame.pack();
//        cpuFrame.setBounds(100, 100, 800, 600);
//        cpuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        cpuFrame.setVisible(true);

        stepModeListener = new StepModeListener() {
            @Override
            public void onStepping(Cpu cpu) {
                refreshUI();
            }
        };
        return contentPane;
    }

    void refreshUI(){
//        instructionPane.invalidate();
//        memoryPane.invalidate();
//        registerPane.invalidate();

//        //  Update Instruction list
//        ProgramMemoryInfo programMemoryInfo = computer.getProgramMemoryInfo("bootloader");
//        int memoryBase [] = computer.getMainBoard().getMemoryManager().getMemoryBase();
//        int count = 20;
//        Vector<Instruction> instructions = decode(programMemoryInfo, memoryBase, count);
//        instructionPane.setInstructions(instructions);

        // Update memory

        // Update registers

        instructionPane.refreshUI();
        memoryPane.repaint();
        registerPane.repaint();
    }

    JMenuBar createMenuBar(){
        JPopupMenu pop;
        JMenuItem item1;
        JMenuItem item2;
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile=new JMenu("File");
        JMenu menuEdit=new JMenu("Edit");
        JMenu menuRun=new JMenu("Run");
        JMenu menuOption=new JMenu("Option");
        JMenu menuWindow=new JMenu("Window");

        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuRun);
        menuBar.add(menuOption);
        menuBar.add(menuWindow);

        item1=new JMenuItem("子菜单1");
        item2=new JMenuItem("子菜单2");

        JMenuItem item3=new JMenuItem("子菜单3");
        JMenuItem item4=new JMenuItem("子菜单4");
        JMenuItem item5=new JMenuItem("子菜单5");
        JMenuItem itemDebug=new JMenuItem("Step Mode");
        JMenuItem itemNextStep=new JMenuItem("Next Step");
        JMenuItem item8=new JMenuItem("子菜单8");
        JMenuItem item9=new JMenuItem("子菜单9");
        JMenuItem item10=new JMenuItem("子菜单10");
        JMenuItem item11=new JMenuItem("子菜单11");
        JMenuItem item12=new JMenuItem("子菜单12");

        itemDebug.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                computer.getMainBoard().getCpu().toggleStep();
            }

            @Override
            public Object getValue(String key) {
                if (key.equalsIgnoreCase("Name"))
                    return "Step Mode";

                return super.getValue(key);
            }
        });

        itemNextStep.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                computer.getCpu().nextStep();
            }

            @Override
            public Object getValue(String key) {
                if (key.equalsIgnoreCase("Name"))
                    return "Next Step";

                return super.getValue(key);
            }
        });
        menuFile.add(item1);
        menuFile.addSeparator();
        menuFile.add(item2);
        menuFile.addSeparator();
        menuFile.add(item3);

        menuEdit.add(item4);
        menuEdit.addSeparator();
        menuEdit.add(item5);

        menuRun.add(itemDebug);
        menuRun.addSeparator();
        menuRun.add(itemNextStep);

        menuOption.add(item8);
        menuOption.addSeparator();
        menuOption.add(item9);
        menuOption.addSeparator();
        menuOption.add(item10);

        menuWindow.add(item11);
        menuWindow.addSeparator();
        menuWindow.add(item12);

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

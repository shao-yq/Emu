package cc.emulator.apps;

import cc.emulator.computer.IBMPC5150;
import cc.emulator.core.computer.Computer;
import cc.emulator.core.computer.swing.Display;
import fr.neatmonster.ibmpc.IBMCGA;
import cc.emulator.ui.swing.InstructionPane;

import javax.swing.*;

/**
 * @author Shao Yongqing
 * Date: 2017/7/25.
 */
public class MyComputer {
    public static void main(final String[] args) {
        MyComputer myComputer = new MyComputer();
        myComputer.initCpuEmuUi();

        String configFile="/IBMPc.properties";
        myComputer.initPcUi(configFile);
    }
     void initPcUi(String configFile){
        final Computer computer = new IBMPC5150(configFile);
        // Power supply
        computer.powerUp();

        IBMCGA display = (IBMCGA) computer.getDisplay();

        JFrame pcFrame = new JFrame("IBMPC5150");
        pcFrame.add(display);
        pcFrame.addKeyListener(display);
        pcFrame.pack();
        pcFrame.setVisible(true);
        pcFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        computer.start();
    }
     void initCpuEmuUi(){
        JFrame cpuFrame = new JFrame("CPU x86");

        InstructionPane instructinPane = new InstructionPane();
        instructinPane.initUi();

        //cpuFrame.add(instructinPane);
        cpuFrame.setContentPane(instructinPane);
        // cpuFrame.addKeyListener(instructinPane);
        cpuFrame.pack();
        cpuFrame.setVisible(true);
        cpuFrame.setBounds(100, 100, 300, 200);
        cpuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}

package cc.emulator.apps;

import cc.emulator.computer.IBMPC5150;
import cc.emulator.core.computer.Computer;

/**
 * @author Shao Yongqing
 * Date: 2017/7/25.
 */
public class MyComputer {
    public static void main(final String[] args) {
        String configFile="/IBMPc.properties";
        final Computer computer = new IBMPC5150(configFile);
        // Power supply
        computer.powerUp();
    }
}

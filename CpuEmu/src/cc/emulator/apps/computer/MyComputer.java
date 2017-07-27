package cc.emulator.apps.computer;

import cc.emulator.core.computer.Computer;
import cc.emulator.computer.IBMPC5150;

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

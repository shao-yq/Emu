package cc.emulator.apps.computer;

import cc.emulator.core.computer.Computer;
import cc.emulator.core.computer.PersonalComputer;

/**
 * @author Shao Yongqing
 * Date: 2017/7/25.
 */
public class MyComputer {
    public static void main(final String[] args) {
        String configFile="/IBMPc.properties";
        final Computer computer = new PersonalComputer(configFile);
        // Power supply
        computer.powerUp();
    }
}

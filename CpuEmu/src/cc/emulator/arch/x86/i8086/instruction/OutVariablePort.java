package cc.emulator.arch.x86.i8086.instruction;

import cc.emulator.arch.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class OutVariablePort extends Instruction8086 {
    public OutVariablePort(int[] raw) {
        super(raw);
    }
}

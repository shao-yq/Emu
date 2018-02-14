package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2018/2/14.
 */
public class Call extends Instruction8086 {
    public Call() {
    }

    public Call(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    @Override
    public String getMnemonic() {
        return "CALL";
    }
}

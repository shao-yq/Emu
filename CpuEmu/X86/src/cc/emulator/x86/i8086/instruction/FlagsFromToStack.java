package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class FlagsFromToStack extends Instruction8086 {
    public FlagsFromToStack(int[] raw, int startIndex) {
        super(raw, startIndex);
    }
}

package cc.emulator.arch.arm.instruction;

import cc.emulator.arch.arm.ArmInstruction;

/**
 * @author Shao Yongqing
 * Date: 2017/9/22.
 */
public class UnconditionalInstruction extends ArmInstruction{
    @Override
    public int getOpCode() {
        return 0;
    }

    @Override
    public boolean hasOpcode(int[] queue, int startIndex) {
        return false;
    }

}

package cc.emulator.arch.arm.instruction;

import cc.emulator.arch.arm.ArmInstruction;

/**
 * @author Shao Yongqing
 * Date: 2017/8/18.
 */
public  class DataProcessMisc extends ArmInstruction {

    public DataProcessMisc(int[] queue) {
        super(queue);
    }

    @Override
    public boolean hasOpcode(int[] queue, int startIndex) {
        return false;
    }
}

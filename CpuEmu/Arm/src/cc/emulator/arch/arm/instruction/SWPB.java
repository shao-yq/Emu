package cc.emulator.arch.arm.instruction;

import cc.emulator.core.cpu.Instruction;

/**
 * @author Shao Yongqing
 * Date: 2017/10/17.
 */
public class SWPB extends Swap {
    public SWPB(int rawInstruction) {
        super(new int[]{rawInstruction});
    }

}

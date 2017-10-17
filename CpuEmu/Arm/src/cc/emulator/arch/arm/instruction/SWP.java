package cc.emulator.arch.arm.instruction;

import cc.emulator.core.cpu.Instruction;

/**
 * @author Shao Yongqing
 * Date: 2017/10/17.x
 */
public class SWP extends  Swap {
    public SWP(int rawInstruction) {
        super(new int[]{rawInstruction});
    }


}

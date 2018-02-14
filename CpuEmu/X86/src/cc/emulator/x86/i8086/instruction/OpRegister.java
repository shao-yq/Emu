package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;
import cc.emulator.x86.i8086.IntelInstructionHelper;

/**
 * @author Shao Yongqing
 * Date: 2018/2/14.
 */
public class OpRegister extends Instruction8086 {
    public OpRegister() {
    }

    public OpRegister(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    public void decode(int[] raw, int startIndex) {
        decode(raw, 1, startIndex);
        reg = op & 0b111;
    }

    @Override
    protected String getOperandPart() {
        return IntelInstructionHelper.getRegMnemonic(reg,Instruction8086.W);
    }

}

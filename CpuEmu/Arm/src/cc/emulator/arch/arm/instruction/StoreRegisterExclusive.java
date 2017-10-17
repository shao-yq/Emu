package cc.emulator.arch.arm.instruction;

import cc.emulator.arch.arm.ArmInstruction;

/**
 * @author Shao Yongqing
 * Date: 2017/10/17.
 */
public class StoreRegisterExclusive extends ArmInstruction {
    public StoreRegisterExclusive(int rawInstruction) {
        super(new int[]{rawInstruction});
    }

    @Override
    public int getOpCode() {
        return 0;
    }
    protected int getCategory(){
        return 0b0001;
    }

    protected int getOp(){
        return 0b1001;
    }
    protected int getOp2(){
        return 0b1001;
    }

    @Override
    public boolean hasOpcode(int[] queue, int startIndex) {
        int raw = queue[startIndex];
        int cond = (raw>>28)&0xf;
        int category = (raw>>24) & 0b1111;
        int op = (raw>>20) & 0b1111;
        int op2 = (raw>>4) & 0b1111;
        if((cond!=0xf)
                && (op == getOp())
                &&(category == getCategory())
                && (op2==getOp2()))
            return true;

        return false;
    }

    @Override
    public void decode(int[] raw, int startIndex) {
        super.decode(raw, startIndex);

        Rn = (rawInstruction>>16) & 0xf;
        Rd = (rawInstruction>>12) & 0xf;
        Rt = (rawInstruction>> 0) & 0xf;
    }
}

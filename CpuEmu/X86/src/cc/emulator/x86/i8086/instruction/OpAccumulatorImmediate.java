package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2018/2/14.
 */
public abstract class OpAccumulatorImmediate extends Instruction8086 {
    public final static int AL_IMMED8   = 0;
    public final static int AX_IMMED16  = 1;

    public OpAccumulatorImmediate() {
    }

    public OpAccumulatorImmediate(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    public void decode(int[] raw, int startIndex) {
        decode(raw,1,startIndex);
        immediate = raw[1+startIndex];
        incLength(1);
        if(isAxImmed16(op)){
            immediate |= (raw[2+startIndex]<<8);
            incLength(1);
        }
    }
    @Override
    protected String getOperandPart() {
        StringBuffer asm = new StringBuffer();
        switch (oprandMode(op)) {
            // Immediate to Accumulator
            case AL_IMMED8 : //  OP AL,IMMED8
                asm.append("AL");
                break;
            case AX_IMMED16: //  OP AX,IMMED16
                asm.append("AX");
        }
        asm.append(", "+immediate);

        return asm.toString();
    }

    @Override
    public int getClocks() {
        return 4;
    }

    abstract int oprandMode(int op);

    protected abstract boolean isAxImmed16(int op);

}

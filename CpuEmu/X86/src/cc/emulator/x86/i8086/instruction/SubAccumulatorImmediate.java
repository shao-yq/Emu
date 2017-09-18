package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class SubAccumulatorImmediate extends Instruction8086 {
    public SubAccumulatorImmediate(int[] raw, int startIndex) {
        super(raw, startIndex);
        immediate = raw[1+startIndex];
        incLength(1);
        if(op == SUB_AX_IMMED16){
            immediate |= (raw[2+startIndex]<<8);
            incLength(1);
        }
    }

    public static boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch(raw) {
            /*
             * SUB destination,source
             *
             * The source operand is subtracted from the destination operand,
             * and the result replaces the destination operand. The operands may
             * be bytes or words. Both operands may be signed or unsigned binary
             * numbers. SUB updates AF, CF, OF, PF, SF and ZF.
             */
            // Immediate from Accumulator
            case SUB_AL_IMMED8 : //  0x2c: // SUB AL,IMMED8
            case SUB_AX_IMMED16: //  0x2d: // SUB AX,IMMED16
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 4;
    }
}

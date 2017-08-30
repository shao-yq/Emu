package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
/*
 * OR destination,source
 *
 * OR performs the logical "inclusive or" of the two operands (byte
 * or word) and returns the result to the destination operand. A bit
 * in the result is set if either or both corresponding bits of the
 * original operands are set; otherwise the result bit is cleared.
 */
public class OrAccumulatorImmediate extends Instruction8086 {
    public OrAccumulatorImmediate(int[] raw) {
        super(raw);
        immediate = raw[1];
        incLength(1);
        if(op == OR_AX_IMMED16){
            immediate |= (raw[2]<<8);
            incLength(1);
        }
    }

    public static boolean hasOpcode(int raw) {
        switch(raw) {
            // Immediate to Accumulator
            case OR_AL_IMMED8 : //   0x0c: // OR AL,IMMED8
            case OR_AX_IMMED16: //   0x0d: // OR AX,IMMED16
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 4;
    }
}

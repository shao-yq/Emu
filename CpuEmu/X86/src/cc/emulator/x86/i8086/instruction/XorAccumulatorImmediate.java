package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class XorAccumulatorImmediate extends Instruction8086 {
    public XorAccumulatorImmediate(int[] raw) {
        super(raw);
        immediate = raw[1];
        incLength(1);
        if(op == XOR_AX_IMMED16){
            immediate |= (raw[2]<<8);
            incLength(1);
        }
    }

    public static boolean hasOpcode(int raw) {
        switch(raw) {
            /*
             * XOR destination,source
             *
             * XOR (Exclusive Or) performs the logical "exclusive or" of the two
             * operands and returns the result to the destination operand. A bit
             * in the result if set if the corresponding bits of the original
             * operands contain opposite values (one is set, the other is
             * cleared); otherwise the result bit is cleared.
             */
            // Immediate to Accumulator
            case XOR_AL_IMMED8 : //   0x34: // XOR AL,IMMED8
            case XOR_AX_IMMED16: //   0x35: // XOR AX,IMMED16
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 4;
    }
}
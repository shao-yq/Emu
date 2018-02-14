package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class SbbAccumulatorImmediate extends OpAccumulatorImmediate {
    public SbbAccumulatorImmediate(){}
    public SbbAccumulatorImmediate(int[] raw, int startIndex) {
        super(raw, startIndex);
    }


    public  boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch(raw) {
            /*
             * SBB destination,source
             *
             * SBB (Subtract with Borrow) subtracts the source from the
             * destination, subtracts one if CF is set, and returns the result
             * to the destination operand. Both operands may be bytes or words.
             * Both operands may be signed or unsigned binary numbers. SBB
             * updates AF, CF, OF, PF, SF and ZF. Since it incorporates a borrow
             * from a previous operation, SBB may be used to write routines that
             * subtract numbers longer than 16 bits.
             */
            // Immediate to Accumulator
            case SBB_AL_IMMED8 : //   0x1c: // SBB AL,IMMED8
            case SBB_AX_IMMED16: //   0X1d: // SBB AX,IMMED16
                return true;
        }
        return false;
    }

    @Override
    protected boolean isAxImmed16(int op) {
        return (op == SBB_AX_IMMED16);
    }

    @Override
    int oprandMode(int op) {
        return op - SBB_AL_IMMED8;
    }

}

package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class AndAccumulatorImmediate extends OpAccumulatorImmediate {
    public AndAccumulatorImmediate(){}
    public AndAccumulatorImmediate(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    public  boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch(raw) {
            /*
             * AND destination,source
             *
             * AND performs the logical "and" of the two operands (byte or word)
             * and returns the result to the destination operand. A bit in the
             * result is set if both corresponding bits of the original operands
             * are set; otherwise the bit is cleared.
             */
            // Immediate to Accumulator
            case AND_AL_IMMED8 : //  0x24: // AND AL,IMMED8
            case AND_AX_IMMED16: //  0x25: // AND AX,IMMED16
                return true;
        }
        return false;
    }

    @Override
    public String getMnemonic() {
        return "AND";
    }


    @Override
    protected boolean isAxImmed16(int op) {
        return (op == AND_AX_IMMED16);
    }

    @Override
    int oprandMode(int op) {
        return op - AND_AL_IMMED8;
    }

}

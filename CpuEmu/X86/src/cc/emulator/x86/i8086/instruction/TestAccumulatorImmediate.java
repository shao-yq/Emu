package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
/*
 * TEST destination,source
 *
 * TEST performs the logical "and" of the two operands (byte or
 * word), updates the flags, but does not return the result, i.e.,
 * neither operand is changed. If a TEST instruction is followed by
 * a JNZ (jump if not zero) instruction, the jump will be taken if
 * there are any corresponding 1-bits in both operands.
 */
public class TestAccumulatorImmediate extends OpAccumulatorImmediate {
    public TestAccumulatorImmediate(){}
    public TestAccumulatorImmediate(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    public  boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch(raw) {
            // Immediate to Accumulator
            case TEST_AL_IMMED8 : //   0xa8: // TEST AL,IMMED8
            case TEST_AX_IMMED16: //   0xa9: // TEST AX,IMMED16
                return true;
        }
        return false;
    }


    @Override
    protected boolean isAxImmed16(int op) {
        return (op == TEST_AX_IMMED16);
    }

    @Override
    int oprandMode(int op) {
        return op - TEST_AL_IMMED8;
    }

}

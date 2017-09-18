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
public class TestRegisterMemory extends Instruction8086 {
    public TestRegisterMemory(int[] raw, int startIndex) {
        super(raw, 2, startIndex);
        decodeDisplacement(raw);
    }
    public static boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch(raw) {

            // Register/Memory and Register
            case TEST_REG8__MEM8_REG8   : //   0x84: // TEST REG8/MEM8,REG8
            case TEST_REG16__MEM16_REG16: //   0x85: // TEST REG16/MEM16,REG16
                return true;
        }
        return false;
    }
    @Override
    public int getClocks() {
        return  mod == 0b11 ? 3 : 9;
    }
}

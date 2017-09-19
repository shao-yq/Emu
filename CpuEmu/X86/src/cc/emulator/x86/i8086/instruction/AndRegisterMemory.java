package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class AndRegisterMemory extends Instruction8086 {
    public AndRegisterMemory(){}

    public AndRegisterMemory(int[] raw, int startIndex) {
        super(raw, startIndex);
    }
    public void decode(int[] raw, int startIndex) {
        decode(raw, 2, startIndex);
        decodeDisplacement(raw);
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
            // Register/Memory and Register
            case AND_REG8__MEM8_REG8   : //   0x20: // AND REG8/MEM8,REG8
            case AND_REG16__MEM16_REG16: //   0x21: // AND REG16/MEM16,REG16
            case AND_REG8_REG8__MEM8   : //   0x22: // AND REG8,REG8/MEM8
            case AND_REG16_REG16__MEM16: //   0x23: // AND REG16,REG16/MEM16
                return true;
        }
        return false;
    }
    @Override
    public int getClocks() {
        if (d == 0b0)
            return mod == 0b11 ? 3 : 16;
        else
            return mod == 0b11 ? 3 : 9;
    }
}

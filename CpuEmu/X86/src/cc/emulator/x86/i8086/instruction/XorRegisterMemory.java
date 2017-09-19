package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class XorRegisterMemory extends Instruction8086 {
    public XorRegisterMemory(){}
    public XorRegisterMemory(int[] raw, int startIndex) {
        super(raw, startIndex);
    }
    public void decode(int[] raw, int startIndex) {
        super.decode(raw, 2, startIndex);
        decodeDisplacement(raw);
    }
    public  boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
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
            // Register/Memory and Register
            case XOR_REG8__MEM8_REG8   : //    0x30: // XOR REG8/MEM8,REG8
            case XOR_REG16__MEM16_REG16: //    0x31: // XOR REG16/MEM16,REG16
            case XOR_REG8_REG8__MEM8   : //    0x32: // XOR REG8,REG8/MEM8
            case XOR_REG16_REG16__MEM16: //    0x33: // XOR REG16,REG16/MEM16
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

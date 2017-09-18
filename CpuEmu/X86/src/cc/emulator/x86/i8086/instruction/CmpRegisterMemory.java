package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class CmpRegisterMemory extends Instruction8086 {
    public CmpRegisterMemory(int[] raw, int startIndex) {
        super(raw, 2, startIndex);
        decodeDisplacement(raw);
    }
    public static boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch(raw) {
            /*
             * CMP destination,source
             *
             * CMP (Compare) subtracts the source from the destination, which
             * may be bytes or words, but does not return the result. The
             * operands are unchanged, but the flags are updated and can be
             * tested by the subsequent conditional jump instructions. CMP
             * updates AF, CF, OF, PF, SF and ZF. The comparison reflected in
             * the flags is that of the destination to the source. If a CMP
             * instruction is followed by a JG (jump if greater) instruction,
             * for example, the jump is taken if the destination operand is
             * greater than the source operand.
             */
            // Register/Memory and Register
            case CMP_REG8__MEM8_REG8   : //  0x38: // CMP REG8/MEM8,REG8
            case CMP_REG16__MEM16_REG16: //  0x39: // CMP REG16/MEM16,REG16
            case CMP_REG8_REG8__MEM8   : //  0x3a: // CMP REG8,REG8/MEM8
            case CMP_REG16_REG16__MEM16: //  0x3b: // CMP REG16,REG16/MEM16
                return true;
        }
        return false;
    }
    @Override
    public int getClocks() {
        return mod == 0b11 ? 3 : 9;
    }
}

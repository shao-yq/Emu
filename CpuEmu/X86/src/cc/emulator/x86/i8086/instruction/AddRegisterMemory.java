package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class AddRegisterMemory extends Instruction8086 {
    public AddRegisterMemory(int[] raw) {
        super(raw, 2);
        decodeDisplacement(raw);
    }

    public static boolean hasOpcode(int raw) {
        switch(raw) {
            /*
             * ADD destination,source
             *
             * The sum of the two operands, which may be bytes or words,
             * replaces the destination operand. Both operands may be signed or
             * unsigned binary numbers. ADD updates AF, CF, OF, PF, SF and ZF.
             */
            // Reg./Memory and Register to Either
            case ADD_REG8__MEM8_REG8   : //  0x00: // ADD REG8/MEM8,REG8
            case ADD_REG16__MEM16_REG16: //  0x01: // ADD REG16/MEM16,REG16
            case ADD_REG8_REG8__MEM8   : //  0x02: // ADD REG8,REG8/MEM8
            case ADD_REG16_REG16__MEM16: //  0x03: // ADD REG16,REG16/MEM16
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

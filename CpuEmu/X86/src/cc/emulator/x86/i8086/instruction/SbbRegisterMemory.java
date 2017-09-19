package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class SbbRegisterMemory extends Instruction8086 {
    public SbbRegisterMemory(){}
    public SbbRegisterMemory(int[] raw, int startIndex) {
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
            // Reg./Memory with Register to Either
            case SBB_REG8__MEM8_REG8   : //   0x18: // SBB REG8/MEM8,REG8
            case SBB_REG16__MEM16_REG16: //   0x19: // SBB REG16/MEM16,REG16
            case SBB_REG8_REG8__MEM8   : //   0x1a: // SBB REG8,REG8/MEM8
            case SBB_REG16_REG16__MEM16: //   0x1b: // SBB REG16,REG16/MEM16
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

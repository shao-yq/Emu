package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class SubRegisterMemory extends OpRegisterMemory {
    public SubRegisterMemory(){}
    public SubRegisterMemory(int[] raw, int startIndex) {
        super(raw, startIndex);
    }
//    public void decode(int[] raw, int startIndex) {
//        super.decode(raw, 2, startIndex);
//        decodeDisplacement(raw);
//    }
    public  boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch(raw) {
            /*
             * SUB destination,source
             *
             * The source operand is subtracted from the destination operand,
             * and the result replaces the destination operand. The operands may
             * be bytes or words. Both operands may be signed or unsigned binary
             * numbers. SUB updates AF, CF, OF, PF, SF and ZF.
             */
            // Reg./Memory and Register to Either
            case SUB_REG8__MEM8_REG8   : //   0x28: // SUB REG8/MEM8,REG8
            case SUB_REG16__MEM16_REG16: //   0x29: // SUB REG16/MEM16,REG16
            case SUB_REG8_REG8__MEM8   : //   0x2a: // SUB REG8,REG8/MEM8
            case SUB_REG16_REG16__MEM16: //   0x2b: // SUB REG16,REG16/MEM16
                return true;
        }
        return false;
    }
//    @Override
//    public int getClocks() {
//        if (d == 0b0)
//            return mod == 0b11 ? 3 : 16;
//        else
//            return mod == 0b11 ? 3 : 9;
//    }

    @Override
    int oprandMode(int op) {
        return op-SUB_REG8__MEM8_REG8;
    }

    @Override
    public String getMnemonic() {
        return "SUB";
    }
}

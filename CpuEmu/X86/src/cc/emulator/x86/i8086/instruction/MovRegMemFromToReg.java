package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class MovRegMemFromToReg extends MOV {
    public MovRegMemFromToReg(int[] raw) {
        super(raw, 2);
        decodeDisplacement(raw);
    }

    public int getClocks(int c) {
        if (mod == 0b11) {
           return 2;
        } else {
            if (d == 0b0) {
                return 9;
            }
            return 8;
        }
    }

    public static boolean hasOpcode(int raw) {
        switch (raw) {
            // Register/Memory to/from Register
            case MOV_REG8__MEM8_REG8: //   0x88: // MOV REG8/MEM8,REG8
            case MOV_REG16__MEM16_REG16: //   0x89: // MOV REG16/MEM16,REG16
            case MOV_REG8_REG8__MEM8: //   0x8a: // MOV REG8,REG8/MEM8
            case MOV_REG16_REG16__MEM16: //   0x8b: // MOV REG16,REG16/MEM16
                return true;
        }
        return false;
    }

}

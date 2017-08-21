package cc.emulator.arch.x86.i8086.instruction;

import cc.emulator.arch.x86.i8086.Instruction8086;

import static cc.emulator.arch.x86.i8086.Intel8086InstructionSet.*;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class RegMemFromToReg extends Instruction8086 {
    public RegMemFromToReg(int[] raw) {
        super(raw, 2);
        decodeDispData(raw);

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

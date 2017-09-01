package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class IncRegisterMemory extends Instruction8086 {
    public IncRegisterMemory(int[] raw) {
        super(raw, 2);
        decodeDisplacement(raw);
    }

    public static boolean hasOpcode(int raw[]) {
        switch(raw[0]) {
            case EXT_0XFE:  // 0xfe:
                // INC REG8/MEM8
                // DEC REG8/MEM8
            case EXT_0XFF:  // 0xff:
                // INC REG16/MEM16
                // DEC REG16/MEM16
                // CALL REG16/MEM16 (intra)
                // CALL MEM16 (intersegment)
                // JMP REG16/MEM16 (intra)
                // JMP MEM16 (intersegment)
                // PUSH REG16/MEM16

                int reg = (raw[1]>>3) & 0b111;
                switch (reg) {
                    case INC_REG8__MEM8: //   0b000: // INC REG8/MEM8  case INC_REG16__MEM16: //   0b000: // INC REG16/MEM16

                    return true;
                }
        }
        return false;
    }

}

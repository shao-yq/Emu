package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/9/1.
 */
public class PushMemory extends Instruction8086 {
    public PushMemory(int[] raw) {
        super(raw,2);
        decodeDisplacement(raw);
    }
    public static boolean hasOpcode(int raw[]) {
        switch (raw[0]) {
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
                    case PUSH_MEM16: //   0b110: // PUSH MEM16
                        return true;
                }
        }
        return false;
    }
}

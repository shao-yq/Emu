package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class DivRegisterMemory extends Instruction8086 {
    public DivRegisterMemory(int[] raw, int startIndex) {
        super(raw, 2, startIndex);
        decodeDisplacement(raw);
    }

    public static boolean hasOpcode(int raw[], int startIndex) {
        switch(raw[startIndex]) {
            case EXT_0XF6:  // 0xf6:
                // TEST REG8/MEM8,IMMED8
                // NOT REG8/MEM8
                // NEG REG8/MEM8
                // MUL REG8/MEM8
                // IMUL REG8/MEM8
                // DIV REG8/MEM8
                // IDIV REG8/MEM8
            case EXT_0XF7:  // 0xf7:
                // TEST REG16/MEM16,IMMED16
                // NOT REG16/MEM16
                // NEG REG16/MEM16
                // MUL REG16/MEM16
                // IMUL REG16/MEM16
                // DIV REG16/MEM16
                // IDIV REG16/MEM16

                int reg = (raw[1+startIndex]>>3) & 0b111;
                switch (reg) {
                    case MOD_DIV: //   0b110: // DIV
                    case MOD_IDIV: //   0b111: // IDIV
                    return true;
                }
        }
        return false;
    }

}

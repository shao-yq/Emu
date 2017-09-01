package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/9/1.
 */
public class PopRegisterMemory extends Instruction8086{
    public PopRegisterMemory(int[] raw) {
        super(raw,2);
        decodeDisplacement(raw);
    }
    public static boolean hasOpcode(int raw[]) {
        switch (raw[0]) {
            /*
             * GROUP 1A
             */
            case POP_REG16__MEM16: //  0x8f: // POP REG16/MEM16
                int reg = (raw[1]>>3) & 0b111;
                switch (reg) {
                    case MOD_POP: //  0b000: // POP
                        return true;
                }
        }
        return false;
    }
}

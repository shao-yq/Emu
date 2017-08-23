package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class PopSegmentRegister extends Instruction8086 {
    public PopSegmentRegister(int[] raw) {
        super(raw);
        reg = op >>> 3 & 0b111;
    }
    public static boolean hasOpcode(int raw) {
        switch (raw) {
            // Segment Register
            case POP_ES: //  0x07: // POP ES
            case POP_CS: //  0x0f: // POP CS
            case POP_SS: //  0x17: // POP SS
            case POP_DS: //  0x1f: // POP DS
                return true;
        }
        return false;
    }
    public int getClocks() {
        return 8;
    }

}

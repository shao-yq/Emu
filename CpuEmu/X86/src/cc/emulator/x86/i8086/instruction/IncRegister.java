package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/30.
 */
/*
 * INC destination
 *
 * INC (Increment) adds one to the destination operand. The operand
 * may be a byte or a word and is treated as an unsigned binary
 * number. INC updates AF, OF, PF, SF and ZF; it does not affect CF.
 */
public class IncRegister extends Instruction8086 {
    public IncRegister(int[] raw) {
        super(raw);
        reg = op & 0b111;
    }

    public static boolean hasOpcode(int raw) {
        switch(raw) {
            // Register
            case INC_AX: //  0x40: // INC AX
            case INC_CX: //  0x41: // INC CX
            case INC_DX: //  0x42: // INC DX
            case INC_BX: //  0x43: // INC BX
            case INC_SP: //  0x44: // INC SP
            case INC_BP: //  0x45: // INC BP
            case INC_SI: //  0x46: // INC SI
            case INC_DI: //  0x47: // INC DI
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 2;
    }
}

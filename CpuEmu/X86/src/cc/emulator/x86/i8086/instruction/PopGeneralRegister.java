package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class PopGeneralRegister extends Instruction8086 {
    public PopGeneralRegister(int[] raw) {
        super(raw);
        reg = op & 0b111;
    }

    public static boolean hasOpcode(int raw) {
        switch (raw) {
            // Register
            case POP_AX: //  0x58: // POP AX
            case POP_CX: //  0x59: // POP CX
            case POP_DX: //  0x5a: // POP DX
            case POP_BX: //  0x5b: // POP BX
            case POP_SP: //  0x5c: // POP SP
            case POP_BP: //  0x5d: // POP BP
            case POP_SI: //  0x5e: // POP SI
            case POP_DI: //  0x5f: // POP DI
                return true;
        }
        return false;
    }
    public int getClocks() {
        return 8;
    }
}
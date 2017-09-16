package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/30.
 */
/*
 * DEC destination
 *
 * DEC (Decrement) subtracts one from the destination, which may be
 * a byte or a word. DEC updates AF, OF, PF, SF, and ZF; it does not
 * affect CF.
 */
public class DecRegister extends Instruction8086 {
    public DecRegister(int[] raw, int startIndex) {
        super(raw, startIndex);
        reg = op & 0b111;
    }

    public static boolean hasOpcode(int raw) {
        switch(raw) {
            // Register
            case DEC_AX: //   0x48: // DEC AX
            case DEC_CX: //   0x49: // DEC CX
            case DEC_DX: //   0x4a: // DEC DX
            case DEC_BX: //   0x4b: // DEC BX
            case DEC_SP: //   0x4c: // DEC SP
            case DEC_BP: //   0x4d: // DEC BP
            case DEC_SI: //   0x4e: // DEC SI
            case DEC_DI: //   0x4f: // DEC DI
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 2;
    }
}

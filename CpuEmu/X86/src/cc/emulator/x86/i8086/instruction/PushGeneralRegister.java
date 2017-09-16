package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
 /*
 * PUSH source
 *
 * PUSH decrements SP (the stack pointer) by two and then transfers
 * a word from the source operand to the top of the stack now
 * pointed by SP. PUSH often is used to place parameters on the
 * stack before calling a procedure; more generally, it is the basic
 * means of storing temporary data on the stack.
 */
public class PushGeneralRegister extends Instruction8086 {
    public PushGeneralRegister(int[] raw, int startIndex) {
        super(raw, startIndex);
        reg = op & 0b111;
    }

    public static boolean hasOpcode(int raw) {
        switch (raw) {
            // Push Register
            case PUSH_AX: //  0x50: // PUSH AX
            case PUSH_CX: //  0x51: // PUSH CX
            case PUSH_DX: //  0x52: // PUSH DX
            case PUSH_BX: //  0x53: // PUSH BX
            case PUSH_SP: //  0x54: // PUSH SP
            case PUSH_BP: //  0x55: // PUSH BP
            case PUSH_SI: //  0x56: // PUSH SI
            case PUSH_DI: //  0x57: // PUSH DI
            return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 11;
    }
}

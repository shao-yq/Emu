package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/30.
 */
public class PushPopFlags extends Instruction8086 {
    public PushPopFlags(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    public static boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        int opcode = raw;
        switch (opcode){
            /*
             * PUSHF
             *
             * PUSH decrements SP (the stack pointer) by two and then transfers
             * all flags to the word at the top of stack pointed to by SP. The
             * flags themselves are not affected.
             */
            case PUSHF: //  0x9c: // PUSHF
            /*
             * POPF
             *
             * POPF transfers specific bits from the word at the current top of
             * stack (pointed to by register SP) into the 8086 flags, replacing
             * whatever values the flags previously contained. SP is then
             * incremented by two to point at the new top of stack. PUSHF and
             * POPF allow a procedure to save and restore a calling program's
             * flags. They also allow a program to change the setting of TF
             * (there is no instruction for updating this flag directly). The
             * change is accomplished by pushing the flags, altering bit 8 of
             * the memory- image and then popping the flags.
             */
            case POPF: //  0x9d: // POPF
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        switch (op){
            case PUSHF: //  0x9c: // PUSHF
                return 10;
            case POPF: //  0x9d: // POPF
                return 8;
        }
        return 8;
    }
}

package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/30.
 */

/*
 * STOS destination-string
 *
 * STOS (Store String) transfers a byte or word from register AL or
 * AX to the string element addressed by DI and updates DI to point
 * to the next location in the string. As a repeated operation, STOS
 * provides a convenient way to initialize a string to a constant
 * value (e.g., to blank out a print line).
 */
public class StoreString extends Instruction8086 {
    public StoreString(int[] raw) {
        super(raw);
    }

    public static boolean hasOpcode(int raw) {
        int opcode = raw;
        switch (opcode){
            case STOS_STR8 : //   0xaa: // STOS DEST-STR8
            case STOS_STR16: //   0xab: // STOS DEST-STR16
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 10;
    }
}

package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/30.
 */

/*
 * SCAS destination-string
 *
 * SCAS (Scan String) subtracts the destination string element (byte
 * or word) addressed by DI from the content of AL (byte string) or
 * AX (word string) and updates the flags, but does not alter the
 * destination string or the accumulator. SCAS also updates DI to
 * point to the next string element and AF, CF, OF, PF, SF and ZF to
 * reflect the relationship of the scan value in AL/AX to the string
 * element. If SCAS is prefixed with REPE or REPZ, the operation is
 * interpreted as "scan while not end-of-string (CX not 0) and
 * string-element = scan-value (ZF = 1)." This form may be used to
 * scan for departure from a given value. If SCAS is prefixed with
 * REPNE or REPNZ, the operation is interpreted as "scan while not
 * end-of-string (CX not 0) and string-element is not equal to
 * scan-value (ZF = 0)." This form may be used to locate a value in
 * a string.
 */
public class ScanString extends Instruction8086 {
    public ScanString(int[] raw) {
        super(raw);
    }

    public static boolean hasOpcode(int raw) {
        int opcode = raw;
        switch (opcode){

            case SCAS_DEST8 : //   0xae: // SCAS DEST-STR8
            case SCAS_DEST16: //   0xaf: // SCAS DEST-STR16
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 15;
    }
}

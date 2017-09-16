package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/30.
 */

/*
 * CMPS destination-string,source-string
 *
 * CMPS (Compare String) subtracts the destination byte or word
 * (addressed by DI) from the source byte or word (addressed by SI).
 * CMPS affects the flags but does not alter either operand, updates
 * SI and DI to point to the next string element and updates AF, CF,
 * OF, PF, SF and ZF to reflect the relationship of the destination
 * element to the source element. For example, if a JG (Jump if
 * Greater) instruction follows CMPS, the jump is taken if the
 * destination element is greater than the source element. If CMPS
 * is prefixed with REPE or REPZ, the operation is interpreted as
 * "compare while not end-of-string (CX not zero) and strings are
 * equal (ZF = 1)." If CMPS is preceded by REPNE or REPNZ, the
 * operation is interpreted as "compare while not end-of-string (CX
 * not zero) and strings are not equal (ZF = 0)." Thus, CMPS can be
 * used to find matching or differing string elements.
 */
public class CompareString extends Instruction8086 {
    public CompareString(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    public static boolean hasOpcode(int raw) {
        int opcode = raw;
        switch (opcode){

            case CMPS_STR8_STR8  : //   0xa6: // CMPS DEST-STR8,SRC-STR8
            case CMPS_STR16_STR16: //   0xa7: // CMPS DEST-STR16,SRC-STR16
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 22;
    }
}

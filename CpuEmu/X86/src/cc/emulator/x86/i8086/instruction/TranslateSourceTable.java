package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */

/*
 * XLAT translate-table
 *
 * XLAT (translate) replaces a byte in the AL register with a byte
 * from a 256-byte, user-coded translation table. Register BX is
 * assumed to point to the beginning of the table. The byte in AL is
 * used as an index into the table and is replaced by the byte at
 * the offset in the table corresponding to AL's binary value. The
 * first byte in the table has an offset of 0. For example, if AL
 * contains 5H, and the sixth element of the translation table
 * contains 33H, then AL will contain 33H following the instruction.
 * XLAT is useful for translating characters from one code to
 * another, the classic example being ASCII to EBCDIC or the
 * reverse.
 */
public class TranslateSourceTable extends Instruction8086 {
    public TranslateSourceTable(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    public static boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch (raw) {
            case XLAT_SOURCE_TABLE:     // 0xd7: // XLAT SOURCE-TABLE
                return true;
        }

        return false;
    }
    public int getClocks() {
        return 11;
    }
}

package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/30.
 */

/*
 * MOVS destination-string,source-string
 *
 * MOVS (Move String) transfers a byte or a word from the source
 * string (addressed by SI) to the destination string (addressed by
 * DI) and updates SI and DI to point to the next string element.
 * When used in conjunction with REP, MOVS performs a memory-to-
 * memory block transfer.
 */
public class MoveString extends Instruction8086 {
    public MoveString(){}
    public MoveString(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    public  boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        int opcode = raw;
        switch (opcode){
            /*
             * MOVSB/MOVSW
             *
             * These are alternate mnemonics for the move string instruction.
             * These mnemonics are coded without operands; they explicitly tell
             * the assembler that a byte string (MOVSB) or a word string (MOVSW)
             * is to be moved (when MOVS is coded, the assembler determines the
             * string type from the attributes of the operands). These mnemonics
             * are useful when the assembler cannot determine the attributes of
             * a string, e.g., a section of code is being moved.
             */
            case MOVS_STR8_STR8  : //   0xa4: // MOVS DEST-STR8,SRC-STR8
            case MOVS_STR16_STR16: //   0xa5: // MOVS DEST-STR16,SRC-STR16
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 17;
    }
    @Override
    public String getMnemonic() {
        switch (op){
            case MOVS_STR8_STR8  : //   0xa4: // MOVS DEST-STR8,SRC-STR8
                return "MOVSB";
            case MOVS_STR16_STR16: //   0xa5: // MOVS DEST-STR16,SRC-STR16
                return "MOVSW";
        }
        return "MOVS";
    }
}

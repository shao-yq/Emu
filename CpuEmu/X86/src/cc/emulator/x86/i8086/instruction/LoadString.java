package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/30.
 */

/*
 * LODS source-string
 *
 * LODS (Load String) transfers the byte or word string element
 * addressed by SI to register AL or AX, and updates SI to point to
 * the next element in the string. This instruction is not
 * ordinarily repeated since the accumulator would be overwritten by
 * each repetition, and only the last element would be retained.
 * However, LODS is very useful in software loops as part of a more
 * complex string function built up from string primitives and other
 * instructions.
 */
public class LoadString extends Instruction8086 {
    public LoadString(int[] raw) {
        super(raw);
    }

    public static boolean hasOpcode(int raw) {
        int opcode = raw;
        switch (opcode){

            case LODS_SRC8 : //  0xac: // LODS SRC-STR8
            case LODS_SRC16: //  0xad: // LODS SRC-STR16
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 13;
    }
}
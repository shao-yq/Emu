package cc.emulator.x86.i8086.instruction;

import cc.emulator.core.MemoryManager;
import cc.emulator.x86.i8086.Instruction8086;
import cc.emulator.x86.i8086.Intel8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/30.
 */
/*
 * AAA
 *
 * AAA (ASCII Adjust for Addition) changes the contents of register
 * AL to a valid unpacked decimal number; the high-order half-byte
 * is zeroed. AAA updates AF and CF; the content of OF, PF, SF and
 * ZF is undefined following execution of AAA.
 */
public class AsciiAdjustForAddition extends Instruction8086 {


    public AsciiAdjustForAddition(int[] raw) {
        super(raw);
    }


    public static boolean hasOpcode(int raw) {
        switch(raw) {

            case AAA: //  0x37: // AAA
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 4;
    }
}

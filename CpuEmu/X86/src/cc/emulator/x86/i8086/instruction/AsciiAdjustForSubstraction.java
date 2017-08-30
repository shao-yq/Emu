package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/30.
 */
/*
 * AAS
 *
 * AAS (ASCII Adjust for Subtraction) corrects the result of a
 * previous subtraction of two valid unpacked decimal operands (the
 * destination operand must have been specified as register AL). AAS
 * changes the content of AL to a valid unpacked decimal number; the
 * high-order half-byte is zeroed. AAS updates AF and CF; the
 * content of OF, PF, SF and ZF is undefined following execution of
 * AAS.
 */
public class AsciiAdjustForSubstraction extends Instruction8086 {


    public AsciiAdjustForSubstraction(int[] raw) {
        super(raw);
    }


    public static boolean hasOpcode(int raw) {
        switch(raw) {

            case AAS: //  0x3f: // AAS
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 4;
    }
}

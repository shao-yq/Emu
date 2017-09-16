package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/30.
 */
/*
 * AAM
 *
 * AAM (ASCII Adjust for Multiply) corrects the result of a previous
 * multiplication of two valid unpacked decimal operands. A valid 2-
 * digit unpacked decimal number is derived from the content of AH
 * and AL and is returned to AH and AL. The high-order half-bytes of
 * the multiplied operands must have been 0H for AAM to produce a
 * correct result. AAM updates PF, SF and ZF; the content of AF, CF
 * and OF is undefined following execution of AAM.
 */
public class AsciiAdjustForMultiply extends Instruction8086 {


    public AsciiAdjustForMultiply(int[] raw, int startIndex) {
        super(raw, startIndex);
        // 2nd byte
        immediate = raw[1+startIndex];
    }


    public static boolean hasOpcode(int raw) {
        switch(raw) {


            case AAM: //  0xd4: // AAM
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 83;
    }
}

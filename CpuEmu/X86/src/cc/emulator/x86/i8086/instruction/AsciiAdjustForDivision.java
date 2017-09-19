package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/30.
 */
/*
 * AAD
 *
 * AAD (ASCII Adjust for Division) modifies the numerator in AL
 * before dividing two valid unpacked decimal operands so that the
 * quotient produced by the division will be a valid unpacked
 * decimal number. AH must be zero for the subsequent DIV to produce
 * the correct result. The quotient is returned in AL, and the
 * remainder is returned in AH; both high-order half-bytes are
 * zeroed. AAD updates PF, SF and ZF; the content of AF, CF and OF
 * is undefined following execution of AAD.
 */
public class AsciiAdjustForDivision extends Instruction8086 {
    public AsciiAdjustForDivision(){}
    public AsciiAdjustForDivision(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    public void decode(int[] raw, int startIndex) {
        decode(raw, 1,startIndex);
        // 2nd byte
        immediate = raw[1+startIndex];
        incLength(1);
    }

    public  boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch(raw) {

            case AAD: //  0xd5: // AAD
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 60;
    }
}

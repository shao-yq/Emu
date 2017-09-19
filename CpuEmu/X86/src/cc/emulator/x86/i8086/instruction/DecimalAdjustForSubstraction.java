package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/30.
 */

/*
 * DAS
 *
 * DAS (Decimal Adjust for Subtraction) corrects the result of a
 * previous subtraction of two valid packed decimal operands (the
 * destination operand must have been specified as register AL). DAS
 * changes the content of AL to a pair of valid packed decimal
 * digits. DAS updates AF, CF, PF, SF and ZF; the content of OF is
 * undefined following the execution of DAS.
 */
public class DecimalAdjustForSubstraction extends Instruction8086 {
    public DecimalAdjustForSubstraction(){}

    public DecimalAdjustForSubstraction(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

//    public void decode(int[] raw, int startIndex) {
//        decode(raw, 1, startIndex);
//    }

    public  boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch(raw) {

            case DAS: //  0x2f: // DAS
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 4;
    }
}

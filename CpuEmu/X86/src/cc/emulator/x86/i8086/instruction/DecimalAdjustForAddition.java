package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/30.
 */

/*
 * DAA
 *
 * DAA (Decimal Adjust for Addition) corrects the result of
 * previously adding two valid packed decimal operands (the
 * destination operand must have been register AL). DAA changes the
 * content of AL to a pair of valid packed decimal digits. It
 * updates AF, CF, PF, SF and ZF; the content of OF is undefined
 * following execution of DAA.
 */
public class DecimalAdjustForAddition extends Instruction8086 {
    public DecimalAdjustForAddition(){}

    public DecimalAdjustForAddition(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    public  boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch(raw) {

            case DAA:  //  0x27:  // DAA
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 4;
    }

    @Override
    public String getMnemonic() {
        return "DAA";
    }
}

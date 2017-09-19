package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class AdcAccumulatorImmediate extends Instruction8086 {
    public AdcAccumulatorImmediate(){}

    public AdcAccumulatorImmediate(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    public void decode(int[] raw, int startIndex) {
        decode(raw, 1, startIndex);
        immediate = raw[1+startIndex];
        incLength(1);
        if(op == ADC_AX_IMMED16){
            immediate |= (raw[2+startIndex]<<8);
            incLength(1);
        }
    }

    public  boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch(raw) {
            /*
             * ADC destination,source
             *
             * ADC (Add with Carry) sums the operands, which may be bytes or
             * words, adds one if CF is set and replaces the destination operand
             * with the result. Both operands may be signed or unsigned binary
             * numbers. ADC updates AF, CF, OF, PF, SF and ZF. Since ADC
             * incorporates a carry from a previous operation, it can be used to
             * write routines to add numbers longer than 16 bits.
             */
            // Immediate to Accumulator
            case ADC_AL_IMMED8 : //  0x14: // ADC AL,IMMED8
            case ADC_AX_IMMED16: //  0X15: // ADC AX,IMMED16
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 4;
    }
}

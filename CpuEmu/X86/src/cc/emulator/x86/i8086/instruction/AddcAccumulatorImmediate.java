package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class AddcAccumulatorImmediate extends Instruction8086 {
    public AddcAccumulatorImmediate(int[] raw) {
        super(raw);
        immediate = raw[1];
        incLength(1);
        if(op == ADC_AX_IMMED16){
            immediate |= (raw[2]<<8);
            incLength(1);
        }
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

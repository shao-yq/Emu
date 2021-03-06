package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class AdcRegisterMemory extends OpRegisterMemory {
    public AdcRegisterMemory(){}

    public AdcRegisterMemory(int[] raw, int startIndex) {
        super(raw, startIndex);
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
            // Reg./Memory with Register to Either
            case ADC_REG8__MEM8_REG8   : //  0x10: // ADC REG8/MEM8,REG8
            case ADC_REG16__MEM16_REG16: //  0x11: // ADC REG16/MEM16,REG16
            case ADC_REG8_REG8__MEM8   : //  0x12: // ADC REG8,REG8/MEM8
            case ADC_REG16_REG16__MEM16: //  0x13: // ADC REG16,REG16/MEM16
                return true;
        }
        return false;
    }

    @Override
    int oprandMode(int op) {
        return op-ADC_REG8__MEM8_REG8;
    }
    @Override
    public String getMnemonic() {
        return "ADC";
    }
}

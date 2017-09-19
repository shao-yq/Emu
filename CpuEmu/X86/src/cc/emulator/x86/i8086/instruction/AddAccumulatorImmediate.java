package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class AddAccumulatorImmediate extends Instruction8086 {
    public AddAccumulatorImmediate(){}

    public AddAccumulatorImmediate(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    public void decode(int[] raw, int startIndex) {
        decode(raw,1,startIndex);
        immediate = raw[1+startIndex];
        incLength(1);
        if(op == ADD_AX_IMMED16){
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
             * ADD destination,source
             *
             * The sum of the two operands, which may be bytes or words,
             * replaces the destination operand. Both operands may be signed or
             * unsigned binary numbers. ADD updates AF, CF, OF, PF, SF and ZF.
             */
            // Immediate to Accumulator
            case ADD_AL_IMMED8 : //  0x04: // ADD AL,IMMED8
            case ADD_AX_IMMED16: //  0x05: // ADD AX,IMMED16
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 4;
    }
}

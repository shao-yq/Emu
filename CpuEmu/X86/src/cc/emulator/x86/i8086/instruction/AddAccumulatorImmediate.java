package cc.emulator.x86.i8086.instruction;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class AddAccumulatorImmediate extends OpAccumulatorImmediate {
    public AddAccumulatorImmediate(){}

    public AddAccumulatorImmediate(int[] raw, int startIndex) {
        super(raw, startIndex);
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
    public String getMnemonic() {
        return "ADD";
    }


    @Override
    protected boolean isAxImmed16(int op) {
        return (op == ADD_AX_IMMED16);
    }

    @Override
    int oprandMode(int op) {
        return op - ADD_AL_IMMED8;
    }

}

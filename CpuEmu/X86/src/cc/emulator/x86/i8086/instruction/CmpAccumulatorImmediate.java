package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class CmpAccumulatorImmediate extends Instruction8086 {
    public CmpAccumulatorImmediate(int[] raw) {
        super(raw);
        immediate = raw[1];
        incLength(1);
        if(op == CMP_AX_IMMED16){
            immediate |= (raw[2]<<8);
            incLength(1);
        }
    }

    public static boolean hasOpcode(int raw) {
        switch(raw) {
            /*
             * CMP destination,source
             *
             * CMP (Compare) subtracts the source from the destination, which
             * may be bytes or words, but does not return the result. The
             * operands are unchanged, but the flags are updated and can be
             * tested by the subsequent conditional jump instructions. CMP
             * updates AF, CF, OF, PF, SF and ZF. The comparison reflected in
             * the flags is that of the destination to the source. If a CMP
             * instruction is followed by a JG (jump if greater) instruction,
             * for example, the jump is taken if the destination operand is
             * greater than the source operand.
             */
            // Immediate with Accumulator
            case CMP_AL_IMMED8 : //  0x3c: // CMP AL,IMMED8
            case CMP_AX_IMMED16: //  0x3d: // CMP AX,IMMED16
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 4;
    }
}

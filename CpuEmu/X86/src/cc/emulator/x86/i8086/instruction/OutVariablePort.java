package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */

/*
 * OUT port,accumulator
 *
 * OUT transfers a byte or a word from the AL register or the AX
 * register, respectively, to an output port. The port number may be
 * specified either with an immediate byte constant, allowing access
 * to ports numbered 0 through 255, or with a number previously
 * placed in register DX, allowing variable access (by changing the
 * value in DX) to ports numbered from 0 through 65,535.
 */
public class OutVariablePort extends Instruction8086 {
    public OutVariablePort(){}
    public OutVariablePort(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    public void decode(int[] raw, int startIndex) {
        decode(raw, 1, startIndex);
        immediate = raw[1+startIndex];
        incLength(1);
    }

    public  boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch (raw) {
            // Variable Port
            case OUT_AL_IMMED8: //  0xe6: // OUT AL,IMMED8
            case OUT_AX_IMMED8: //  0xe7: // OUT AX,IMMED8
                return true;
        }
        return false;
    }
    public int getClocks() {
        int clock = 10;
        if (w == W && (immediate & 0b1) == 0b1)
            clock += 4;
        return clock;
    }

}

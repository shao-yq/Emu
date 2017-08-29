package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */

/*
 * IN accumulator,port
 *
 * IN transfers a byte or a word from an input port to the AL
 * register or the AX register, respectively. The port number may be
 * specified either with an immediate byte constant, allowing access
 * to ports numbered 0 through 255, or with a number previously
 * placed in the DX register, allowing variable access (by changing
 * the value in DX) to ports numbered from 0 through 65,535.
 */
public class InVariablePort extends Instruction8086 {
    public InVariablePort(int[] raw) {
        super(raw);
        immediate = raw[1];
        incLength(1);
    }

    public static boolean hasOpcode(int raw) {
        switch (raw) {
            // Variable Port
            case IN_AL_IMMED8: //  0xe4: // IN AL,IMMED8
            case IN_AX_IMMED8: //  0xe5: // IN AX,IMMED8
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

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
public class OutFixedPort extends Instruction8086 {
    public OutFixedPort(int[] raw) {
        super(raw);
    }

    public static boolean hasOpcode(int raw) {
        switch (raw) {
            // Fixed Port
            case OUT_AL_DX: //  0xee: // OUT AL,DX
            case OUT_AX_DX: //  0xef: // OUT AX,DX
                return true;
        }
        return false;
    }
    public int getClocks() {
        int clock = 8;

        return clock;
    }
}

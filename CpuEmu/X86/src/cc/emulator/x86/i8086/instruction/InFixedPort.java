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
 *
 */
public class InFixedPort extends Instruction8086 {
    public InFixedPort(){}
    public InFixedPort(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    public  boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch (raw) {
            // Fixed Port
            case IN_AL_DX: //  0xec: // IN AL,DX
            case IN_AX_DX: //  0xed: // IN AX,DX
                return true;
        }
        return false;
    }
    public int getClocks() {
        int clock = 8;

        return clock;
    }
    protected  String getOperandPart() {
        switch (op) {
            // Fixed Port
            case IN_AL_DX: //  0xec: // IN AL,DX
                return "AL,DX";
            case IN_AX_DX: //  0xed: // IN AX,DX
                return "AX,DX";
        }
        return "";
    }

    @Override
    public String getMnemonic() {
        return "IN";
    }

}

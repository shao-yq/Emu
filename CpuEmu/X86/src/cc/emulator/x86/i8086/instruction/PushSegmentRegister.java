package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class PushSegmentRegister extends Instruction8086 {
    public PushSegmentRegister(){}
    public PushSegmentRegister(int[] raw, int startIndex) {
        super(raw, startIndex);
    }
    public void decode(int[] raw, int startIndex) {
        decode(raw,1, startIndex);
        reg = op >>> 3 & 0b111;
    }
    public  boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch (raw) {
            // Segment Register
            case PUSH_ES: //  0x06: // PUSH ES
            case PUSH_CS: //  0x0e: // PUSH CS
            case PUSH_SS: //  0x16: // PUSH SS
            case PUSH_DS: //  0x1e: // PUSH DS
                return true;
        }
        return false;
    }
    public int getClocks() {
        return 10;
    }

    protected  String getOperandPart() {
        switch (op) {
            // Segment Register
            case PUSH_ES: //  0x06: // PUSH ES
                return "ES";
            case PUSH_CS: //  0x0e: // PUSH CS
                return "CS";
            case PUSH_SS: //  0x16: // PUSH SS
                return "SS";
            case PUSH_DS: //  0x1e: // PUSH DS
                return "DS";
        }
        return "";
    }

    @Override
    public String getMnemonic() {
        return "PUSH";
    }
}

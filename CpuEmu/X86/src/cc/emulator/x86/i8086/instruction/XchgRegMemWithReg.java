package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */

/*
 * XCHG destination,source
 *
 * XCHG (exchange) switches the contents of the source and
 * destination (byte or word) operands. When used in conjunction
 * with the LOCK prefix, XCHG can test and set a semaphore that
 * controls access to a resource shared by multiple processors.
 */

public class XchgRegMemWithReg extends Instruction8086 {
    public XchgRegMemWithReg(){}
    public XchgRegMemWithReg(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    public void decode(int[] raw, int startIndex) {
        super.decode(raw, 2, startIndex);
        decodeDisplacement(raw);
    }
    public  boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch (raw) {
            // Register/Memory with Register
            case XCHG_REG8_REG8__MEM8   : // 0x86: // XCHG REG8,REG8/MEM8
            case XCHG_REG16_REG16__MEM16: // 0x87: // XCHG REG16,REG16/MEM16
                return true;
        }
        return false;
    }
    public int getClocks() {
        return mod == 0b11 ? 3 : 17;
    }

}

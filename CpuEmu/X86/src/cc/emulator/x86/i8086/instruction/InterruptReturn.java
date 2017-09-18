package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/31.
 */
/*
 * IRET
 *
 * IRET (Interrupt Return) transfers control back to the point of
 * interruption by popping IP, CS and the flags from the stack. IRET
 * thus affects all flags by restoring them to previously saved
 * values. IRET is used to exit any interrupt procedure, whether
 * activated by hardware or software.
 */
public class InterruptReturn extends Instruction8086{
    public InterruptReturn(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    public static boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch (raw) {

            case IRET: //  0xcf: // IRET
                return true;
        }

        return false;
    }
    @Override
    public int getClocks() {
        return 24;

    }
}

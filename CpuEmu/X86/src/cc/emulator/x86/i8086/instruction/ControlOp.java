package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/31.
 */
/*
 * Processor Control Instructions
 *
 * These instructions allow programs to control various CPU functions.
 *
 */
public class ControlOp extends Instruction8086 {
    public ControlOp(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    public static boolean hasOpcode(int raw) {
        switch (raw) {
            /*
             * External Synchronization
             */
            /*
             * HLT
             *
             * HLT (Halt) causes the 8086 to enter the halt state. The processor
             * leaves the halt state upon activation of the RESET line, upon
             * receipt of a non-maskable interrupt request on NMI, or, if
             * interrupts are enabled, upon receipt of a maskable interrupt
             * request on INTR. HLT does not affect any flags. It may be used as
             * an alternative to an endless software loop in situations where a
             * program must wait for an interrupt.
             */
            case HLT : //  0xf4: // HLT
            /*
             * No Operation
             */
            /*
             * NOP
             *
             * NOP (No Operation) causes the CPU to do nothing. NOP does not
             * affect any flags.
             */
            case NOP : //  0x90: // NOP
            /*
             * WAIT
             *
             * WAIT causes the CPU to enter the wait state while its /TEST line
             * is not active. WAIT does not affect any flags.
             */
            case WAIT : //  0x9b: // WAIT

                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        if(op==HLT)
            return 2;
        else
            return 3;
    }
}

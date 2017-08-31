package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/30.
 */
/*
 * Iteration Control
 *
 * The iteration control instructions can be used to regulate the
 * repetition of software loops. These instructions use the CX
 * register as a counter. Like the conditional transfers, the
 * iteration control instructions are self-relative and may only
 * transfer to targets that are within -128 to +127 bytes of
 * themselves, i.e., they are SHORT transfers.
 */
public class Loop extends Instruction8086{
    public Loop(int[] raw) {
        super(raw);

        // IP-INC-8
        disp = raw[1];
        incLength(1);
    }

    public static boolean hasOpcode(int raw) {
        switch (raw){
            /*
             * LOOP short-label
             *
             * LOOP decrements CX by 1 and transfers control to the target
             * operand if CX is not 0; otherwise the instruction following LOOP
             * is executed.
             */
            case LOOP_SHORT: //  0xe2: // LOOP SHORT-LABEL
            /*
             * LOOPE/LOOPZ short-label
             *
             * LOOPE and LOOPZ (Loop While Equal and Loop While Zero) are
             * different mnemonics for the same instruction (similar to the REPE
             * and REPZ repeat prefixes). CX is decremented by 1, and control is
             * transferred to the target operand if CX is not 0 and if ZF is
             * set; otherwise the instruction following LOOPE/LOOPZ is executed.
             */
            case LOOPE__LOOPZ_SHORT: //  0xe1: // LOOPE/LOOPZ SHORT-LABEL
            /*
             * LOOPNE/LOOPNZ short-label
             *
             * LOOPNE and LOOPNZ (Loop While Not Equal and Loop While Not Zero)
             * are also synonyms for the same instruction. CX is decremented by
             * 1, and control is transferred to the target operand if CX is not
             * 0 and if ZF is clear; otherwise the next sequential instruction
             * is executed.
             */
            case LOOPNE__LOOPNZ_SHORT: //   0xe0: // LOOPNE/LOOPNZ SHORT-LABEL

                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 15;

    }

    public int getIpInc() {
        return disp;
    }
}

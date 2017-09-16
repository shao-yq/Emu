package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/30.
 */
/*
 * CALL procedure-name
 *
 * CALL activated an out-of-line procedure, saving information on
 * the stack to permit a RET (return) instruction in the procedure
 * to transfer control back to the instruction following the CALL.
 * The assembler generates a different type of CALL instruction
 * depending on whether the programmer has defined the procedure
 * name as NEAR or FAR. For control to return properly, the type of
 * CALL instruction must match the type of RET instruction that
 * exists from the procedure. (The potential for a mismatch exists
 * if the procedure and the CALL are contained in separately
 * assembled programs.) Different forms of the CALL instruction
 * allow the address of the target procedure to be obtained from the
 * instruction itself (direct CALL) or from a memory location or
 * register referenced by the instruction (indirect CALL). In the
 * following descriptions, bear in mind that the processor
 * automatically adjusts IP to point to the next instruction to be
 * executed before saving it on the stack.
 *
 * For an intrasegment direct CALL, SP (the stack pointer) is
 * decremented by two and IP is pushed onto the stack. The relative
 * displacement (up to ±32k) of the target procedure from the CALL
 * instruction is then added to the instruction pointer. This form
 * of the CALL instruction is "self-relative" and is appropriate for
 * position independent (dynamically relocatable) routines in which
 * the CALL and its target are in the same segment and are moved
 * together.
 *
 * An intrasegment indirect CALL may be made through memory or
 * through a register. SP is decremented by two and IP is pushed
 * onto the stack. The offset of the target procedure is obtained
 * from the memory word or 16-bit general register referenced in the
 * instruction and replaces IP.
 *
 * For an intersegment direct CALL, SP is decremented by two, and CS
 * is pushed onto the stack. CS is replaced by the segment word
 * contained in the instruction. SP again is decremented by two. IP
 * is pushed onto the stack and is replaced by the offset word
 * contained in the instruction.
 *
 * For an intersegment indirect CALL (which only may be made through
 * memory), SP is decremented by two, and CS is pushed onto the
 * stack. CS is then replaced by the content of the second word of
 * the doubleword memory pointer referenced by the instruction. SP
 * again is decremented by two, and IP is pushed onto the stack and
 * is replaced by the content of the first word of the doubleword
 * pointer referenced by the instruction.
 */
public class CallNearProc extends Instruction8086{
    public CallNearProc(int[] raw, int startIndex) {
        super(raw, startIndex);
        // Near address
        disp = raw[1+startIndex]|(raw[2+startIndex]<<8);
        incLength(2);
    }

    public static boolean hasOpcode(int raw) {
        switch (raw){

            // Direct with Segment
            case CALL_NEAR_PROC: //   0xe8: // CALL NEAR-PROC
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 19;
    }

    public int getIpInc() {
        return disp;
    }
}

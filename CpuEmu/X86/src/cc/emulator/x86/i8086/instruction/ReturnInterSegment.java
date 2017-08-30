package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/30.
 */
public class ReturnInterSegment extends Instruction8086{
    public ReturnInterSegment(int[] raw) {
        super(raw);
    }
    public static boolean hasOpcode(int raw) {
        switch (raw){

            /*
             * RET optional-pop-value
             *
             * RET (Return) transfers control from a procedure back to the
             * instruction following the CALL that activated the procedure. The
             * assembler generates an intrasegment RET if the programmer has
             * defined the procedure NEAR, or an intersegment RET if the
             * procedure has been defined as FAR. RET pops the word at the top
             * of the stack (pointed to by the register SP) into the instruction
             * pointer and increments SP by two. If RET is intersegment, the
             * word at the top of the stack is popped into the CS register, and
             * SP is again incremented by two. If an optional pop value has been
             * specified, RET adds that value to SP. This feature may be used to
             * discard parameters pushed onto the stack before the execution of
             * the CALL instruction.
             */
            // Intersegment
            case RET_INTERSEGMENT: //  0xcb: // RET (intersegment)
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 18;
    }
}

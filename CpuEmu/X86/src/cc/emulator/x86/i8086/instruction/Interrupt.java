package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;
import cc.emulator.x86.i8086.Intel8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/31.
 */
/*
 * Interrupt Instructions
 *
 * The interrupt instructions allow interrupt service routines to be
 * activated by programs as well as by external hardware devices.
 * The effect of software interrupts is similar to hardware-
 * initiated interrupts. However, the processor does not execute an
 * interrupt acknowledge bus cycle if the interrupt originates in
 * software or with an NMI. The effect of the interrupt instructions
 * on the flags is covered in the description of each instruction.
 */
public class Interrupt extends Instruction8086 {
    public Interrupt(int[] raw, int startIndex) {
        super(raw, startIndex);
        if(op == INT_IMMED8){
            // DATA-8
            immediate = raw[1+startIndex];
            incLength(1);
        }
    }

    public static boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch (raw) {
            /*
             * INT interrupt-type
             *
             * INT (Interrupt) activates the interrupt procedure specified by
             * the interrupt-type operand. INT decrements the stack pointer by
             * two, pushes the flags onto the stack, and clears the trap (TF)
             * and interrupt-enable (IF) flags to disable single-step and
             * maskable interrupts. The flags are stored in the format used by
             * the PUSHF instruction. SP is decremented again by two, and the CS
             * register is pushed onto the stack. The address of the interrupt
             * pointer is calculated by multiplying interrupt-type by four; the
             * second word of the interrupt pointer replaces CS. SP again is
             * decremented by two, and IP is pushed onto the stack and is
             * replaced by the first word of the interrupt pointer. If
             * interrupt-type = 3, the assembler generates a short (1 byte) form
             * of the instruction, known as the breakpoint interrupt.
             *
             * Software interrupts can be used as "supervisor calls," i.e.,
             * requests for service from an operating system. A different
             * interrupt-type can be used for each type of service that the
             * operating system could supply for an application program.
             * Software interrupts also may be used to check out interrupt
             * service procedures written for hardware-initiated interrupts.
             */
            // Type 3
            case INT_3: //  0xcc: // INT 3
                // Type Specified
            case INT_IMMED8: //  0xcd: // INT IMMED8
            /*
             * INTO
             *
             * INTO (Interrupt on Overflow) generates a software interrupt if
             * the overflow flag (OF) is set; otherwise control proceeds to the
             * following instruction without activating an interrupt procedure.
             * INTO addresses the target interrupt procedure (its type is 4)
             * through the interrupt pointer at location 1OH; it clears the TF
             * and IF flags and otherwise operates like INT. INTO may be written
             * following an arithmetic or logical operation to activate an
             * interrupt procedure if overflow occurs.
             */
            case INTO: //  0xce: // INTO
                return true;
        }
        return false;
    }

}

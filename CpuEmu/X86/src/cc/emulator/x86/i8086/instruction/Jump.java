package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/30.
 */
/*
 * JMP target
 *
 * JMP unconditionally transfers control to the target location.
 * Unlike a CALL instruction, JMP does not save any information on
 * the stack, and no return to the instruction following the JMP is
 * expected. Like CALL, the address of the target operand may be
 * obtained from the instruction itself (direct JMP) or from memory
 * or a register referenced by the instruction (indirect JMP).
 *
 * An intrasegment direct JMP changes the instruction pointer by
 * adding the relative displacement of the target from the JMP
 * instruction. If the assembler can determine that the target is
 * within 127 bytes of the JMP, it automatically generates a two-
 * byte form of this instruction called a SHORT JMP; otherwise, it
 * generates a NEAR JMP that can address a target within ±32k.
 * Intrasegment direct JMPS are self-relative and are appropriate in
 * position-independent (dynamically relocatable) routines in which
 * the JMP and its target are in the same segment and are moved
 * together.
 *
 * An intrasegment indirect JMP may be made either through memory or
 * through a 16-bit general register. In the first case, the content
 * of the word referenced by the instruction replaces the
 * instruction pointer. In the second case, the new IP value is
 * taken from the register named in the instruction.
 *
 * An intersegment direct JMP replaces IP and CS with values
 * contained in the instruction.
 *
 * An intersegment indirect JMP may be made only through memory. The
 * first word of the doubleword pointer referenced by the
 * instruction replaces IP, and the second word replaces CS.
 */
public class Jump extends Instruction8086{
    public Jump(){}
    public Jump(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    public void decode(int[] raw, int startIndex) {
        decode(raw, 1, startIndex);
        disp = 0;
        switch(op) {
            // Direct Intersegment
            case JMP_FAR: //   0xea: // JMP FAR-LABEL
                immediate = raw[3+startIndex] | (raw[4+startIndex]<<8);
                incLength(2);
                // Direct within Segment
            case JMP_NEAR: //   0xe9: // JMP NEAR-LABEL
                // Near address
                disp |= raw[2+startIndex]<<8;
                incLength(1);
                // Direct within Segment-Short
            case JMP_SHORT: //   0xeb: // JMP SHORT-LABEL
                disp |= raw[1+startIndex];
                incLength(1);
                break;
        }
    }

    public  boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch (raw){
            // Direct Intersegment
            case JMP_FAR: //   0xea: // JMP FAR-LABEL
                // Direct within Segment
            case JMP_NEAR: //   0xe9: // JMP NEAR-LABEL
                // Direct within Segment-Short
            case JMP_SHORT: //   0xeb: // JMP SHORT-LABEL
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 15;
//        switch (op) {
//            // Direct Intersegment
//            case JMP_FAR: //   0xea: // JMP FAR-LABEL
//                // Direct within Segment
//            case JMP_NEAR: //   0xe9: // JMP NEAR-LABEL
//                // Direct within Segment-Short
//            case JMP_SHORT: //   0xeb: // JMP SHORT-LABEL
//        }
    }

    public int getIpInc() {
        return disp;
    }
    public int getBase() {
        return immediate;
    }

    public int getOffset() {
        return disp;
    }


    @Override
    protected String getOperandPart() {
        StringBuffer asm = new StringBuffer();
        switch (op) {
            // Direct Intersegment
            case JMP_FAR: //   0xea: // JMP FAR-LABEL
                asm.append(" FAR "+getBase()+":"+getIpInc());
                break;
                // Direct within Segment
            case JMP_NEAR: //   0xe9: // JMP NEAR-LABEL
                asm.append(" NEAR #"+getIpInc());
                break;
                // Direct within Segment-Short
            case JMP_SHORT: //   0xeb: // JMP SHORT-LABEL
                asm.append(" SHORT #"+getIpInc());
                break;
        }

        return asm.toString();
    }

    @Override
    public String getMnemonic() {
        return "JMP";
    }
}

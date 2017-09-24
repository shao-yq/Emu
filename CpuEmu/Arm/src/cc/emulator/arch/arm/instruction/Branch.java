package cc.emulator.arch.arm.instruction;

import cc.emulator.arch.arm.ArmInstruction;
import cc.emulator.core.cpu.Instruction;

/**
 *  Instruction Branch:
 *  Branch, branch with link, and block data transfer
 *
 *
 *  B , BL
 *
 * 4.4 Branch and Branch with Link (B, BL)
 * The instruction is only executed if the condition is true. The various conditions are
 * defined Table 4-2: Condition code summary on page 4-5. The instruction encoding
 * is shown in Figure 4-3: Branch instructions, below.
 * 
 * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7  6  5  4| 3  2  1  0|Instruction Type
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
 * | Condition | 1| 0| 1| L|                      Offset                                           |  Branch
 * |-----------|--+--+--+--+-----------|-----------|-----------|-----------------------------------|------------------------------
 * Condition field       Link bit
 *                       0 = Branch
 *                       1 = Branch with Link
 * 
 * Branch instructions contain a signed 2’s complement 24 bit offset. This is shifted left
 * two bits, sign extended to 32 bits, and added to the PC. The instruction can therefore
 * specify a branch of +/- 32Mbytes. The branch offset must take account of the prefetch
 * operation, which causes the PC to be 2 words (8 bytes) ahead of the current instruction.
 * Branches beyond +/- 32Mbytes must use an offset or absolute destination which has
 * been previously loaded into a register. In this case the PC should be manually saved in
 * R14 if a Branch with Link type operation is required.
 * 
 * 4.4.1 The link bit
 * Branch with Link (BL) writes the old PC into the link register (R14) of the current bank.
 * The PC value written into R14 is adjusted to allow for the prefetch, and contains the
 * address of the instruction following the branch and link instruction. Note that the CPSR
 * is not saved with the PC and R14[1:0] are always cleared.
 * To return from a routine called by Branch with Link use MOV PC,R14 if the link register
 * is still valid or LDM Rn!,{..PC} if the link register has been saved onto a stack pointed
 * to by Rn.
 * 4.4.2 Instruction cycle times
 * Branch and Branch with Link instructions take 2S + 1N incremental cycles, where S and
 * N are as defined in 6.2 Cycle Types on page 6-3.
 * 
 * 4.4.3 Assembler syntax
 * Items in {} are optional. Items in <> must be present.
 *    B{L}{cond} <expression>
 *    {L}         is used to request the Branch with Link form of the instruction.
 *                If absent, R14 will not be affected by the instruction.
 *    {cond}      is a two-character mnemonic as shown in Table 4-2:
 *                Condition code summary on page 4-5. If absent then AL (ALways) will be used.
 *    <expression> is the destination. The assembler calculates the offset.
 * 
 * 4.4.4 Examples
 * here   BAL here    ; assembles to 0xEAFFFFFE (note effect of PC offset).
 *        B there     ; Always condition used as default.
 *        CMP R1,#0   ; Compare R1 with zero and branch to fred if R1 was zero, otherwise continue
 *        BEQ fred    ; continue to next instruction.
 *        BL sub+ROM  ; Call subroutine at computed address.
 *        ADDS R1,#1  ; Add 1 to register 1, setting CPSR flags on the result then call subroutine if
 *        BLCC sub    ; the C flag is clear, which will be the case unless R1 held 0xFFFFFFFF.
 * 
 * 
 * Mnemonic| Instruction                           |   Action                  | See Section:
 * --------+---------------------------------------+---------------------------+--------------
 * B       | Branch                                | R15 := address            | 4.4
 * BL      | Branch with Link                      | R14 := R15, R15 := address| 4.4
 * 
 *


 
 *  @author Shao Yongqing
 */
public class Branch extends ArmInstruction {
    private  boolean linkFlag;

    public Branch(){
    }

    public Branch(int[] queue) {
        super(queue);
    }

    @Override
    public void decode(int[] raw, int startIndex) {
        super.decode(raw, startIndex);

        // Immediate
        extractImmediate();
        // Link flag
        linkFlag =  (rawInstruction & 0x01000000)!=0;

        setWritesPC (true);
        setFixedJump(true);
    }

    private void extractImmediate() {
        immediate = rawInstruction & 0x00FFFFFF;
        if ((immediate & 0x00800000) != 0) {
            // Extends sign
            immediate |= 0xFF000000;
        }
        immediate <<= 2;
    }

    @Override
    public int getOpCode() {
        return 0;
    }

    @Override
    public boolean hasOpcode(int[] queue, int startIndex) {
        int raw = queue[startIndex];
        int op = (raw>>25) & 0b111;
        if(op == 0b101)
            return true;

        return false;
    }

}

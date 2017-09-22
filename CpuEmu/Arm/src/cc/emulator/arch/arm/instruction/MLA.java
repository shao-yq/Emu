package cc.emulator.arch.arm.instruction;

import cc.emulator.arch.arm.ArmInstruction;

/**
 * MLA instruction
 *
 * Multiply Accumulate multiplies two register values, and adds a third register value. The least significant 32 bits of
 * the result are written to the destination register. These 32 bits do not depend on whether the source register values
 * are considered to be signed values or unsigned values.
 * In an ARM instruction, the condition flags can optionally be updated based on the result. Use of this option
 * adversely affects performance on many processor implementations.
 *
 * Encoding T1 ARMv6T2, ARMv7
 *  MLA<c> <Rd>, <Rn>, <Rm>, <Ra>.
 * -------------------------------------------------------------------------------------------------
 * |15 14 13 12|11|10| 9| 8  7| 6| 5| 4| 3  2  1  0|15 14 13 12|11 10  9  8| 7| 6  5| 4| 3  2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * | 1  1  1  1  1| 0  1  1  0| 0  0  0|    Rn     |   Ra      |    Rd     | 0  0  0  0|    Rm     |
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * 
 *  if Ra == '1111' then SEE MUL;
 *  d = UInt(Rd); n = UInt(Rn); m = UInt(Rm); a = UInt(Ra); setflags = FALSE;
 *  if d IN {13,15} || n IN {13,15} || m IN {13,15} || a == 13 then UNPREDICTABLE;
 * 
 * Encoding A1 ARMv4*, ARMv5T*, ARMv6*, ARMv7
 *   MLA{S}<c> <Rd>, <Rn>, <Rm>, <Ra>
 * -------------------------------------------------------------------------------------------------
 * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7| 6  5| 4| 3  2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * | Condition | 0  0 0 | 0  0  0  1| S| Rd        |    Ra     |     Rm    | 1  0  0  1|    Rn     |
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * 
 *
 * d = UInt(Rd); n = UInt(Rn); m = UInt(Rm); a = UInt(Ra); setflags = (S == '1');
 * if d == 15 || n == 15 || m == 15 || a == 15 then UNPREDICTABLE;
 * if ArchVersion() < 6 && d == n then UNPREDICTABLE;
 *
 *  Assembler syntax
 *      MLA{S}{<c>}{<q>} <Rd>, <Rn>, <Rm>, <Ra>
 *  where:
 *      S           If S is present, the instruction updates the flags. Otherwise, the flags are not updated.
 *      S           can be specified only for the ARM instruction set.
 *      <c>, <q>    See Standard assembler syntax fields on page A8-287.
 *      <Rd>        The destination register.
 *      <Rn>        The first operand register.
 *      <Rm>        The second operand register.
 *      <Ra>        The register containing the accumulate value.
 *  The pre-UAL syntax MLA<c>S is equivalent to MLAS<c>.    
 *
 *  Operation
 *  if ConditionPassed() then
 *      EncodingSpecificOperations();
 *      operand1 = SInt(R[n]);  // operand1 = UInt(R[n]) produces the same final results
 *      operand2 = SInt(R[m]);  // operand2 = UInt(R[m]) produces the same final results
 *      addend = SInt(R[a]);    // addend = UInt(R[a]) produces the same final results
 *      result = operand1 * operand2 + addend;
 *      R[d] = result<31:0>;
 *      if setflags then
 *          APSR.N = result<31>;
 *          APSR.Z = IsZeroBit(result<31:0>);
 *          if ArchVersion() == 4 then
 *              APSR.C = bit UNKNOWN;
 *          // else APSR.C unchanged
 *          // APSR.V always unchanged    
 *                                          
 *  Exceptions
 *  None.
 *   
 * @author Shao Yongqing
 * Date: 2017/9/22.
 */
public class MLA extends MUL {


    private int Ra;

    public MLA(int rawInstruction[]) {
        super(rawInstruction);
    }
    public MLA(int rawInstruction) {
        this(new int[]{rawInstruction});
    }

    @Override
    public boolean hasOpcode(int[] queue, int startIndex) {
        int raw = queue[startIndex];
        
        int cond = (raw>>28)&0xf;
        int op1 = (raw >> 21) & 0b1111111;
        int op2 = (raw >> 4)  & 0b1111;
        
        if((cond!=0xf) && (op1==0x01) && (op2==0x9))
            return true;
        
        return false;
    }

    /**
     *
     * Encoding A1 ARMv4*, ARMv5T*, ARMv6*, ARMv7
     *   MLA{S}<c> <Rd>, <Rn>, <Rm>, <Ra>
     * -------------------------------------------------------------------------------------------------
     * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7| 6  5| 4| 3  2  1  0|
     * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
     * | Condition | 0  0 0 | 0  0  0  1| S| Rd        |    Ra     |     Rm    | 1  0  0  1|    Rn     |
     * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
     *
     */
    @Override
    public void decode(int[] raw, int startIndex) {
        super.decode(raw, startIndex);

        Ra = (rawInstruction>>12) & 0xf;
    }


    public int getRa() {
        return Ra;
    }
}

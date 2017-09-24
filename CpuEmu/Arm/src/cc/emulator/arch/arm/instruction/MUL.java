package cc.emulator.arch.arm.instruction;

import cc.emulator.arch.arm.ArmInstruction;

/**
 * MUL instruction
 *
 * Multiply multiplies two register values. The least significant 32 bits of the result are written to the destination
 * register. These 32 bits do not depend on whether the source register values are considered to be signed values or
 * unsigned values.
 * Optionally, it can update the condition flags based on the result. In the Thumb instruction set, this option is limited
 * to only a few forms of the instruction. Use of this option adversely affects performance on many processor
 * implementations.
 *
 * Encoding T1 ARMv4T, ARMv5T*, ARMv6*, ARMv7
 *  MULS <Rdm>, <Rn>, <Rdm> Outside IT block.
 *  MUL<c> <Rdm>, <Rn>, <Rdm> Inside IT block.
 * -------------------------------------------------
 * |15 14 13 12|11|10| 9| 8  7| 6| 5| 4| 3| 2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|
 * | 0  1  0  0  0  0| 1  1  0  1|   Rn   | Rdm    |
 * |-----------|--+--+--+-----------+--|-----------|
 * 
 * d = UInt(Rdm); n = UInt(Rn); m = UInt(Rdm); setflags = !InITBlock();
 * if ArchVersion() < 6 && d == n then UNPREDICTABLE;
 *
 * Encoding T2 ARMv6T2, ARMv77
 *  MUL<c> <Rd>, <Rn>, <Rm>
 * -------------------------------------------------------------------------------------------------
 * |15 14 13 12|11|10| 9| 8  7| 6| 5| 4| 3  2  1  0|15 14 13 12|11 10  9  8| 7| 6  5| 4| 3  2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * | 1  1  1  1  1| 0  1  1  0| 0  0  0|    Rn     | 1  1  1  1|    Rd     | 0  0  0  0|    Rm     |
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * 
 * d = UInt(Rd); n = UInt(Rn); m = UInt(Rm); setflags = FALSE;
 * if d IN {13,15} || n IN {13,15} || m IN {13,15} then UNPREDICTABLE;
 * 
 * Encoding A1 ARMv4*, ARMv5T*, ARMv6*, ARMv7
 *   MUL{S}<c> <Rd>, <Rn>, <Rm>
 * -------------------------------------------------------------------------------------------------
 * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7| 6  5| 4| 3  2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * | Condition | 0| 0|0 | 0  0  0  0| S| Rd        |(0)(0)(0)(0|     Rm    | 1  0  0  1|    Rn     |
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * 
 *
 *  d = UInt(Rd); n = UInt(Rn); m = UInt(Rm); setflags = (S == '1');
 *  if d == 15 || n == 15 || m == 15 then UNPREDICTABLE;
 *  if ArchVersion() < 6 && d == n then UNPREDICTABLE;
 *
 *  Assembler syntax
 *      MUL{S}{<c>}{<q>} <Rd>, <Rn>{, <Rm>}
 *  where:
 *      S           If S is present, the instruction updates the flags. Otherwise, the flags are not updated.
 *                  In the Thumb instruction set, S can be specified only if both <Rn> and <Rm> are R0-R7 and the
 *                  instruction is outside an IT block.
 *      <c>, <q>    See Standard assembler syntax fields on page A8-287.
 *      <Rd>        The destination register.
 *      <Rn>        The first operand register.
 *      <Rm>        The second operand register. If omitted, <Rd> is used.
 *  Note
 *  Issues A and B of this document showed the MUL syntax as MUL{S}{<c>}{<q>} {<Rd>, }<Rn>, <Rm>. The <Rm> register
 *  is now made optional because omitting <Rd> can generate UNPREDICTABLE instructions in some cases. Some
 *  assembler versions might not support this revised specification.
 *  The pre-UAL syntax MUL<c>S is equivalent to MULS<c>.
 *
 *  Operation
 *  if ConditionPassed() then
 *      EncodingSpecificOperations();
 *      operand1 = SInt(R[n]); // operand1 = UInt(R[n]) produces the same final results
 *      operand2 = SInt(R[m]); // operand2 = UInt(R[m]) produces the same final results
 *      result = operand1 * operand2;
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
 *      None.
 * @author Shao Yongqing
 * Date: 2017/9/22.
 */
public class MUL extends DataProcessMisc {


    public MUL(int rawInstruction[]) {
        super(rawInstruction);
    }
    public MUL(int rawInstruction) {
        this(new int[]{rawInstruction});
    }

    @Override
    public boolean hasOpcode(int[] queue, int startIndex) {
        int raw = queue[startIndex];
        
        int cond = (raw>>28)&0xf;
        int op1 = (raw >> 21) & 0b1111111;
        int op2 = (raw >> 4)  & 0b1111;
        
        if((cond!=0xf) && (op1==0x0) && (op2==0b1001))
            return true;
        
        return false;
    }

    /**
     *
     * Encoding A1 ARMv4*, ARMv5T*, ARMv6*, ARMv7
     *   MUL{S}<c> <Rd>, <Rn>, <Rm>
     * -------------------------------------------------------------------------------------------------
     * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7| 6  5| 4| 3  2  1  0|
     * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
     * | Condition | 0| 0|0 | 0  0  0  0| S| Rd        |(0)(0)(0)(0|     Rm    | 1  0  0  1|    Rn     |
     * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
     * 
     *
     */
    @Override
    public void decode(int[] raw, int startIndex) {
        super.decode(raw, startIndex);

        setFlag = ((rawInstruction>>20)&0x1) == 1;
        
        Rd = (rawInstruction>>16) & 0xf;
        Rm = (rawInstruction>>8)  & 0xf;
        Rn = (rawInstruction>>0)  & 0xf;
    }


}

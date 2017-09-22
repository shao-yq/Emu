package cc.emulator.arch.arm.instruction;

import cc.emulator.arch.arm.ArmInstruction;

/**
 * SMULL instruction
 *
 * Signed Multiply Long multiplies two 32-bit signed values to produce a 64-bit result.
 * In ARM instructions, the condition flags can optionally be updated based on the result. Use of this option adversely
 * affects performance on many processor implementations.
 *
 *
 * Encoding T1 ARMv6T2, ARMv7
 *  SMULL<c> <RdLo>, <RdHi>, <Rn>, <Rm>
 * -------------------------------------------------------------------------------------------------
 * |15 14 13 12|11|10| 9| 8  7  6| 5| 4| 3  2  1  0|15 14 13 12|11 10  9  8| 7| 6  5| 4| 3  2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * | 1  1  1  1  1| 0  1  1  1  0| 0  0|    Rn     | RdLo      |    RdHi   | 0  0  0  0|    Rm     |
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * 
 *
 *  dLo = UInt(RdLo); dHi = UInt(RdHi); n = UInt(Rn); m = UInt(Rm); setflags = FALSE;
 *  if dLo IN {13,15} || dHi IN {13,15} || n IN {13,15} || m IN {13,15} then UNPREDICTABLE;
 *  if dHi == dLo then UNPREDICTABLE;
 *   *
 * Encoding A1 ARMv4*, ARMv5T*, ARMv6*, ARMv7
 *  SMULL{S}<c> <RdLo>, <RdHi>, <Rn>, <Rm>
 * -------------------------------------------------------------------------------------------------
 * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7| 6  5| 4| 3  2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * | Condition | 0  0 0   0  1  1  0| S| RdHi      | RdLo      |     Rm    | 1  0  0  1|    Rn     |
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * 
 *
 *  dLo = UInt(RdLo); dHi = UInt(RdHi); n = UInt(Rn); m = UInt(Rm); setflags = (S == "1");
 *  if dLo == 15 || dHi == 15 || n == 15 || m == 15 then UNPREDICTABLE;
 *  if dHi == dLo then UNPREDICTABLE;
 *  if ArchVersion() < 6 && (dHi == n || dLo == n) then UNPREDICTABLE;
 *
 *  Assembler syntax
 *      SMULL{S}{<c>}{<q>} <RdLo>, <RdHi>, <Rn>, <Rm>
 *  where:
 *  S           If S is present, the instruction updates the flags. Otherwise, the flags are not updated.
 *  S           can be specified only for the ARM instruction set.
 *  <c>, <q>    See Standard assembler syntax fields on page A8-287.
 *  <RdLo>      Stores the lower 32 bits of the result.
 *  <RdHi>      Stores the upper 32 bits of the result.
 *  <Rn>        The first operand register.
 *  <Rm>        The second operand register.
 *  The pre-UAL syntax SMULL<c>S is equivalent to SMULLS<c>.
 *
 *  Operation
 *  if ConditionPassed() then
 *      EncodingSpecificOperations();
 *      result = SInt(R[n]) * SInt(R[m]);
 *      R[dHi] = result<63:32>;
 *      R[dLo] = result<31:0>;
 *      if setflags then
 *          APSR.N = result<63>;
 *          APSR.Z = IsZeroBit(result<63:0>);
 *          if ArchVersion() == 4 then
 *              APSR.C = bit UNKNOWN;
 *              APSR.V = bit UNKNOWN;
 *          // else APSR.C, APSR.V unchanged
 *  Exceptions
 *      None.
 *
 * @author Shao Yongqing
 * Date: 2017/9/22.
 */
public class SMULL extends MUL {
    private int RdHi;
    private int RdLo;

    public SMULL(int rawInstruction[]) {
        super(rawInstruction);
    }
    public SMULL(int rawInstruction) {
        this(new int[]{rawInstruction});
    }

    @Override
    public boolean hasOpcode(int[] queue, int startIndex) {
        int raw = queue[startIndex];
        
        int cond = (raw>>28)&0xf;
        int op1 = (raw >> 21) & 0b1111111;
        int op2 = (raw >> 4)  & 0b1111;
        
        if((cond!=0xf) && (op1==0x6) && (op2==0x9))
            return true;
        
        return false;
    }

    /**
     *
     * Encoding A1 ARMv4*, ARMv5T*, ARMv6*, ARMv7
     *  SMULL{S}<c> <RdLo>, <RdHi>, <Rn>, <Rm>
     * -------------------------------------------------------------------------------------------------
     * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7| 6  5| 4| 3  2  1  0|
     * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
     * | Condition | 0  0 0   0  1  1  0| S| RdHi      | RdLo      |     Rm    | 1  0  0  1|    Rn     |
     * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
     * 
     *
     */
    @Override
    public void decode(int[] raw, int startIndex) {
        super.decode(raw, startIndex);

        RdHi = (rawInstruction>>16) & 0xf;
        RdLo = (rawInstruction>>12) & 0xf;
    }

    public int getRdHi() {
        return RdHi;
    }

    public int getRdLo() {
        return RdLo;
    }
}

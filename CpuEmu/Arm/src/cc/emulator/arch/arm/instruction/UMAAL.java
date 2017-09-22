package cc.emulator.arch.arm.instruction;

import cc.emulator.arch.arm.ArmInstruction;

/**
 * UMAAL instruction
 *
 * Unsigned Multiply Accumulate Accumulate Long multiplies two unsigned 32-bit values to produce a 64-bit value,
 * adds two unsigned 32-bit values, and writes the 64-bit result to two registers.
 *
 *
 * Encoding T1 ARMv6T2, ARMv7
 *  UMAAL<c> <RdLo>, <RdHi>, <Rn>, <Rm>
 * -------------------------------------------------------------------------------------------------
 * |15 14 13 12|11|10| 9| 8  7| 6| 5| 4| 3  2  1  0|15 14 13 12|11 10  9  8| 7| 6  5| 4| 3  2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * | 1  1  1  1  1| 0  1  1  1| 1  1  0|    Rn     | RdLo      |    RdHi   | 0  1  1  0|    Rm     |
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * 
 *
 *  dLo = UInt(RdLo); dHi = UInt(RdHi); n = UInt(Rn); m = UInt(Rm);
 *  if dLo IN {13,15} || dHi IN {13,15} || n IN {13,15} || m IN {13,15} then UNPREDICTABLE;
 *  if dHi == dLo then UNPREDICTABLE;
 * 
 * Encoding A1 ARMv6*, ARMv7
 *  UMAAL<c> <RdLo>, <RdHi>, <Rn>, <Rm>
 * -------------------------------------------------------------------------------------------------
 * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7| 6  5| 4| 3  2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * | Condition | 0  0  0  0  0  1  0  0| RdHi      | RdLo      |     Rm    | 1  0  0  1|    Rn     |
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * 
 *
 *  dLo = UInt(RdLo); dHi = UInt(RdHi); n = UInt(Rn); m = UInt(Rm);
 *  if dLo == 15 || dHi == 15 || n == 15 || m == 15 then UNPREDICTABLE;
 *  if dHi == dLo then UNPREDICTABLE;
 *
 *  Assembler syntax
 *      UMAAL{<c>}{<q>} <RdLo>, <RdHi>, <Rn>, <Rm>
 *  where:
 *  <c>, <q>    See Standard assembler syntax fields on page A8-287.
 *  <RdLo>      Supplies one of the 32-bit values to be added, and is the destination register for the lower 32 bits of
 *              the result.
 *  <RdHi>      Supplies the other of the 32-bit values to be added, and is the destination register for the upper
 *              32 bits of the result.
 *  <Rn>        The register that contains the first multiply operand.
 *  <Rm>        The register that contains the second multiply operand.
 *
 *  Operation
 *  if ConditionPassed() then
 *      EncodingSpecificOperations();
 *      result = UInt(R[n]) * UInt(R[m]) + UInt(R[dHi]) + UInt(R[dLo]);
 *      R[dHi] = result<63:32>;
 *      R[dLo] = result<31:0>;
 *  Exceptions
 *      None. 
 *
 * @author Shao Yongqing
 * Date: 2017/9/22.
 */
public class UMAAL extends UMULL {
//    private int RdHi;
//    private int RdLo;

    public UMAAL(int rawInstruction[]) {
        super(rawInstruction);
    }
    public UMAAL(int rawInstruction) {
        this(new int[]{rawInstruction});
    }

    @Override
    public boolean hasOpcode(int[] queue, int startIndex) {
        int raw = queue[startIndex];
        
        int cond = (raw>>28)&0xf;
        int op1 = (raw >> 20) & 0b11111111;
        int op2 = (raw >> 4)  & 0b1111;
        
        if((cond!=0xf) && (op1==0x04) && (op2==0x9))
            return true;
        
        return false;
    }

    /**
     *
     * Encoding A1 ARMv6*, ARMv7
     *  UMAAL<c> <RdLo>, <RdHi>, <Rn>, <Rm>
     * -------------------------------------------------------------------------------------------------
     * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7| 6  5| 4| 3  2  1  0|
     * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
     * | Condition | 0  0  0  0  0  1  0  0| RdHi      | RdLo      |     Rm    | 1  0  0  1|    Rn     |
     * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
     * 
     *
     */
    @Override
    public void decode(int[] raw, int startIndex) {
        super.decode(raw, startIndex);

//        RdHi = (rawInstruction>>16) & 0xf;
//        RdLo = (rawInstruction>>12) & 0xf;
    }

}

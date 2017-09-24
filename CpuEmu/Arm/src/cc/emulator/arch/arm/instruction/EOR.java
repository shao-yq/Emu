package cc.emulator.arch.arm.instruction;

import cc.emulator.arch.arm.ArmInstruction;
import cc.emulator.core.cpu.Instruction;

/**
 * EOR instruction
 *
 * 1. EOR (immediate)
 *
 *  Bitwise Exclusive OR (immediate) performs a bitwise Exclusive OR of a register value and an immediate value,
 *  and writes the result to the destination register. It can optionally update the condition flags based on the result.
 *  Encoding T1 ARMv6T2, ARMv7
 *  EOR{S}<c> <Rd>, <Rn>, #<const>
 * -------------------------------------------------------------------------------------------------
 * |15 14 13 12|11|10| 9| 8  7| 6| 5| 4| 3  2  1  0|15 14 13 12|11 10  9  8| 7| 6| 5| 4| 3  2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * | 1  1  1  1  0| 1| 0| 0  1  0  0| S|    Rn     | 0| imm3   |    Rd     |        imm8           |
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 *  
 *  
 *  if Rd == '1111' && S == '1' then SEE TEQ (immediate);
 *  d = UInt(Rd); n = UInt(Rn); setflags = (S == '1');
 *  (imm32, carry) = ThumbExpandImm_C(i:imm3:imm8, APSR.C);
 *  if d == 13 || (d == 15 && S == '0') || n IN {13,15} then UNPREDICTABLE;
 *  
 *  Encoding A1 ARMv4*, ARMv5T*, ARMv6*, ARMv7
 *  EOR{S}<c> <Rd>, <Rn>, #<const>
 * -------------------------------------------------------------------------------------------------
 * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7| 6  5| 4| 3  2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * | Condition | 0  0| 1| 0  0  0  1| S|   Rn      |    Rd     |         imm12                     |
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 *  
 *  if Rd == '1111' && S == '1' then SEE SUBS PC, LR and related instructions;
 *  d = UInt(Rd); n = UInt(Rn); setflags = (S == '1');
 *  (imm32, carry) = ARMExpandImm_C(imm12, APSR.C);
 *  
 *  Assembler syntax
 *      EOR{S}{<c>}{<q>} {<Rd>,} <Rn>, #<const>
 *  where:
 *  S           If S is present, the instruction updates the flags. Otherwise, the flags are not updated.
 *  <c>, <q>    See Standard assembler syntax fields on page A8-287.
 *  <Rd>        The destination register. If S is specified and <Rd> is the PC, see SUBS PC, LR (Thumb) on
 *              page B9-2010 or SUBS PC, LR and related instructions (ARM) on page B9-2012.
 *              In ARM instructions, if S is not specified and <Rd> is the PC, the instruction is a branch to the address
 *              calculated by the operation. This is an interworking branch, see Pseudocode details of operations
 *              on ARM core registers on page A2-47. ARM deprecates this use of the PC.
 *              
 *              ------Note------
 *              Before ARMv7, this was a simple branch.
 *              ------------------
 *  <Rn>        The register that contains the operand. The PC can be used in ARM instructions, but ARM
 *              deprecates this use of the PC.
 *  <const>     The immediate value to be exclusive ORed with the value obtained from <Rn>. See Modified
 *              immediate constants in Thumb instructions on page A6-232 or Modified immediate constants in
 *              ARM instructions on page A5-200 for the range of values.
 *  The pre-UAL syntax EOR<c>S is equivalent to EORS<c>.
 *
 *  Operation
 *  if ConditionPassed() then
 *      EncodingSpecificOperations();
 *      result = R[n] EOR imm32;
 *      if d == 15 then // Can only occur for ARM encoding
 *          ALUWritePC(result); // setflags is always FALSE here
 *      else
 *          R[d] = result;
 *          if setflags then
 *              APSR.N = result<31>;
 *              APSR.Z = IsZeroBit(result);
 *              APSR.C = carry;
 *              // APSR.V unchanged
 *  Exceptions
 *      None.
 *
 * EOR (register)
 *  Bitwise Exclusive OR (register) performs a bitwise Exclusive OR of a register value and an optionally-shifted
 *  register value, and writes the result to the destination register. It can optionally update the condition flags based on
 *  the result. 
 *  
 *  Encoding T1 ARMv4T, ARMv5T*, ARMv6*, ARMv7
 *  EORS <Rdn>, <Rm> Outside IT block.
 *  EOR<c> <Rdn>, <Rm> Inside IT block.
 * -------------------------------------------------
 * |15 14 13 12|11|10| 9| 8  7| 6| 5| 4| 3| 2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|
 * | 0  1  0  0  0  0| 0  0  0  1|   Rm   | Rdn    |
 * |-----------|--+--+--+-----------+--|-----------|
 *  d = UInt(Rdn); n = UInt(Rdn); m = UInt(Rm); setflags = !InITBlock();
 *  (shift_t, shift_n) = (SRType_LSL, 0);
 *  
 *  Encoding T2 ARMv6T2, ARMv7
 *  EOR{S}<c>.W <Rd>, <Rn>, <Rm>{, <shift>}
 * -------------------------------------------------------------------------------------------------
 * |15 14 13 12|11|10| 9| 8  7| 6| 5| 4| 3  2  1  0|15 14 13 12|11 10  9  8| 7| 6| 5| 4| 3  2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * | 1  1  1  0  1| 0  1| 0  0  0  0| S|    Rn     |(0| imm3   |    Rd     | imm2| type|    Rm     |
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 *  if Rd == '1111' && S == '1' then SEE TEQ (register);
 *  d = UInt(Rd); n = UInt(Rn); m = UInt(Rm); setflags = (S == '1');
 *  (shift_t, shift_n) = DecodeImmShift(type, imm3:imm2);
 *  if d == 13 || (d == 15 && S == '0') || n IN {13,15} || m IN {13,15} then UNPREDICTABLE;
 *  
 *  Encoding A1 ARMv4*, ARMv5T*, ARMv6*, ARMv7
 *  EOR{S}<c> <Rd>, <Rn>, <Rm>{, <shift>}
 *  
 * -------------------------------------------------------------------------------------------------
 * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7| 6  5| 4| 3  2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * | Condition | 0  0| 0| 0  0  0  1| S|   Rn      |    Rd     |   imm5       | type| 0|    Rm     |
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 *
 *  if Rd == '1111' && S == '1' then SEE SUBS PC, LR and related instructions;
 *  d = UInt(Rd); n = UInt(Rn); m = UInt(Rm); setflags = (S == '1');
 *  (shift_t, shift_n) = DecodeImmShift(type, imm5);
 *  
 *  Assembler syntax
 *      EOR{S}{<c>}{<q>} {<Rd>,} <Rn>, <Rm> {, <shift>}
 *  where:
 *  S           If S is present, the instruction updates the flags. Otherwise, the flags are not updated.
 *  <c>, <q>    See Standard assembler syntax fields on page A8-287.
 *  <Rd>        The destination register. If S is specified and <Rd> is the PC, see SUBS PC, LR (Thumb) on
 *              page B9-2010 or SUBS PC, LR and related instructions (ARM) on page B9-2012.
 *              In ARM instructions, if S is not specified and <Rd> is the PC, the instruction is a branch to the address
 *              calculated by the operation. This is an interworking branch, see Pseudocode details of operations
 *              on ARM core registers on page A2-47. ARM deprecates this use of the PC.
 *              Note
 *              Before ARMv7, this was a simple branch.
 *  <Rn>        The first operand register. The PC can be used in ARM instructions, but ARM deprecates this use
 *              of the PC.
 *  <Rm>        The register that is optionally shifted and used as the second operand. The PC can be used in ARM
 *              instructions, but ARM deprecates this use of the PC.
 *  <shift>     The shift to apply to the value read from <Rm>. If present, encoding T1 is not permitted. If absent, no
 *              shift is applied and all encodings are permitted.Shifts applied to a register on page A8-291 describes
 *              the shifts and how they are encoded.
 *  In Thumb assembly:
 *  -   outside an IT block, if EORS <Rd>, <Rn>, <Rd> has <Rd> and <Rn> both in the range R0-R7, it is assembled using
 *      encoding T1 as though EORS <Rd>, <Rn> had been written
 *  -   inside an IT block, if EOR<c> <Rd>, <Rn>, <Rd> has <Rd> and <Rn> both in the range R0-R7, it is assembled
 *      using encoding T1 as though EOR<c> <Rd>, <Rn> had been written.
 *  To prevent either of these happening, use the .W qualifier.
 *  The pre-UAL syntax EOR<c>S is equivalent to EORS<c>.
 *
 *  Operation
 *  if ConditionPassed() then
 *      EncodingSpecificOperations();
 *      (shifted, carry) = Shift_C(R[m], shift_t, shift_n, APSR.C);
 *      result = R[n] EOR shifted;
 *      if d == 15 then // Can only occur for ARM encoding
 *          ALUWritePC(result); // setflags is always FALSE here
 *      else
 *          R[d] = result;
 *          if setflags then
 *              APSR.N = result<31>;
 *              APSR.Z = IsZeroBit(result);
 *              APSR.C = carry;
 *              // APSR.V unchanged
 *  Exceptions
 *      None.
 *
 *

 *   
 * @author Shao Yongqing
 * Date: 2017/9/22.
 */
public class EOR extends DataProcessMisc {
    public EOR(int[] raw) {
        super(raw);
    }

    @Override
    public boolean hasOpcode(int[] queue, int startIndex) {
        int raw = queue[startIndex];
        
        int cond = (raw>>28)&0xf;
        int op1 = (raw >> 21) & 0b1111111;
        
        if(op1==0b0010001){
            // EOR (Immediate)
            return true;
        }
        if(op1==0b0000001){
            // EOR (Register)
            return true;
        }

        return false;
    }
    
    /**
     * 1. EOR (immediate)
     *  Encoding A1 ARMv4*, ARMv5T*, ARMv6*, ARMv7
     *  EOR{S}<c> <Rd>, <Rn>, #<const>
     * -------------------------------------------------------------------------------------------------
     * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7| 6  5| 4| 3  2  1  0|
     * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
     * | Condition | 0  0| 1| 0  0  0  1| S|   Rn      |    Rd     |         imm12                     |
     * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
     * 
     * 2. AND (register-shifted register)
     *  Encoding A1 ARMv4*, ARMv5T*, ARMv6*, ARMv7
     *  AND{S}<c> <Rd>, <Rn>, <Rm>, <type> <Rs>
     * -------------------------------------------------------------------------------------------------
     * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7| 6  5| 4| 3  2  1  0|
     * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
     * | Condition | 0  0| 0| 0  0  0  0| S|   Rn      |    Rd     |   Rs      | 0| type| 1|    Rm     |
     * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
     *
     */
    @Override
    public void decode(int[] raw, int startIndex) {
        super.decode(raw, startIndex);

    }

    
}

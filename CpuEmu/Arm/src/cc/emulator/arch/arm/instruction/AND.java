package cc.emulator.arch.arm.instruction;

import cc.emulator.arch.arm.ArmInstruction;

/**
 * AND Instruction
 *
 * 1. AND (immediate)
 *  This instruction performs a bitwise AND of a register value and an immediate value, and writes the result to the
 *  destination register.
 *
 *  Encoding T1 ARMv6T2, ARMv7
 *      AND{S}<c> <Rd>, <Rn>, #<const>
 * -------------------------------------------------------------------------------------------------
 * |15 14 13 12|11|10| 9| 8  7| 6| 5| 4| 3  2  1  0|15 14 13 12|11 10  9  8| 7| 6| 5| 4| 3  2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * | 1  1  1  1  0| i| 0| 0  0  0  0| S|    Rn     | 0| imm3   |    Rd     |        imm8           |
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 *  
 *  if Rd == '1111' && S == ��'1' then SEE TST (immediate);
 *  d = UInt(Rd); n = UInt(Rn); setflags = (S == ��1��);
 *  (imm32, carry) = ThumbExpandImm_C(i:imm3:imm8, APSR.C);
 *  if d == 13 || (d == 15 && S == '0') || n IN {13,15} then UNPREDICTABLE;
 *  
 *  Encoding A1 ARMv4*, ARMv5T*, ARMv6*, ARMv7
 *  AND{S}<c> <Rd>, <Rn>, #<const>
 * -------------------------------------------------------------------------------------------------
 * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7| 6  5| 4| 3  2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * | Condition | 0  0| 1| 0  0  0  0| S|   Rn      |    Rd     |         imm12                     |
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 *  
 *  if Rd == '1111' && S == '1' then SEE SUBS PC, LR and related instructions;
 *  d = UInt(Rd); n = UInt(Rn); setflags = (S == '1');
 *  (imm32, carry) = ARMExpandImm_C(imm12, APSR.C);
 *  
 *  Assembler syntax
 *      AND{S}{<c>}{<q>} {<Rd>,} <Rn>, #<const>
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
 *  <Rn>        The first operand register. The PC can be used in ARM instructions. ARM deprecates this use of the PC.
 *  <const>     The immediate value to be ANDed with the value obtained from <Rn>. See Modified immediate
 *              constants in Thumb instructions on page A6-232 or Modified immediate constants in ARM
 *              instructions on page A5-200 for the range of values.
 *  The pre-UAL syntax AND<c>S is equivalent to ANDS<c>.
 *
 *  Operation
 *  if ConditionPassed() then
 *      EncodingSpecificOperations();
 *      result = R[n] AND imm32;
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
 
 * 2. AND (register)
 * This instruction performs a bitwise AND of a register value and an optionally-shifted register value, and writes the
 * result to the destination register. It can optionally update the condition flags based on the result.
 *
 *  Encoding T1 ARMv4T, ARMv5T*, ARMv6*, ARMv7
 *      ANDS <Rdn>, <Rm>        Outside IT block.
 *      AND<c> <Rdn>, <Rm>      Inside IT block.
 * -------------------------------------------------
 * |15 14 13 12|11|10| 9| 8  7| 6| 5| 4| 3| 2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|
 * | 0  1  0  0  0  0| 0  0  0  0|   Rm   | Rn    |
 * |-----------|--+--+--+-----------+--|-----------|
 * 
 * d = UInt(Rdn); n = UInt(Rdn); m = UInt(Rm); setflags = !InITBlock();
 * (shift_t, shift_n) = (SRType_LSL, 0);
 *
 * Encoding T2 ARMv6T2, ARMv7
 *  AND{S}<c>.W <Rd>, <Rn>, <Rm>{, <shift>}
 * -------------------------------------------------------------------------------------------------
 * |15 14 13 12|11|10| 9| 8  7| 6| 5| 4| 3  2  1  0|15 14 13 12|11 10  9  8| 7| 6| 5| 4| 3  2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * | 1  1  1  0  1| 0  1| 0  0  0  0| S|    Rn     |(0| imm3   |    Rd     | imm2| type|    Rm     |
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * 
 *  if Rd == '1111' && S == '1' then SEE TST (register);
 *  d = UInt(Rd); n = UInt(Rn); m = UInt(Rm); setflags = (S == '1');
 *  (shift_t, shift_n) = DecodeImmShift(type, imm3:imm2);
 *  if d == 13 || (d == 15 && S == '0') || n IN {13,15} || m IN {13,15} then UNPREDICTABLE;
 * 
 * Encoding A1 ARMv4*, ARMv5T*, ARMv6*, ARMv7
 *   AND{S}<c> <Rd>, <Rn>, <Rm>{, <shift>}
 * -------------------------------------------------------------------------------------------------
 * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7| 6  5| 4| 3  2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * | Condition | 0  0| 0| 0  0  0  0| S|   Rn      |    Rd     |   imm5       | type| 0|    Rm     |
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 *
 *  if Rd == '1111' && S == '1' then SEE SUBS PC, LR and related instructions;
 *  d = UInt(Rd); n = UInt(Rn); m = UInt(Rm); setflags = (S == '1');
 *  (shift_t, shift_n) = DecodeImmShift(type, imm5);
 *
 *  Assembler syntax
 *      AND{S}{<c>}{<q>} {<Rd>,} <Rn>, <Rm> {, <shift>}
 *  where:
 *  S           If S is present, the instruction updates the flags. Otherwise, the flags are not updated.
 *  <c>, <q>    See Standard assembler syntax fields on page A8-287.
 *  <Rd>        The destination register. If S is specified and <Rd> is the PC, see SUBS PC, LR (Thumb) on
 *              page B9-2010 or SUBS PC, LR and related instructions (ARM) on page B9-2012.
 *              In ARM instructions, if S is not specified and <Rd> is the PC, the instruction is a branch to the address
 *              calculated by the operation. This is an interworking branch, see Pseudocode details of operations
 *              on ARM core registers on page A2-47. ARM deprecates this use of the PC.
 *                  Note
 *              Before ARMv7, this was a simple branch.
 *  <Rn>        The first operand register. The PC can be used in ARM instructions. ARM deprecates this use of the PC.
 *  <Rm>        The register that is optionally shifted and used as the second operand. The PC can be used in ARM
 *              instructions. ARM deprecates this use of the PC.
 *  <shift>     The shift to apply to the value read from <Rm>. If present, encoding T1 is not permitted. If absent, no
 *              shift is applied and all encodings are permitted. Shifts applied to a register on page A8-291
 *              describes the shifts and how they are encoded.
 *
 *  In Thumb assembly:
 *  -   outside an IT block, if ANDS <Rd>, <Rn>, <Rd> has <Rd> and <Rn> both in the range R0-R7, it is assembled using
 *      encoding T1 as though ANDS <Rd>, <Rn> had been written
 *  -   inside an IT block, if AND<c> <Rd>, <Rn>, <Rd> has <Rd> and <Rn> both in the range R0-R7, it is assembled
 *      using encoding T1 as though AND<c> <Rd>, <Rn> had been written.
 *  To prevent either of these happening, use the .W qualifier.
 *  The pre-UAL syntax AND<c>S is equivalent to ANDS<c>.
 *
 *  Operation
 *  if ConditionPassed() then
 *      EncodingSpecificOperations();
 *      (shifted, carry) = Shift_C(R[m], shift_t, shift_n, APSR.C);
 *      result = R[n] AND shifted;
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
 * 3. AND (register-shifted register)
 * This instruction performs a bitwise AND of a register value and a register-shifted register value. It writes the result
 * to the destination register, and can optionally update the condition flags based on the result.
 *
 *
 *  Encoding A1 ARMv4*, ARMv5T*, ARMv6*, ARMv7
 *  AND{S}<c> <Rd>, <Rn>, <Rm>, <type> <Rs>
 * -------------------------------------------------------------------------------------------------
 * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7| 6  5| 4| 3  2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * | Condition | 0  0| 0| 0  0  0  0| S|   Rn      |    Rd     |   Rs      | 0| type| 1|    Rm     |
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 *
 *  d = UInt(Rd); n = UInt(Rn); m = UInt(Rm); s = UInt(Rs);
 *  setflags = (S == '1'); shift_t = DecodeRegShift(type);
 *  if d == 15 || n == 15 || m == 15 || s == 15 then UNPREDICTABLE;  
 *
 *  Assembler syntax
 *      AND{S}{<c>}{<q>} {<Rd>,} <Rn>, <Rm>, <type> <Rs>
 *  where:
 *  S           If S is present, the instruction updates the flags. Otherwise, the flags are not updated.
 *  <c>, <q>    See Standard assembler syntax fields on page A8-287.
 *  <Rd>        The destination register.
 *  <Rn>        The first operand register.
 *  <Rm>        The register that is shifted and used as the second operand.
 *  <type>      The type of shift to apply to the value read from <Rm>. It must be one of:
 *              ASR     Arithmetic shift right, encoded as  type = 0b10.
 *              LSL     Logical shift left, encoded as      type = 0b00.
 *              LSR     Logical shift right, encoded as     type = 0b01.
 *              ROR     Rotate right, encoded as            type = 0b11.
 *  <Rs>        The register whose bottom byte contains the amount to shift by.
 *  The pre-UAL syntax AND<c>S is equivalent to ANDS<c>. 
 *
 *  Operation
 *  if ConditionPassed() then
 *      EncodingSpecificOperations();
 *      shift_n = UInt(R[s]<7:0>);
 *      (shifted, carry) = Shift_C(R[m], shift_t, shift_n, APSR.C);
 *      result = R[n] AND shifted;
 *      R[d] = result;
 *      if setflags then
 *          APSR.N = result<31>;
 *          APSR.Z = IsZeroBit(result);
 *          APSR.C = carry;
 *          // APSR.V unchanged
 *
 *  Exceptions
 *      None. 
 *
 *
 *
 * @author Shao Yongqing
 * Date: 2017/9/22.
 */
public class AND extends DataProcessMisc{

    public AND(int[] raw) {
        super(raw);
    }

    @Override
    public boolean hasOpcode(int[] queue, int startIndex) {
        int raw = queue[startIndex];
        
        int cond = (raw>>28)&0xf;
        int prefix = (raw >> 26) & 0b11;
        int op1 = (raw >> 21) & 0b1111;
        
        //int bit4 = (raw >> 4)  & 0b1;
        //int bit7 = (raw >> 7)  & 0b1;
        
        if(cond!=0xf) {
            if(prefix == DATA_PROCESSING_PREFIX){
                if(op1==OpCode_AND){
                    if(checkBit4Bit7(raw))
                        return true;
                    //if(bit4==0)
                    //    return true;
                    //if(bit7==0)
                    //    return true;
                } 
            }
        }
        
        return false;
    }
    boolean checkBit4Bit7(int raw){
        int bit4 = (raw >> 4)  & 0b1;
        int bit7 = (raw >> 7)  & 0b1;
        if(bit4==0)
            return true;
        if(bit7==0)
            return true;
            
        return false;
    }
    
    
    /**
     * 2. AND (register)
     * Encoding A1 ARMv4*, ARMv5T*, ARMv6*, ARMv7
     *   AND{S}<c> <Rd>, <Rn>, <Rm>{, <shift>}
     * -------------------------------------------------------------------------------------------------
     * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7| 6  5| 4| 3  2  1  0|
     * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
     * | Condition | 0  0| 0| 0  0  0  0| S|   Rn      |    Rd     |   imm5       | type| 0|    Rm     |
     * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
     * 
     * 3. AND (register-shifted register)
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

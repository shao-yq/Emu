package cc.emulator.arch.arm.instruction;

import cc.emulator.arch.arm.ArmInstruction;

/**
 * @author Shao Yongqing
 * Date: 2017/8/18.
 */

/**
 * 4.5 Data Processing
 * The data processing instruction is only executed if the condition is true.
 *
 -------------------------------------------------------------------------------------------------------------------------------
 |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7  6  5  4| 3  2  1  0|Instruction Type
 |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
 | Condition | 0| 0| I|  OPCODE   | S|     Rn    |   Rd      |           OPERAND‐2              | Data processing/PSR Transfer
 |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------

 Condition: Condition field
 Rd: Destination register
 Rn: 1st operand register
 S: Set condition codes
    0 = do not alter condition codes
    1 = set condition codes
 OPCODE: Operation Code
     0000 = AND - Rd:= Op1 AND Op2
     0010 = SUB - Rd:= Op1 - Op2
     0011 = RSB - Rd:= Op2 - Op1
     0100 = ADD - Rd:= Op1 + Op2
     0101 = ADC - Rd:= Op1 + Op2 + C
     0110 = SBC - Rd:= Op1 - Op2 + C
     0111 = RSC - Rd:= Op2 - Op1 + C
     1000 = TST - set condition codes on Op1 AND Op2
     1001 = TEQ - set condition codes on Op1 EOR Op2
     1010 = CMP - set condition codes on Op1 - Op2
     1011 = CMN - set condition codes on Op1 + Op2
     1100 = ORR - Rd:= Op1 OR Op2
     1101 = MOV - Rd:= Op2
     1110 = BIC - Rd:= Op1 AND NOT Op2
     1111 = MVN - Rd:= NOT Op2
 I: Immediate Operand
     0 = operand 2 is a register
         OPERAND‐2:
            |11          4 |3     0|
            | Shift        | Rm    |
         shift applied to Rm|2nd operand register
    1 = operand 2 is an immediate value
            |11       8|7          0|
            |  Rotate  |  Imm       |
   shift applied to Imm| Unsigned 8 bit immediate value

 The instruction produces a result by performing a specified arithmetic or logical
 operation on one or two operands. The first operand is always a register (Rn).
 The second operand may be a shifted register (Rm) or a rotated 8 bit immediate value
 (Imm) according to the value of the I bit in the instruction. The condition codes in the
 CPSR may be preserved or updated as a result of this instruction, according to the
 value of the S bit in the instruction.
 Certain operations (TST, TEQ, CMP, CMN) do not write the result to Rd. They are used
 only to perform tests and to set the condition codes on the result and always have the
 S bit set.

 4.5.1 CPSR flags
 The data processing operations may be classified as logical or arithmetic. The logical
 operations (AND, EOR, TST, TEQ, ORR, MOV, BIC, MVN) perform the logical action
 on all corresponding bits of the operand or operands to produce the result. If the S bit
 is set (and Rd is not R15, see below) the V flag in the CPSR will be unaffected, the C
 flag will be set to the carry out from the barrel shifter (or preserved when the shift
 operation is LSL #0), the Z flag will be set if and only if the result is all zeros, and the N
 flag will be set to the logical value of bit 31 of the result.

 Table 4-3: ARM Data processing instructions
 Assembler
 Mnemonic OpCode Action
 AND 0000 operand1 AND operand2
 EOR 0001 operand1 EOR operand2
 SUB 0010 operand1 - operand2
 RSB 0011 operand2 - operand1
 ADD 0100 operand1 + operand2
 ADC 0101 operand1 + operand2 + carry
 SBC 0110 operand1 - operand2 + carry - 1
 RSC 0111 operand2 - operand1 + carry - 1
 TST 1000 as AND, but result is not written
 TEQ 1001 as EOR, but result is not written
 CMP 1010 as SUB, but result is not written
 CMN 1011 as ADD, but result is not written
 ORR 1100 operand1 OR operand2
 MOV 1101 operand2(operand1 is ignored)
 BIC 1110 operand1 AND NOT operand2(Bit clear)
 MVN 1111 NOT operand2(operand1 is ignored)

 The arithmetic operations (SUB, RSB, ADD, ADC, SBC, RSC, CMP, CMN) treat each
 operand as a 32 bit integer (either unsigned or 2’s complement signed, the two are
 equivalent). If the S bit is set (and Rd is not R15) the V flag in the CPSR will be set if
 an overflow occurs into bit 31 of the result; this may be ignored if the operands were
 considered unsigned, but warns of a possible error if the operands were 2’s
 complement signed. The C flag will be set to the carry out of bit 31 of the ALU, the Z
 flag will be set if and only if the result was zero, and the N flag will be set to the value
 of bit 31 of the result (indicating a negative result if the operands are considered to be
 2’s complement signed).

 4.5.2 Shifts
 When the second operand is specified to be a shifted register, the operation of the
 barrel shifter is controlled by the Shift field in the instruction. This field indicates the type
 of shift to be performed (logical left or right, arithmetic right or rotate right). The amount
 by which the register should be shifted may be contained in an immediate field in the
 instruction, or in the bottom byte of another register (other than R15).

 The encoding for the different shift types is shown in Figure 4-5: ARM shift operations.
 |11         7| 6  5 | 4 |
 |Shift amount| type |0  |
 Shift type
     00 = logical left
     01 = logical right
     10 = arithmetic right
     11 = rotate right
 Shift amount
    5 bit unsigned integer

 |11          8 | 7 | 6 5 | 4|
 |   Rs         |0  |type |1 |
 Rs: Shift register
    Shift amount specified in bottom byte of Rs
 Shift type
     00 = logical left
     01 = logical right
     10 = arithmetic right
     11 = rotate right

( Figure 4-5: ARM shift operations.)

 Instruction specified shift amount
 When the shift amount is specified in the instruction, it is contained in a 5 bit field which
 may take any value from 0—31. A logical shift left (LSL) takes the contents of Rm and
 moves each bit by the specified amount to a more significant position. The least
 significant bits of the result are filled with zeros, and the high bits of Rm which do not
 map into the result are discarded, except that the least significant discarded bit
 becomes the shifter carry output, which may be latched into the C bit of the CPSR when
 the ALU operation is in the logical class (see above).


 Immediate operand rotates
 The immediate operand rotate field is a 4 bit unsigned integer which specifies a shift
 operation on the 8 bit immediate value. This value is zero extended to 32 bits, and then
 subject to a rotate right by twice the value in the rotate field. This enables many
 common constants to be generated, for example all powers of 2.
 4.5.4 Writing to R15
 When Rd is a register other than R15, the condition code flags in the CPSR may be
 updated from the ALU flags as described above.
 When Rd is R15 and the S flag in the instruction is not set the result of the operation is
 placed in R15 and the CPSR is unaffected.
 When Rd is R15 and the S flag is set the result of the operation is placed in R15 and
 the SPSR corresponding to the current mode is moved to the CPSR. This allows state
 changes which atomically restore both PC and CPSR. This form of instruction should
 not be used in User mode.

 4.5.5 Using R15 as an operand
 If R15 (the PC) is used as an operand in a data processing instruction the register is
 used directly.
 The PC value will be the address of the instruction, plus 8 or 12 bytes due to instruction
 prefetching. If the shift amount is specified in the instruction, the PC will be 8 bytes
 ahead. If a register is used to specify the shift amount the PC will be 12 bytes ahead.
 4.5.6 TEQ, TST, CMP and CMN opcodes
 Note TEQ, TST, CMP and CMN do not write the result of their operation but do set flags in
 the CPSR. An assembler should always set the S flag for these instructions even if this
 is not specified in the mnemonic.
 The TEQP form of the TEQ instruction used in earlier ARM processors must not be
 used: the PSR transfer operations should be used instead.
 The action of TEQP in the ARM7TDMI-S is to move SPSR_<mode> to the CPSR if the
 processor is in a privileged mode and to do nothing if in User mode.


 4.5.7 Instruction cycle times
 Data Processing instructions vary in the number of incremental cycles taken as follows:
 Processing Type                                                Cycles
 ----------------------------------------------------------------------------
 Normal Data Processing                                         1S
 Data Processing with register specified shift                  1S + 1I
 Data Processing with PC written                                2S + 1N
 Data Processing with register specified shift and PC written   2S + 1N + 1I
 =============================================================================
        (Table 4-4: Incremental cycle times)


 4.5.8 Assembler syntax
     1 MOV,MVN (single operand instructions.)
        <opcode>{cond}{S} Rd,<Op2>
     2 CMP,CMN,TEQ,TST (instructions which do not produce a result.)
        <opcode>{cond} Rn,<Op2>
     3 AND,EOR,SUB,RSB,ADD,ADC,SBC,RSC,ORR,BIC
        <opcode>{cond}{S} Rd,Rn,<Op2>
     where:
         <Op2>          is Rm{,<shift>} or,<#expression>
         {cond}         is a two-character condition mnemonic. See Table 4-2: Condition code summary on page 4-5.
         {S}            set condition codes if S present (implied for CMP, CMN, TEQ,TST).
         Rd, Rn and Rm  are expressions evaluating to a register number.
         <#expression>  if this is used, the assembler will attempt to generate a shifted
                         immediate 8-bit field to match the expression. If this is impossible, it will give an error.
         <shift>        is <shiftname> <register> or <shiftname> #expression, or RRX (rotate right one bit with extend).
         <shiftname>s   are: ASL, LSL, LSR, ASR, ROR. (ASL is a synonym for LSL, they assemble to the same code.)

 4.5.9 Examples
     ADDEQR2,R4,R5          ; If the Z flag is set make R2:=R4+R5
     TEQSR4,#3              ; test R4 for equality with 3.
                            ; (The S is in fact redundant as the assembler inserts it automatically.)
     SUB R4,R5,R7,LSR R2    ; Logical right shift R7 by the number in the bottom byte of R2, subtract result
                            ; from R5, and put the answer into R4.
     MOV PC,R14             ; Return from subroutine.
     MOVSPC,R14             ; Return from exception and restore CPSR  from SPSR_mode.

 *
 */
public  class DataProcessMisc extends ArmInstruction {

    public DataProcessMisc(int[] queue) {
        super(queue);
    }

    /**
     *
     -------------------------------------------------------------------------------------------------------------------------------
     |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7  6  5  4| 3  2  1  0|Instruction Type
     |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
     | Condition | 0| 0| I|  OPCODE   | S|     Rn    |   Rd      |           OPERAND‐2              | Data processing/PSR Transfer
     |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------

     * @param raw
     * @param startIndex
     * @return
     */
    @Override
    public boolean hasOpcode(int[] raw, int startIndex) {
        int rawData = raw[startIndex];
        if(((rawData >> 26) & 0x3) ==0){
            int opcode = (rawData >> 21) & 0xF;
            switch (opcode){

            }
        }
        return false;
    }
}

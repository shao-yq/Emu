package cc.emulator.arch.arm.instruction;

/**
 *
 *  B
 *  Branch causes a branch to a target address.
 *
 * Encoding T1 ARMv4T, ARMv5T*, ARMv6*, ARMv7
 *  B<c> <label>        Not permitted in IT block.
 * -------------------------------------------------
 * |15 14 13 12|11|10| 9| 8| 7| 6| 5| 4| 3| 2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|
 * | 1  1  0  1| cond      |           imm8        |
 * |-----------|--+--+--+-----------+--|-----------|
 *
 *  if cond == ‘1110’ then SEE UDF;
 *  if cond == ‘1111’ then SEE SVC;
 *  imm32 = SignExtend(imm8:’0’, 32);
 *  if InITBlock() then UNPREDICTABLE;
 *
 *  Encoding T2 ARMv4T, ARMv5T*, ARMv6*, ARMv7
 *  B<c> <label> Outside or last in IT block
 * -------------------------------------------------
 * |15 14 13 12|11|10| 9| 8| 7| 6| 5| 4| 3| 2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|
 * | 1  1  1  0|           imm11                   |
 * |-----------|--+--+--+-----------+--|-----------|
 *
 *  imm32 = SignExtend(imm11:’0’, 32);
 *  if InITBlock() && !LastInITBlock() then UNPREDICTABLE;
 *
 *  Encoding T3 ARMv6T2, ARMv7
 *  B<c>.W <label>          Not permitted in IT block.
 * -------------------------------------------------------------------------------------------------
 * |15 14 13 12|11|10| 9| 8  7| 6| 5| 4| 3  2  1  0|15 14|13|12|11|10  9  8| 7| 6| 5| 4| 3  2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * | 1  1  1  1  0| S|   cond    |      imm6       | 1  0|J1| 0|J2|          imm11                 |
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 *
 *  if cond<3:1> == ‘111’ then SEE “Related encodings”;
 *  imm32 = SignExtend(S:J2:J1:imm6:imm11:’0’, 32);
 *  if InITBlock() then UNPREDICTABLE;
 *
 *  Encoding T4 ARMv6T2, ARMv7
 *  B<c>.W <label>          Outside or last in IT block
 * -------------------------------------------------------------------------------------------------
 * |15 14 13 12|11|10| 9| 8  7| 6| 5| 4| 3  2  1  0|15 14|13|12|11|10  9  8| 7| 6| 5| 4| 3  2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * | 1  1  1  1  0| S|       imm10                 | 1  0|J1| 1|J2|          imm11                 |
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 *
 *  I1 = NOT(J1 EOR S); I2 = NOT(J2 EOR S); imm32 = SignExtend(S:I1:I2:imm10:imm11:’0’, 32);
 *  if InITBlock() && !LastInITBlock() then UNPREDICTABLE;
 *
 *  Encoding A1 ARMv4*, ARMv5T*, ARMv6*, ARMv7
 *  B<c> <label>
 * -------------------------------------------------------------------------------------------------
 * |31 30 29 28|27|26|25|24|23 22 21 20 19 18 17 16|15 14 13 12 11 10  9  8| 7  6  5  4  3  2  1  0|
 * |-----------|--+--+--+--+-----------------------|-----------------------------------------------|
 * | Condition | 1  0  1  0|                                       imm24                           |
 * |-----------|--+--+--+--+-----------------------|-----------------------------------------------|
 *
 *  imm32 = SignExtend(imm24:’00’, 32);
 *
 *  Assembler syntax
 *      B{<c>}{<q>} <label>
 *  where:
 *  <c>, <q>            See Standard assembler syntax fields on page A8-287.
 *                      Note
 *                      Encodings T1 and T3 are conditional in their own right, and do not require an IT instruction to make
 *                      them conditional.
 *                      For encodings T1 and T3, <c> must not be AL or omitted. The 4-bit encoding of the condition is
 *                      placed in the instruction and not in a preceding IT instruction, and the instruction must not be in an
 *                      IT block. As a result, encodings T1 and T2 are never both available to the assembler, nor are
 *                      encodings T3 and T4.
 *  <label>             The label of the instruction that is to be branched to. The assembler calculates the required value of
 *                      the offset from the PC value of the B instruction to this label, then selects an encoding that sets imm32
 *                      to that offset.
 *                      Permitted offsets are:
 *                      Encoding T1 Even numbers in the range –256 to 254
 *                      Encoding T2 Even numbers in the range –2048 to 2046
 *                      Encoding T3 Even numbers in the range –1048576 to 1048574
 *                      Encoding T4 Even numbers in the range –16777216 to 16777214
 *                      Encoding A1 Multiples of 4 in the range –33554432 to 33554428.
 *  Operation
 *  if ConditionPassed() then
 *      EncodingSpecificOperations();
 *      BranchWritePC(PC + imm32);
 *
 *  Exceptions
 *      None.
 *
 * @author Shao Yongqing
 * Date: 2017/9/23.
 */
public class B extends Branch{
    public B(){
    }

    public B(int[] queue) {
        super(queue);
    }

    @Override
    public boolean hasOpcode(int[] queue, int startIndex) {
        int raw = queue[startIndex];
        int op = (raw>>24)&0xf;
        if(op == 0b1010)
            return true;

        return false;
    }

}

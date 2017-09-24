package cc.emulator.arch.arm.instruction;

/**
 * BL, BLX (immediate)
 * 
 *  Branch with Link calls a subroutine at a PC-relative address.
 *  Branch with Link and Exchange Instruction Sets (immediate) calls a subroutine at a PC-relative address, and
 *  changes instruction set from ARM to Thumb, or from Thumb to ARM. 
 *  
 *  Encoding T1     ARMv4T, ARMv5T*, ARMv6*, ARMv7 if J1 == J2 == 1
 *                  ARMv6T2, ARMv7 otherwise
 *      BL<c> <label> Outside or last in IT block
 * ------------------------------------------------------------------------------------------------- 
 * |15 14 13 12|11|10| 9| 8| 7| 6| 5| 4| 3| 2  1  0|15 14|13|12|11|10  9  8| 7| 6| 5| 4| 3  2  1  0| 
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------| 
 * | 1  1  1  1  0| S|     imm10                   | 1  1|J1| 1|J2|        imm10                   | 
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------| 
 *  
 *  I1 = NOT(J1 EOR S); I2 = NOT(J2 EOR S); imm32 = SignExtend(S:I1:I2:imm10:imm11:'0', 32);
 *  targetInstrSet = CurrentInstrSet();
 *  if InITBlock() && !LastInITBlock() then UNPREDICTABLE;
 *  
 *  Encoding T2     ARMv5T*, ARMv6*, ARMv7 if J1 == J2 == 1
 *                  ARMv6T2, ARMv7 otherwise
 *      BLX<c> <label> Outside or last in IT block
 * ------------------------------------------------------------------------------------------------- 
 * |15 14 13 12|11|10| 9| 8| 7| 6| 5| 4| 3| 2  1  0|15 14|13|12|11|10  9  8| 7| 6| 5| 4| 3  2  1  0| 
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------| 
 * | 1  1  1  1  0| S|     imm10H                  | 1  1|J1| 1|J2|        imm10L                  | 
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------| 
 *  
 *  if CurrentInstrSet() == InstrSet_ThumbEE || H == '1' then UNDEFINED;
 *  I1 = NOT(J1 EOR S); I2 = NOT(J2 EOR S); imm32 = SignExtend(S:I1:I2:imm10H:imm10L:'00', 32);
 *  targetInstrSet = InstrSet_ARM;
 *  if InITBlock() && !LastInITBlock() then UNPREDICTABLE;
 *  
 *  
 *  Encoding A1 ARMv4*, ARMv5T*, ARMv6*, ARMv7
 *      BL<c> <label>
 * -------------------------------------------------------------------------------------------------
 * |31 30 29 28|27|26|25|24|23 22 21 20 19 18 17 16|15 14 13 12 11 10  9  8| 7  6  5  4  3  2  1  0|
 * |-----------|--+--+--+--+-----------------------|-----------------------------------------------|
 * | Condition | 1  0  1  1|                                       imm24                           |
 * |-----------|--+--+--+--+-----------------------|-----------------------------------------------|
 *  
 *  imm32 = SignExtend(imm24:'00', 32); targetInstrSet = InstrSet_ARM;
 *  
 *  Encoding A2 ARMv5T*, ARMv6*, ARMv7
 *      BLX <label>
 * -------------------------------------------------------------------------------------------------
 * |31 30 29 28|27|26|25|24|23 22 21 20 19 18 17 16|15 14 13 12 11 10  9  8| 7  6  5  4  3  2  1  0|
 * |-----------|--+--+--+--+-----------------------|-----------------------------------------------|
 * | 1  1  1  1| 1  0  1| H|                                       imm24                           |
 * |-----------|--+--+--+--+-----------------------|-----------------------------------------------|
 *  
 *  imm32 = SignExtend(imm24:H:'0', 32); targetInstrSet = InstrSet_Thumb;
 *  
 *  Assembler syntax
 *      BL{X}{<c>}{<q>} <label>
 *  where:
 *  <c>, <q>        See Standard assembler syntax fields on page A8-287. An ARM BLX (immediate) instruction must
 *                  be unconditional.
 *  X               If present, specifies a change of instruction set (from ARM to Thumb or from Thumb to ARM). If
 *                  X is omitted, the processor remains in the same state. For ThumbEE instructions, specifying X is
 *                  not permitted.
 *  <label>         The label of the instruction that is to be branched to.
 *                  BL uses encoding T1 or A1. The assembler calculates the required value of the offset from the PC
 *                  value of the BL instruction to this label, then selects an encoding with imm32 set to that offset.
 *                  BLX uses encoding T2 or A2. The assembler calculates the required value of the offset from the
 *                  Align(PC, 4) value of the BLX instruction to this label, then selects an encoding with imm32 set to that
 *                  offset.
 *                  Permitted offsets are:
 *                  Encoding T1 Even numbers in the range -6777216 to 16777214.
 *                  Encoding T2 Multiples of 4 in the range -16777216 to 16777212.
 *                  Encoding A1 Multiples of 4 in the range -33554432 to 33554428.
 *                  Encoding A2 Even numbers in the range -33554432 to 33554430.
 *
 *  Operation
 *  if ConditionPassed() then
 *      EncodingSpecificOperations();
 *      if CurrentInstrSet() == InstrSet_ARM then
 *          LR = PC - 4;
 *      else
 *          LR = PC<31:1> : '1';
 *      if targetInstrSet == InstrSet_ARM then
 *          targetAddress = Align(PC,4) + imm32;
 *      else
 *          targetAddress = PC + imm32;
 *      SelectInstrSet(targetInstrSet);
 *      BranchWritePC(targetAddress);
 *
 *  Exceptions
 *      None.
 *  
 *  
 * @author Shao Yongqing
 * Date: 2017/9/23.
 */
public class BL extends Branch{
    public BL(int[] queue) {
        super(queue);
    }

    @Override
    public boolean hasOpcode(int[] queue, int startIndex) {
        int raw = queue[startIndex];
        int op = (raw>>24)&0xf;
        if(op == 0b1011)
            return true;

        return false;
    }
}

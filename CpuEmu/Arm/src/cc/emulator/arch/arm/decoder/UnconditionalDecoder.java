package cc.emulator.arch.arm.decoder;

import cc.emulator.core.cpu.Instruction;

/**
 * Uncoditional Instruction decoder
 *
 * Instructin format with cond = 0b1111
 *
 * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7  6  5 | 4| 3  2  1  0|Instruction Type
 * |-----------+-----------------------+-----------+---------------------------------+--+-----------------------------
 * | cond      | op1                   |      Rn   |                                 |op|           | Instruction classes(指令分类)
 * |-----------+--------+------------------------------------------------------------+--+-----------+---------
 * | 1111      | op1                   |      Rn   |                                 |op|  如果cond字段为0b1111，只能无条件地执行指令
 * -----------------------------------------------------------------------------------------------------------
 * 
 * 
 *     op1        op      Rn      Instruction                     See                                             Variant
 *     0xxxxxxx   -       -       -                               Memory hints, Advanced SIMD instructions, and
 *     miscellaneous instructions on page A5-217
 *     100xx1x0   -       -       Store Return State              SRS (ARM) on page B9-2006                       v6
 *     100xx0x1   -       -       Return From Exception           RFE on page B9-2000                             v6
 *     101xxxxx   -       -       Branch with Link and Exchange   BL, BLX (immediate) on page A8-348              v5
 *     110xxxx0   -       -       Store Coprocessor               STC, STC2 on page A8-662                        v5
 * not 11000x00
 * 
 *     110xxxx1   -      not1111  Load Coprocessor (immediate)    LDC, LDC2 (immediate) on page A8-392            v5
 * not 11000x01
 *                        1111    Load Coprocessor (literal)      LDC, LDC2 (literal) on page A8-394              v5
 * 
 *     11000100   -       -       Move to Coprocessor from two    ARM MCRR, MCRR2 on page A8-478                  v6
 *     core registers
 *     11000101   -       -       Move to two ARM core registers  MRRC, MRRC2 on page A8-494                      v6
 *     from Coprocessor
 * 
 *     1110xxxx   0       -       Coprocessor data operations     CDP, CDP2 on page A8-358                        v5
 *     1110xxx0   1       -       Move to Coprocessor from ARM    MCR, MCR2 on page A8-476                        v5
 *     core register
 * 
 *     1110xxx1   1       -       Move to ARM core register from  MRC, MRC2 on page A8-492                        v5
 *     Coprocessor
 * 
 * 
 *
 * @author Shao Yongqing
 * Date: 2017/9/22.
 */
public class UnconditionalDecoder  extends AbstractArmDecoder {
    /**
     *
     * @param rawInstruction
     * @return
     */
    @Override
    public  boolean hasInstruction(int rawInstruction) {
        return isUncoditional(rawInstruction);
    }

    @Override
    public boolean hasOpcode(int rawInstruction) {
        return isUncoditional(rawInstruction);
    }

    /**
     * Unconditional instructions
     *
     * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7  6  5 | 4| 3  2  1  0|Instruction Type
     * |-----------+-----------------------+-----------+---------------------------------+--+-----------------------------
     * | cond      | op1                   |      Rn   |                                 |op|           | Instruction classes(指令分类)
     * |-----------+--------+------------------------------------------------------------+--+-----------+---------
     * | 1111      | op1                   |      Rn   |                                 |op|  如果cond字段为0b1111，只能无条件地执行指令
     * ------------------------------------------------------------------------------------------------------------
     * 
     *  op1        op      Rn      Instruction                     See                                             Variant
     *  0xxxxxxx   -       -       -                               Memory hints, Advanced SIMD instructions, and
     *  miscellaneous instructions on page A5-217
     *  100xx1x0   -       -       Store Return State              SRS (ARM) on page B9-2006                       v6
     *  100xx0x1   -       -       Return From Exception           RFE on page B9-2000                             v6
     *  101xxxxx   -       -       Branch with Link and Exchange   BL, BLX (immediate) on page A8-348              v5
     *  110xxxx0   -       -       Store Coprocessor               STC, STC2 on page A8-662                        v5
     *  not 11000x00
     *  
     *  110xxxx1   -      not1111  Load Coprocessor (immediate)    LDC, LDC2 (immediate) on page A8-392            v5
     *  not 11000x01
     *                     1111    Load Coprocessor (literal)      LDC, LDC2 (literal) on page A8-394              v5
     *  
     *  11000100   -       -       Move to Coprocessor from two    ARM MCRR, MCRR2 on page A8-478                  v6
     *  core registers
     *  11000101   -       -       Move to two ARM core registers  MRRC, MRRC2 on page A8-494                      v6
     *  from Coprocessor
     *  
     *  1110xxxx   0       -       Coprocessor data operations     CDP, CDP2 on page A8-358                        v5
     *  1110xxx0   1       -       Move to Coprocessor from ARM    MCR, MCR2 on page A8-476                        v5
     *  core register
     *  
     *  1110xxx1   1       -       Move to ARM core register from  MRC, MRC2 on page A8-492                        v5
     *  Coprocessor
     * 
     *
     * @param rawInstruction
     * @return
     */
    @Override
    public  Instruction decode(int rawInstruction){
        Instruction instruction = null;
        int op1 = (rawInstruction>>20) & 0xff;
        int rn = (rawInstruction>>16)&0xf;
        int op = (rawInstruction>>4)&0x1;
        //
        if((op1 &0x80) ==0) {
            instruction = decodeMemoryHintSimdInstruction(rawInstruction);
        } else {
            int mask = 0b11100101;      // for  100xx1x0, 100xx0x1
            int code = op1 & mask;
            switch(code) {
                case 0b10000100:
                    //   SRS (ARM), Store Return State
                    break;
                case 0b10000001:
                    // RFE, Return From Exception
                    break;
                default:
                    // Others

                    break;
            }
            //
            mask = 0b11100000;          //  for 101xxxxx
            if((op1 & mask) == 0b10100000){
                //  BL, BLX (immediate) ?
            } else {

            }
        }

        return instruction;
    }


    /**
     * Unconditional instructions - Memory hints, Advanced SIMD instructions, and miscellaneous instructions
     *
     |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7  6  5 | 4| 3  2  1  0|Instruction Type
     |-----------+-----------------------+-----------+---------------------------------+--+-----------------------------
     | cond      | op1                   |      Rn   |                                 |op|           | Instruction classes(指令分类)
     |-----------+--------+------------------------------------------------------------+--+-----------+---------
     | 1111     | 0|     op1           |      Rn   |                                 |op|  如果cond字段为0b1111，只能无条件地执行指令
     -------------------------------------------------------------------------------------------------------

     op1 op2 Rn Instruction See Variant
     0010000 xx0x xxx0 Change Processor State CPS (ARM) on page B9-1980 v6
     0010000 0000 xxx1 Set Endianness SETEND on page A8-604 v6
     0010010 0111 - UNPREDICTABLE - v5T
     01xxxxx - - See Advanced SIMD data-processing instructions on page A7-261 v7
     100xxx0 - - See Advanced SIMD element or structure load/store instructions on page A7-275 v7
     100x001 - - Unallocated memory hint (treat as NOP) MP Exta
     100x101 - - Preload Instruction PLI (immediate, literal) on page A8-530 v7
     100xx11 - - UNPREDICTABLE - -
     101x001 - not 1111 Preload Data with intent to Write PLD, PLDW (immediate) on page A8-524 MP Exta
     1111 UNPREDICTABLE - -
     101x101 - not 1111 Preload Data PLD, PLDW (immediate) on page A8-524 v5TE
     1111 Preload Data PLD (literal) on page A8-526 v5TE
     1010011 - - UNPREDICTABLE - -
     1010111 0000 - UNPREDICTABLE - -
     0001 - Clear-Exclusive CLREX on page A8-360 v6K
     001x - UNPREDICTABLE - -
     0100 - Data Synchronization Barrier DSB on page A8-380 v6T2
     0101 - Data Memory Barrier DMB on page A8-378 v7
     0110 - Instruction Synchronization Barrier ISB on page A8-389 v6T2
     0111 - UNPREDICTABLE - -
     1xxx - UNPREDICTABLE - -
     1011x11 - - UNPREDICTABLE -
     110x001 xxx0 - Unallocated memory hint (treat as NOP) MP Exta
     110x101 xxx0 - Preload Instruction PLI (register) on page A8-532 v7
     111x001 xxx0 - Preload Data with intent to Write PLD, PLDW (register) on page A8-528 MP Exta
     111x101 xxx0 - Preload Data PLD, PLDW (register) on page A8-528 v5TE
     11xxx11 xxx0 - UNPREDICTABLE - -
     1111111 1111 Permanently UNDEFINEDb - v5
     */
    Instruction decodeMemoryHintSimdInstruction(int rawInstruction){
        Instruction instruction = null;

        return instruction;
    }

}

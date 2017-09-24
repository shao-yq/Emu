package cc.emulator.arch.arm.decoder;

import cc.emulator.arch.arm.instruction.*;
import cc.emulator.core.cpu.Instruction;

/**
 *  Data Processing Misc instruction decoder
 *
 *  Instruction format :
 * -------------------------------------------------------------------------------------------------------------------------------
 * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7  6  5  4| 3  2  1  0|Instruction Type
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
 * | Condition | 0| 0|I |  OPCODE   | S|     Rn    |   Rd      |           OPERAND‐2              | Data processing/PSR Transfer
 *
 * A5.2 Data-processing and miscellaneous instructions
 * The encoding of ARM data-processing instructions, and some miscellaneous, instructions is:
 * -------------------------------------------------------------------------------------------------------------------------------
 * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7  6  5  4| 3  2  1  0|Instruction Type
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
 * | Condition | 0| 0|op|  op1         |                                   |    op2    |           | Data processing/PSR Transfer
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
 *
 *      Table A5-2 Data-processing and miscellaneous instructions
 * op   op1         op2     Instruction or instruction class                            Variant
 * ---------------------------------------------------------------------------------------------
 * 0    not 10xx0   xxx0    Data-processing (register) on page A5-197                   -
 *                  0xx1    Data-processing (register-shifted register) on page A5-198  -
 *      10xx0       0xxx    Miscellaneous instructions on page A5-207                   -
 *                  1xx0    Halfword multiply and multiply accumulate on page A5-203    -
 *      0xxxx       1001    Multiply and multiply accumulate on page A5-202             -
 *      1xxxx       1001    Synchronization primitives on page A5-205                   -
 *      not 0xx1x   1011    Extra load/store instructions on page A5-203                -
 *                  11x1    Extra load/store instructions on page A5-203                -
 *      0xx10       11x1    Extra load/store instructions on page A5-203                -
 *      0xx1x       1011    Extra load/store instructions, unprivileged on page A5-204  -
 *      0xx11       11x1    Extra load/store instructions, unprivileged on page A5-204  -
 * 1    not 10xx0   -       Data-processing (immediate) on page A5-199                  -
 *      10000       -       16-bit immediate load, MOV (immediate) on page A8-484       v6T2
 *      10100       -       High halfword 16-bit immediate load, MOVT on page A8-491    v6T2
 *      10x10       -       MSR (immediate), and hints on page A5-206
 * ============================================================================================
 *
 * A5.2.1 Data-processing (register)
 * The encoding of ARM data-processing (register) instructions is:
 * -------------------------------------------------------------------------------------------------------------------------------
 * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7| 6  5| 4| 3  2  1  0|Instruction Type
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
 * | Condition | 0| 0|0 |  op1         |                       |     imm5     |  op2| 0|           | Data processing/PSR Transfer
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
 * 
 * Table A5-3 Data-processing (register) instructions
 *  op      op2 imm5    Instruction                                 See
 * -------------------------------------------------------------------------------------------
 *  0000x   -   -       Bitwise AND                         AND (register) on page A8-326
 *  0001x   -   -       Bitwise Exclusive OR                EOR (register) on page A8-384
 *  0010x   -   -       Subtract                            SUB (register) on page A8-712
 *  0011x   -   -       Reverse Subtract                    RSB (register) on page A8-576
 *  0100x   -   -       Add                                 ADD (register, ARM) on page A8-312
 *  0101x   -   -       Add with Carry                      ADC (register) on page A8-302
 *  0110x   -   -       Subtract with Carry                 SBC (register) on page A8-594
 *  0111x   -   -       Reverse Subtract with Carry         RSC (register) on page A8-582
 *  10xx0   -   -       See Data-processing and miscellaneous instructions on page A5-196
 *  10001   -   -       Test                                TST (register) on page A8-746
 *  10011   -   -       Test Equivalence                    TEQ (register) on page A8-740
 *  10101   -   -       Compare                             CMP (register) on page A8-372
 *  10111   -   -       Compare Negative                    CMN (register) on page A8-366
 *  1100x   -   -       Bitwise OR                          ORR (register) on page A8-518
 *  1101x   00 00000    Move                                MOV (register, ARM) on page A8-488
 *         not 00000    Logical Shift Left                  LSL (immediate) on page A8-468
 *          01  -       Logical Shift Right                 LSR (immediate) on page A8-472
 *          10  -       Arithmetic Shift Right              ASR (immediate) on page A8-330
 *          11 00000    Rotate Right with Extend            RRX on page A8-572
 *         not 00000    Rotate Right                        ROR (immediate) on page A8-568
 *  1110x   -   -       Bitwise Bit Clear                   BIC (register) on page A8-342
 *  1111x   -   -       Bitwise NOT                         MVN (register) on page A8-506
 * ===========================================================================================
 *
 *
 * A5.2.2 Data-processing (register-shifted register)
 *   The encoding of ARM data-processing (register-shifted register) instructions is:
 * -------------------------------------------------------------------------------------------------------------------------------
 * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7| 6  5| 4| 3  2  1  0|Instruction Type
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
 * | Condition | 0| 0|0 |  op1         |                                   | 0|  op2| 1|           | Data processing/PSR Transfer
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
 *  Table A5-4 Data-processing (register-shifted register) instructions
 *  op1     op2 Instruction                         See
 *  ---------------------------------------------------------------------------------------
 *  0000x   -   Bitwise AND                 AND (register-shifted register) on page A8-328
 *  0001x   -   Bitwise Exclusive OR        EOR (register-shifted register) on page A8-386
 *  0010x   -   Subtract                    SUB (register-shifted register) on page A8-714
 *  0011x   -   Reverse Subtract            RSB (register-shifted register) on page A8-578
 *  0100x   -   Add                         ADD (register-shifted register) on page A8-314
 *  0101x   -   Add with Carry              ADC (register-shifted register) on page A8-304
 *  0110x   -   Subtract with Carry         SBC (register-shifted register) on page A8-596
 *  0111x   -   Reverse Subtract with Carry RSC (register-shifted register) on page A8-584
 *  10xx0   -   See Data-processing and miscellaneous instructions on page A5-196
 *  10001   -   Test                        TST (register-shifted register) on page A8-748
 *  10011   -   Test Equivalence            TEQ (register-shifted register) on page A8-742
 *  10101   -   Compare                     CMP (register-shifted register) on page A8-374
 *  10111   -   Compare Negative            CMN (register-shifted register) on page A8-368
 *  1100x   -   Bitwise OR                  ORR (register-shifted register) on page A8-520
 *  1101x   00  Logical Shift Left          LSL (register) on page A8-470
 *          01  Logical Shift Right         LSR (register) on page A8-474
 *          10  Arithmetic Shift Right      ASR (register) on page A8-332
 *          11  Rotate Right                ROR (register) on page A8-570
 *  1110x   -   Bitwise Bit Clear           BIC (register-shifted register) on page A8-344
 *  1111x   -   Bitwise NOT                 MVN (register-shifted register) on page A8-508
 * ======================================================================================== 
 *
 *  A5.2.3 Data-processing (immediate)
 *  The encoding of ARM data-processing (immediate) instructions is:
 * -------------------------------------------------------------------------------------------------------------------------------
 * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7| 6  5| 4| 3  2  1  0|Instruction Type
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
 * | Condition | 0| 0| 1|  op          |    Rn     |                                               | Data processing/PSR Transfer
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
 *
 *
 *  Table A5-5 Data-processing (immediate) instructions
 *  op      Rn      Instruction                     See
 * ----------------------------------------------------------------------------------
 *  0000x   -       Bitwise AND                 AND (immediate) on page A8-324
 *  0001x   -       Bitwise Exclusive OR        EOR (immediate) on page A8-382
 *  0010x  not 1111 Subtract                    SUB (immediate, ARM) on page A8-710
 *          1111    Form PC-relative address    ADR on page A8-322
 *  0011x   -       Reverse Subtract            RSB (immediate) on page A8-574
 *  0100x  not 1111 Add                         ADD (immediate, ARM) on page A8-308
 *          1111    Form PC-relative address    ADR on page A8-322
 *  0101x   -       Add with Carry              ADC (immediate) on page A8-300
 *  0110x   -       Subtract with Carry         SBC (immediate) on page A8-592
 *  0111x   -       Reverse Subtract with Carry RSC (immediate) on page A8-580
 *  10xx0   -       See Data-processing and miscellaneous instructions on page A5-196
 *  10001   -       Test                        TST (immediate) on page A8-744
 *  10011   -       Test Equivalence            TEQ (immediate) on page A8-738
 *  10101   -       Compare                     CMP (immediate) on page A8-370
 *  10111   -       Compare Negative            CMN (immediate) on page A8-364
 *  1100x   -       Bitwise OR                  ORR (immediate) on page A8-516
 *  1101x   -       Move                        MOV (immediate) on page A8-484
 *  1110x   -       Bitwise Bit Clear           BIC (immediate) on page A8-340
 *  1111x   -       Bitwise NOT                 MVN (immediate) on page A8-504
 * ==================================================================================
 *
 * A5.2.4 Modified immediate constants in ARM instructions
 *  Table A5-6 shows the range of modified immediate constants available in ARM data-processing instructions, and
 *  their encoding in the a, b, c, d, e, f, g, and h bits and the rotation field in the instruction.
 *
 *   Table A5-6 Encoding of modified immediates in ARM processing instructions
 *  rotation <const> a
 *  0000    00000000 00000000 00000000 abcdefgh
 *  0001    gh000000 00000000 00000000 00abcdef
 *  0010    efgh0000 00000000 00000000 0000abcd
 *  0011    cdefgh00 00000000 00000000 000000ab
 *  0100    abcdefgh 00000000 00000000 00000000
 *  .       .
 *  .       . 8-bit values shifted to other even-numbered positions
 *  .       .

 *  1001    00000000 00abcdef gh000000 00000000
 *  .       .
 *  .       . 8-bit values shifted to other even-numbered positions
 *  .       .
 *  
 *  1110    00000000 00000000 0000abcd efgh0000
 *  1111    00000000 00000000 000000ab cdefgh00
 *
 *  a. This table shows the immediate constant value in binary form, to relate abcdefgh to the encoding diagram.
 *  In assembly syntax, the immediate value is specified in the usual way (a decimal number by default).
 * 
 *  @author Shao Yongqing
 *  Date: 2017/9/22.
 */
public class DataProcessingDecoder extends AbstractArmDecoder {

    /**
     *
     * @param rawInstruction
     * @return
     */
    @Override
    public  boolean hasInstruction(int rawInstruction) {
        if(isUncoditional(rawInstruction))
            return false;

        return  hasOpcode(rawInstruction);
    }
    
    /**
     *
     *  Data Processing Misc instruction decoder
     *
     *  Instruction format :
     * -------------------------------------------------------------------------------------------------------------------------------
     * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7  6  5  4| 3  2  1  0|Instruction Type
     * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
     * | Condition | 0| 0|I |  OPCODE   | S|     Rn    |   Rd      |           OPERAND‐2              | Data processing/PSR Transfer
     *
     */    
    @Override
    public boolean hasOpcode(int rawInstruction) {
        return ((rawInstruction >> 26) & 0x3) == 0;
    }

    /**
     *
     * -------------------------------------------------------------------------------------------------------------------------------
     * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7  6  5  4| 3  2  1  0|Instruction Type
     * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
     * | Condition | 0| 0|op|  op1         |                                   |    op2    |           | Data processing/PSR Transfer
     * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
     *
     *      Table A5-2 Data-processing and miscellaneous instructions
     * op   op1         op2     Instruction or instruction class                            Variant
     * ---------------------------------------------------------------------------------------------
     * 0    not 10xx0   xxx0    Data-processing (register) on page A5-197                   -
     *                  0xx1    Data-processing (register-shifted register) on page A5-198  -
     *      10xx0       0xxx    Miscellaneous instructions on page A5-207                   -
     *                  1xx0    Halfword multiply and multiply accumulate on page A5-203    -
     *      0xxxx       1001    Multiply and multiply accumulate on page A5-202             -
     *      1xxxx       1001    Synchronization primitives on page A5-205                   -
     *      not 0xx1x   1011    Extra load/store instructions on page A5-203                -
     *                  11x1    Extra load/store instructions on page A5-203                -
     *      0xx10       11x1    Extra load/store instructions on page A5-203                -
     *      0xx1x       1011    Extra load/store instructions, unprivileged on page A5-204  -
     *      0xx11       11x1    Extra load/store instructions, unprivileged on page A5-204  -
     * ---------------------------------------------------------------------------------------------
     * 1    not 10xx0   -       Data-processing (immediate) on page A5-199                  -
     *      10000       -       16-bit immediate load, MOV (immediate) on page A8-484       v6T2
     *      10100       -       High halfword 16-bit immediate load, MOVT on page A8-491    v6T2
     *      10x10       -       MSR (immediate), and hints on page A5-206
     * =============================================================================================
     *
     */
    @Override
    public Instruction decode(int rawData[]){
        int rawInstruction = rawData[0];
        Instruction instruction = null;
        // Data-processing and miscellaneous instructions
        int op = (rawInstruction >> 25) & 0x1;
        int op1 = (rawInstruction >> 20) & 0x1f;
        int op2 = (rawInstruction >> 4) & 0xf;
        if(op == 0) {
            if (op2 == 0b1001) {
                if((op1&0b10000) !=0){
                    // op   op1         op2     Instruction or instruction class 
                    // 0    1xxxx       1001    Synchronization primitives on page A5-205
                    
                } else {
                    // MUL
                    // op   op1         op2     Instruction or instruction class 
                    // 0    0xxxx       1001    Multiply and multiply accumulate on page A5-202
                    instruction = decodeMultiplyInstruction(rawData);
                }
            } else if(op2 == 0b1011){
                if((op1&0b10010) == 0b00010) {
                    // op    op1       op2     Instruction or instruction class  
                    // 0    0xx1x      1011    Extra load/store instructions, unprivileged on page A5-204
                    
                } else {
                    // op    op1       op2     Instruction or instruction class  
                    // 0 not 0xx1x     1011    Extra load/store instructions on page A5-203
                    
                }

            } else if((op2&0b1101) == 0b1101) {
                switch(op1&0b10011){
                    case 0b00010:
                        // op    op1       op2     Instruction or instruction class
                        // 0     0xx10     11x1    Extra load/store instructions on page A5-203
                        
                        break;
                    case 0b00011:
                        // op    op1       op2     Instruction or instruction class
                        // 0     0xx11     11x1    Extra load/store instructions, unprivileged on page A5-204
                        
                        break;
                    default:
                        // op     op1       op2     Instruction or instruction class
                        // 0 not 0xx1x      11x1    Extra load/store instructions on page A5-203
                        
                        break;
                }
            } else {
                if((op1&0b11001) == 0b10000){       // 10xx0
                    if((op2&1001)== 0b1000){        //  1xx0
                        // op     op1       op2     Instruction or instruction class
                        //        10xx0     1xx0    Halfword multiply and multiply accumulate on page A5-203
                    } else if((op2&1000)== 0b0000){ // 0xxx
                        // op     op1       op2     Instruction or instruction class
                        //        0xxx    Miscellaneous instructions on page A5-207
                        
                    } else {
                        // op     op1       op2     Instruction or instruction class
                        // 0    not 10xx0   xxx0    Data-processing (register) on page A5-197
                    }
                } else {    // not 10xx0
                    if((op2&0b0001)== 0b0000){      //  xxx0
                        // op     op1       op2     Instruction or instruction class
                        // 0    not 10xx0   xxx0    Data-processing (register) on page A5-197
                    } else if((op2&0b1001)==0b0001) {   // 0xx1
                        // op     op1       op2     Instruction or instruction class
                        // 0  not 10xx0     0xx1    Data-processing (register-shifted register) on page A5-198
                        instruction = decodeDataProcessingRegister(rawData);
                    }
                }
            }
        } else { // op == 1
            switch(op1) {
            case 0b10000:
                //  op     op1       op2     Instruction or instruction class
                //   1   10000       -       16-bit immediate load, MOV (immediate) on page A8-484
                break;
                
            case 0b10100:
                //  op     op1       op2     Instruction or instruction class
                //   1   10100       -       High halfword 16-bit immediate load, MOVT on page A8-491
            
                break;
                
            case 0b10010:
            case 0b10110:
                //  op     op1       op2     Instruction or instruction class
                //  1     10x10       -       MSR (immediate), and hints on page A5-206
                break;
                
            default:
                //  op     op1       op2     Instruction or instruction class
                //  1    not 10xx0   -       Data-processing (immediate) on page A5-199
            
                break;
            }
        }


        return instruction;
    }


    /**
     * Decode  Multiply and multiply accumulate
     * -------------------------------------------------------------------------------------------------------------------------------
     * |31 30 29 28|27|26|25|24|23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7  6  5  4| 3  2  1  0|Instruction Type
     * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
     * | Condition | 0| 0| 0| 0|  op       |                                   | 1  0  0  1|           | Data processing/PSR Transfer
     * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
     *
     *  Table A5-7 Multiply and multiply accumulate instructions
     *  op      Instruction                                     See                     Variant
     * ----------------------------------------------------------------------------------------
     *  000x    Multiply                                        MUL on page A8-502      All
     *  001x    Multiply Accumulate                             MLA on page A8-480      All
     *  0100    Unsigned Multiply Accumulate Accumulate Long    UMAAL on page A8-774    v6
     *  0101    UNDEFINED                                       -                       -
     *  0110    Multiply and Subtract                           MLS on page A8-482      v6T2
     *  0111    UNDEFINED                                       -                       -
     *  100x    Unsigned Multiply Long                          UMULL on page A8-778    All
     *  101x    Unsigned Multiply Accumulate Long               UMLAL on page A8-776    All
     *  110x    Signed Multiply Long                            SMULL on page A8-646    All
     *  111x    Signed Multiply Accumulate Long                 SMLAL on page A8-624    All
     * =========================================================================================
     *
     * @param raw
     * @return
     */
     
    public Instruction decodeMultiplyInstruction(int raw[]) {
        Instruction instruction = null;
        int rawInstruction = raw[0];
        int op = (rawInstruction>>20) & 0xf;
        
        switch (op) {
            case 0b0000:
            case 0b0001:
                // 000x     Multiply                                        MUL on page A8-502
                instruction =  new MUL(rawInstruction);
                break;
            case 0b0010:
            case 0b0011:
                // 001x     Multiply Accumulate                             MLA on page A8-480
                instruction =  new MLA(rawInstruction);
                break;
            case 0b0100:
                //  0100    Unsigned Multiply Accumulate Accumulate Long    UMAAL on page A8-774
                instruction =  new UMAAL(rawInstruction);
                break;
            case 0b0101:
                //  0101    UNDEFINED
                break;
            case 0b0110:
                //  0110    Multiply and Subtract                           MLS on page A8-482
                instruction =  new MLS(rawInstruction);
                break;
            case 0b0111:
                //  0111    UNDEFINED
                break;
            case 0b1000:
            case 0b1001:
                // 100x    Unsigned Multiply Long                          UMULL on page A8-778
                instruction =  new UMULL(rawInstruction);
                break;
            case 0b1010:
            case 0b1011:
                // 101x    Unsigned Multiply Accumulate Long               UMLAL on page A8-776
                instruction =  new UMLAL(rawInstruction);
                break;
            case 0b1100:
            case 0b1101:
                // 110x    Signed Multiply Long                            SMULL on page A8-646
                instruction =  new SMULL(rawInstruction);
                break;
            case 0b1110:
            case 0b1111:
                // 111x    Signed Multiply Accumulate Long                 SMLAL on page A8-624
                instruction =  new SMLAL(raw);
                break;
        }
  
        return instruction;
    }
  
    /**
     *
     * op   op1         op2     Instruction or instruction class                            Variant
     * ---------------------------------------------------------------------------------------------
     * 0    not 10xx0   xxx0    Data-processing (register) on page A5-197                   -
     * 
     * -------------------------------------------------------------------------------------------------
     * |31 30 29 28|27|26|25|24|23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8  7| 6  5| 4| 3  2  1  0|
     * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
     * | Condition | 0| 0| 0|     op       |                       |    imm5      | op2 | 0|           |
     * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
     *
     *  Table A5-3 Data-processing (register) instructions
     *  op      op2 imm5    Instruction                             See 
     * -------------------------------------------------------------------- 
     *  0000x   -   -       Bitwise AND                 AND (register) on page A8-326
     *  0001x   -   -       Bitwise Exclusive OR        EOR (register) on page A8-384
     *  0010x   -   -       Subtract                    SUB (register) on page A8-712
     *  0011x   -   -       Reverse Subtract            RSB (register) on page A8-576
     *  0100x   -   -       Add                         ADD (register, ARM) on page A8-312
     *  0101x   -   -       Add with Carry              ADC (register) on page A8-302
     *  0110x   -   -       Subtract with Carry         SBC (register) on page A8-594
     *  0111x   -   -       Reverse Subtract with Carry RSC (register) on page A8-582
     *  10xx0   -   -       See Data-processing and miscellaneous instructions on page A5-196
     *  10001   -   -       Test                        TST (register) on page A8-746
     *  10011   -   -       Test                        Equivalence TEQ (register) on page A8-740
     *  10101   -   -       Compare                     CMP (register) on page A8-372
     *  10111   -   -       Compare Negative            CMN (register) on page A8-366
     *  1100x   -   -       Bitwise OR                  ORR (register) on page A8-518
     *  1101x   00  00000   Move                        MOV (register, ARM) on page A8-488
     *          not 00000   Logical Shift Left          LSL (immediate) on page A8-468
     *          01  -       Logical Shift Right         LSR (immediate) on page A8-472
     *          10  -       Arithmetic Shift Right      ASR (immediate) on page A8-330
     *          11  00000   Rotate Right with Extend    RRX on page A8-572
     *          not 00000   Rotate Right                ROR (immediate) on page A8-568
     *  1110x   -   -       Bitwise Bit Clear           BIC (register) on page A8-342
     *  1111x   -   -       Bitwise NOT                 MVN (register) on page A8-506
     * 
     *
     * @param rawData
     * @return
     */
    public Instruction decodeDataProcessingRegister(int[] raw) {
        Instruction instruction = null;
        int rawInstruction = raw[0];

        int op = (rawInstruction>>20) & 0x1f;
        int op2 = (rawInstruction>>5) & 0x3;
        int imm5 = (rawInstruction>>7) & 0x1f;

        int op1 = op>>1;
        switch(op1){
            case 0b0000:
                //  0000x   -   -       Bitwise AND                 AND (register) on page A8-326
                instruction = new AND(raw);
                break;
            case 0b0001:
                //  0001x   -   -       Bitwise Exclusive OR        EOR (register) on page A8-384
                instruction = new EOR(raw);
                break;
            case 0b0010:
                //  0010x   -   -       Subtract                    SUB (register) on page A8-712
                break;
            case 0b0011:
                //  0011x   -   -       Reverse Subtract            RSB (register) on page A8-576
                break;
            case 0b0100:
                //  0100x   -   -       Add                         ADD (register, ARM) on page A8-312
                break;
            case 0b0101:
                //  0101x   -   -       Add with Carry              ADC (register) on page A8-302
                break;
            case 0b0110:
                //  0110x   -   -       Subtract with Carry         SBC (register) on page A8-594
                break;
            case 0b0111:
                //  0111x   -   -       Reverse Subtract with Carry RSC (register) on page A8-582
                break;

            case 0b1000:
                // 1000x
            case 0b1001:
                // 1001x
            case 0b1010:
                // 1010x
            case 0b1011:
                // 1011x
                if((op & 0b11001)==10000){
                    // 1100x   -   -       Bitwise OR                  ORR (register) on page A8-518

                }else switch(op) {
                    case 0x10001:
                        //  10001   -   -       Test                        TST (register) on page A8-746
                        break;
                    case 0x10011:
                        //  10011   -   -       Test                        Equivalence TEQ (register) on page A8-740
                        break;
                    case 0x10101:
                        //  10101   -   -       Compare                     CMP (register) on page A8-372
                        break;
                    case 0x10111:
                        //  10111   -   -       Compare Negative            CMN (register) on page A8-366
                        break;
                }
                break;

            case 0b1100:
                //  1100x   -   -       Bitwise OR                  ORR (register) on page A8-518
                break;
            case 0b1101:
                //  op      op2 imm5    Instruction                             See
                // 1101x   00  00000   Move                        MOV (register, ARM) on page A8-488
                break;

            case 0b1110:
                //  1110x   -   -       Bitwise Bit Clear           BIC (register) on page A8-342
                break;

            case 0b1111:
                //  1111x   -   -       Bitwise NOT                 MVN (register) on page A8-506
                break;


        }

        return instruction;
    }

} 
   
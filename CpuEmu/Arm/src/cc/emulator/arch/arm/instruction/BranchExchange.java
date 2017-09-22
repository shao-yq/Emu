package cc.emulator.arch.arm.instruction;

import cc.emulator.arch.arm.ArmInstruction;
import cc.emulator.core.cpu.ExecutionUnit;

/**
 *
 * Instruction: BX
 *
  -------------------------------------------------------------------------------------------------------------------------------
 |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7  6  5  4| 3  2  1  0|Instruction Type
 |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
 | Condition | 0| 0| 0| 1| 0| 0| 1| 0| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 0| 0| 0| 1|    Rn     |  Branch and Exchange
 |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------

 Operand register Rn
 If bit 0 of Rn = 1, subsequent instructions decoded as THUMB instructions
 If bit 0 of Rn = 0, subsequent instructions decoded as ARM instructions

 Mnemonic| Instruction                           |   Action                  | See Section:
 --------+---------------------------------------+---------------------------+--------------
 BX      | Branch and Exchange                   | R15 := Rn, T bit := Rn[0] | 4.3


 4.3 Branch and Exchange (BX)
 This instruction is only executed if the condition is true.
 This instruction performs a branch by copying the contents of a general register, Rn,
 into the program counter, PC. The branch causes a pipeline flush and refill from the
 address specified by Rn. This instruction also permits the instruction set to be
 exchanged. When the instruction is executed, the value of Rn[0] determines whether
 the instruction stream will be decoded as ARM or THUMB instructions.

 4.3.1 Instruction cycle times
 The BX instruction takes 2S + 1N cycles to execute, where S and N are as defined in
 6.2 Cycle Types on page 6-3.
 4.3.2 Assembler syntax
 BX - branch and exchange.
 BX{cond} Rn
 {cond} Two character condition mnemonic. See Table 4-2: Condition code
 summary on page 4-5.
 Rn is an expression evaluating to a valid register number.
 4.3.3 Using R15 as an operand
 If R15 is used as an operand, the behaviour is undefined.

 4.3.4 Examples
 ADR R0, Into_THUMB + 1  ; Generate branch target address
                        ; and set bit 0 high - hence
                        ; arrive in THUMB state.
 BX R0                   ; Branch and change to THUMB state.
 CODE16                  ; Assemble subsequent code as
 Into_THUMB              ; THUMB instructions
 .
 .
 ADR R5, Back_to_ARM : Generate branch target to word
                    : aligned ;address - hence bit 0
                         ; is low and so change back to ARM state.
 BX R5                   ; Branch and change back to ARM; state.
 .
 .
 ALIGN                   ; Word align
 CODE32                  ; Assemble subsequent code as ARM
 Back_to_ARM             ; instructions

 *
 */
public class BranchExchange extends ArmInstruction {

    private static final int DECODE_MODE_THUMB = 0;
    private static final int DECODE_MODE_ARM = 1;

    private int decodeMode;

    public BranchExchange(int[] queue) {
        super(queue);
    }

    @Override
    public void decode(int[] raw, int startIndex) {
        super.decode(raw, startIndex);

        setWritesPC(true);
        setFixedJump(false);

//        Operand register Rn
//        If bit 0 of Rn = 1, subsequent instructions decoded as THUMB instructions
//        If bit 0 of Rn = 0, subsequent instructions decoded as ARM instructions
        rn = raw[startIndex] & 0xF;
        if((rn &0x00000001) != 0){
            decodeMode = DECODE_MODE_THUMB;
        } else {
            decodeMode = DECODE_MODE_ARM;
        }
    }

    public int getDecodeMode() {
        return decodeMode;
    }

    /**
     *
     |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7  6  5  4| 3  2  1  0|Instruction Type
     |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
     | Condition | 0| 0| 0| 1| 0| 0| 1| 0| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 0| 0| 0| 1|    Rn     |  Branch and Exchange

     *
     * @param op
     * @return
     */
    public static boolean canHandle(int op) {

        if ((op & 0x0FFFFFF0) == 0x012FFF10)
            return true;
        return false;
    }

//    public static BranchExchange createInstruction(int[] queue){
//        return new BranchExchange(queue);
//    }

    @Override
    public boolean hasOpcode(int[] queue, int startIndex) {
        return canHandle(queue[startIndex]);
    }
}

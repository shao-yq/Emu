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

        rm = raw[startIndex] & 0xF;
        setWritesPC(true);
        setFixedJump(false);

        if((rawInstruction &0x00000001) != 0){
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

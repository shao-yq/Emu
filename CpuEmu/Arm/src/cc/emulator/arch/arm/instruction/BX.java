package cc.emulator.arch.arm.instruction;

/**
 * BX
 *  Branch and Exchange causes a branch to an address and instruction set specified by a register.
 *  Encoding T1 ARMv4T, ARMv5T*, ARMv6*, ARMv7
 *      BX<c> <Rm> Outside or last in IT block
 * -------------------------------------------------
 * |15 14 13 12|11|10| 9| 8| 7| 6| 5| 4| 3| 2  1  0|
 * |-----------|-----+-----|--|-----------|--------|
 * | 0  1  0  0| 0  1| 1  1| 0|   Rm      |(0)(0)(0|
 * |-----------|-----+-----+--+-----------|--------|
 *
 *  m = UInt(Rm);
 *  if InITBlock() && !LastInITBlock() then UNPREDICTABLE;
 *
 *  Encoding A1 ARMv4T, ARMv5T*, ARMv6*, ARMv7
 *      BX<c> <Rm>
 *   -------------------------------------------------------------------------------------------------------------------------------
 *  |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7  6  5  4| 3  2  1  0|Instruction Type
 *  |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
 *  | Condition | 0| 0| 0| 1| 0| 0| 1| 0| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 0| 0| 0| 1|    Rm     |  Branch and Exchange
 *  |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
 *
 *  m = UInt(Rm);
 *
 *   Assembler syntax
 *  BX{<c>}{<q>} <Rm>
 *  where:
 *  <c>, <q>        See Standard assembler syntax fields on page A8-287.
 *  <Rm>            The register that contains the branch target address and instruction set selection bit. The PC can be
 *                  used. This register can be the SP in both ARM and Thumb instructions, but ARM deprecates this use of the SP.
 *                  Note
 *                  If <Rm> is the PC in a Thumb instruction at a non word-aligned address, it results in UNPREDICTABLE
 *                  behavior because the address passed to the BXWritePC() pseudocode function has bits<1:0> = '10'.
 *  Operation
 *  if ConditionPassed() then
 *      EncodingSpecificOperations();
 *      BXWritePC(R[m]);
 *
 *  Exceptions
 *      None.
 *
 * @author Shao Yongqing
 * Date: 2017/9/24.
 */
public class BX extends BranchExchange{
    public BX(int[] queue) {
        super(queue);
    }
    
    @Override
    public boolean hasOpcode(int[] queue, int startIndex) {
        int raw = queue[startIndex];
        int op = raw;
        if ((op & 0x0FFFFFF0) == 0x012FFF10){
            return true;
        }

        return false;
    }
}

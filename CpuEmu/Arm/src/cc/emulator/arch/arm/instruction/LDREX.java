package cc.emulator.arch.arm.instruction;

import cc.emulator.arch.arm.ArmInstruction;

/**
 * LDREX
 *
 * Load Register Exclusive calculates an address from a base register value and an immediate offset, loads a word from
 * memory, writes it to a register and:
 * .    if the address has the Shared Memory attribute, marks the physical address as exclusive access for the
 *      executing processor in a global monitor
 * .    causes the executing processor to indicate an active exclusive access in the local monitor.
 *
 * For more information about support for shared memory see Synchronization and semaphores on page A3-114. For
 * information about memory accesses see Memory accesses on page A8-294.
 *
 * Encoding T1 ARMv6T2, ARMv7
 * LDREX<c> <Rt>, [<Rn>{, #<imm>}]
 *
 * -------------------------------------------------------------------------------------------------
 * |15 14 13 12|11|10| 9| 8| 7| 6| 5| 4| 3| 2  1  0|15 14|13|12|11|10  9  8| 7| 6| 5| 4| 3  2  1  0|
 * |--------------+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * | 1  1  1  0  1| 0  0| 0| 0| 1| 0| 1|   Rn      |  Rt       |(1)(1)(1)(1|     imm8              |
 * |--------------+--+--+-----------+--|-----------|-----------|-----------------------------------|
 *
 * t = UInt(Rt); n = UInt(Rn); imm32 = ZeroExtend(imm8:’00’, 32);
 * if t IN {13,15} || n == 15 then UNPREDICTABLE;
 *
 * Encoding A1 ARMv6*, ARMv7
 * LDREX<c> <Rt>, [<Rn>]
 *
 * |31 30 29 28|27|26|25|24 23|22|21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7  6  5  4| 3  2  1  0|Instruction Type
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|-----------------
 * | Condition | 0| 0| 0| 1| 1| 0| 0| 1|   Rn      |  Rd       |(1)(1)(1)(1| 1  0  0  1|(1)(1)(1)(1|  LDREX
 * |-----------|--+--+--+--+-----------|-----------|-----------|-----------------------------------|-----------------
 *
 * For the case when cond is 0b1111, see Unconditional instructions on page A5-216.
 * t = UInt(Rt); n = UInt(Rn); imm32 = Zeros(32); // Zero offset
 * if t == 15 || n == 15 then UNPREDICTABLE;
 *
 * Assembler syntax
 *  LDREX{<c>}{<q>} <Rt>, [<Rn> {, #<imm>}]
 * where:
 *  <c>, <q>    See Standard assembler syntax fields on page A8-287.
 *  <Rt>        The destination register.
 *  <Rn>        The base register. The SP can be used.
 *  <imm>       The immediate offset added to the value of <Rn> to form the address. <imm> can be omitted, meaning
 *              an offset of 0. Values are:
 *              Encoding T1     multiples of 4 in the range 0-1020
 *              Encoding A1     omitted or 0.
 * Operation
 *  if ConditionPassed() then
 *      EncodingSpecificOperations(); NullCheckIfThumbEE(n);
 *      address = R[n] + imm32;
 *      SetExclusiveMonitors(address,4);
 *      R[t] = MemA[address,4];
 * Exceptions
 *  Data Abort.
 *
 * @author Shao Yongqing
 * Date: 2017/10/17.
 */
public class LDREX extends LoadRegisterExclusive {
    public LDREX(int rawInstruction) {
        super(rawInstruction);
    }

    @Override
    protected int getOp(){
        return 0b1001;
    }

}

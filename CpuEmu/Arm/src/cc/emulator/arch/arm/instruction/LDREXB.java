package cc.emulator.arch.arm.instruction;

import cc.emulator.arch.arm.ArmInstruction;

/**
 * LDREXB
 * Load Register Exclusive Byte derives an address from a base register value, loads a byte from memory, zero-extends
 * it to form a 32-bit word, writes it to a register and:
 * .    if the address has the Shared Memory attribute, marks the physical address as exclusive access for the
 *      executing processor in a global monitor
 * .    causes the executing processor to indicate an active exclusive access in the local monitor.
 * For more information about support for shared memory see Synchronization and semaphores on page A3-114. For
 * information about memory accesses see Memory accesses on page A8-294.
 *
 * Encoding T1 ARMv7
 *      LDREXB<c> <Rt>, [<Rn>]
 *
 * -------------------------------------------------------------------------------------------------
 * |15 14 13 12|11|10| 9| 8| 7| 6| 5| 4| 3| 2  1  0|15 14|13|12|11|10  9  8| 7| 6| 5| 4| 3  2  1  0|
 * |--------------+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * | 1  1  1  0  1| 0  0  0  1  1  0| 1|   Rn      |  Rt       |(1)(1)(1)(1| 0  1  0  0|(1)(1)(1)(1|
 * |--------------+--+--+-----------+--|-----------|-----------|-----------------------------------|
 *
 *  t = UInt(Rt); n = UInt(Rn);
 *  if t IN {13,15} || n == 15 then UNPREDICTABLE;
 *
 * Encoding A1 ARMv6K, ARMv7
 *      LDREXB<c> <Rt>, [<Rn>]
 *
 * |31 30 29 28|27|26|25|24 23|22|21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7  6  5  4| 3  2  1  0|Instruction Type
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|-----------------
 * | Condition | 0| 0| 0| 1| 1| 1| 0| 1|   Rn      |  Rd       |(1)(1)(1)(1| 1  0  0  1|(1)(1)(1)(1|  LDREX
 * |-----------|--+--+--+--+-----------|-----------|-----------|-----------------------------------|-----------------
 *
 * For the case when cond is 0b1111, see Unconditional instructions on page A5-216.
 *  t = UInt(Rt); n = UInt(Rn);
 *  if t == 15 || n == 15 then UNPREDICTABLE;
 *
 * Assembler syntax
 * LDREXB{<c>}{<q>} <Rt>, [<Rn>]
 * where:
 * <c>, <q>     See Standard assembler syntax fields on page A8-287.
 * <Rt>         The destination register.
 * <Rn>         The base register. The SP can be used.
 * Operation
 *  if ConditionPassed() then
 *      EncodingSpecificOperations(); NullCheckIfThumbEE(n);
 *      address = R[n];
 *      SetExclusiveMonitors(address,1);
 *      R[t] = ZeroExtend(MemA[address,1], 32);
 * Exceptions
 *  Data Abort.
 *
 * @author Shao Yongqing
 * Date: 2017/10/17.
 */
public class LDREXB  extends LoadRegisterExclusive {
    public LDREXB(int rawInstruction) {
        super(rawInstruction);
    }

    @Override
    protected int getOp(){
        return 0b1101;
    }


}

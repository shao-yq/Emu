package cc.emulator.arch.arm.instruction;

import cc.emulator.arch.arm.ArmInstruction;
import cc.emulator.core.cpu.Instruction;

/**
 *
 *
 * LDREXD
 * Load Register Exclusive Doubleword derives an address from a base register value, loads a 64-bit doubleword from
 * memory, writes it to two registers and:
 * .    if the address has the Shared Memory attribute, marks the physical address as exclusive access for the
 *      executing processor in a global monitor
 * .    causes the executing processor to indicate an active exclusive access in the local monitor.
 * For more information about support for shared memory see Synchronization and semaphores on page A3-114. For
 * information about memory accesses see Memory accesses on page A8-294.
 *
 * Encoding T1 ARMv7
 *  LDREXD<c> <Rt>, <Rt2>, [<Rn>]
 *
 * -------------------------------------------------------------------------------------------------
 * |15 14 13 12|11|10| 9| 8| 7| 6| 5| 4| 3| 2  1  0|15 14|13|12|11|10  9  8| 7| 6| 5| 4| 3  2  1  0|
 * |--------------+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * | 1  1  1  0  1| 0  0  0  1  1  0| 1|   Rn      |  Rt       |   Rt2     | 0  1  1  1|(1)(1)(1)(1|
 * |--------------+--+--+-----------+--|-----------|-----------|-----------------------------------|
 *
 * t = UInt(Rt); t2 = UInt(Rt2); n = UInt(Rn);
 * if t IN {13,15} || t2 IN {13,15} || t == t2 || n == 15 then UNPREDICTABLE;
 *
 * Encoding A1 ARMv6K, ARMv7
 *  LDREXD<c> <Rt>, <Rt2>, [<Rn>]
 *
 * |31 30 29 28|27|26|25|24 23|22|21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7  6  5  4| 3  2  1  0|Instruction Type
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|-----------------
 * | Condition | 0| 0| 0| 1| 1| 0| 1| 1|   Rn      |  Rd       |(1)(1)(1)(1| 1  0  0  1|(1)(1)(1)(1|  LDREXD
 * |-----------|--+--+--+--+-----------|-----------|-----------|-----------------------------------|-----------------
 *
 * For the case when cond is 0b1111, see Unconditional instructions on page A5-216.
 * t = UInt(Rt); t2 = t+1; n = UInt(Rn);
 * if Rt<0> == ‘1’ || Rt == ‘1110’ || n == 15 then UNPREDICTABLE;
 *
 *
 * Assembler syntax
 *      LDREXD{<c>}{<q>} <Rt>, <Rt2>, [<Rn>]
 * where:
 *  <c>, <q>    See Standard assembler syntax fields on page A8-287.
 *  <Rt>        The first destination register. For an ARM instruction, <Rt> must be even-numbered and not R14.
 *  <Rt2>       The second destination register. For an ARM instruction, <Rt2> must be <R(t+1)>.
 *  <Rn>        The base register. The SP can be used.
 * Operation
 *  if ConditionPassed() then
 *      EncodingSpecificOperations(); NullCheckIfThumbEE(n);
 *      address = R[n];
 *      // LDREXD requires doubleword-aligned address
 *      if address<2:0> != ‘000’ then AlignmentFault(address, FALSE)
 *      SetExclusiveMonitors(address,8);
 *      // See the description of Single-copy atomicity for details of whether
 *      // the two loads are 64-bit single-copy atomic.
 *      R[t] = MemA[address,4];
 *      R[t2] = MemA[address+4,4];
 * Exceptions
 *  Data Abort
 *
 * @author Shao Yongqing
 * Date: 2017/10/17.
 */
public class LDREXD  extends LoadRegisterExclusive {
    public LDREXD(int rawInstruction) {
        super(rawInstruction);
    }

    @Override
    protected int getOp(){
        return 0b1011;
    }

}

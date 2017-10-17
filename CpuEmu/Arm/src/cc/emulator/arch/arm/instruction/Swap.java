package cc.emulator.arch.arm.instruction;

import cc.emulator.arch.arm.ArmInstruction;

/**
 * SWP, SWPB
 * SWP (Swap) swaps a word between registers and memory. SWP loads a word from the memory address given by the
 * value of register <Rn>. The value of register <Rt2> is then stored to the memory address given by the value of <Rn>,
 * and the original loaded value is written to register <Rt>. If the same register is specified for <Rt> and <Rt2>, this
 * instruction swaps the value of the register and the value at the memory address.
 *
 * SWPB (Swap Byte) swaps a byte between registers and memory. SWPB loads a byte from the memory address given by
 * the value of register <Rn>. The value of the least significant byte of register <Rt2> is stored to the memory address
 * given by <Rn>, the original loaded value is zero-extended to a 32-bit word, and the word is written to register <Rt>.
 * If the same register is specified for <Rt> and <Rt2>, this instruction swaps the value of the least significant byte of
 * the register and the byte value at the memory address, and clears the most significant three bytes of the register.
 * For both instructions, the memory system ensures that no other memory access can occur to the memory location
 * between the load access and the store access.
 * --- Note ---
 * .The SWP and SWPB instructions rely on the properties of the system beyond the processor to ensure that no
 *  stores from other observers can occur between the load access and the store access, and this might not be
 *  implemented for all regions of memory on some system implementations. In all cases, SWP and SWPB do ensure
 *  that no stores from the processor that executed the SWP or SWPB instruction can occur between the load access
 *  and the store access of the SWP or SWPB.
 * .ARM deprecates the use of SWP and SWPB, and strongly recommends that new software uses:
 *  — LDREX/STREX in preference to SWP
 *  — LDREXB/STREXB in preference to SWPB.
 * .If the translation table entries that relate to a memory location accessed by the SWP or SWPB instruction change,
 *  or are seen to change by the executing processor as a result of TLB eviction, this might mean that the
 *  translation table attributes, permissions or addresses for the load are different to those for the store. In this
 *  case, the architecture makes no guarantee that no memory access occur to these memory locations between
 *  the load and store.
 * The Virtualization Extensions make the SWP and SWPB instructions OPTIONAL and deprecated:
 * . If an implementation does not include the SWP and SWPB instructions, the ID_ISAR0.Swap_instrs and
 *  ID_ISAR4.SWP_frac fields are zero, see About the Instruction Set Attribute registers on page B7-1952.
 * . In an implementation that includes SWP and SWPB, both instructions are UNDEFINED in Hyp mode.

 * Encoding A1 ARMv4*, ARMv5T*, deprecated in ARMv6* and ARMv7, OPTIONAL in ARMv7VE
 *  SWP{B}<c> <Rt>, <Rt2>, [<Rn>]
 * |31 30 29 28|27|26|25|24 23|22|21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7  6  5  4| 3  2  1  0|Instruction Type
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
 * | Condition | 0| 0| 0| 1| 0| B| 0| 0|   Rn      |  Rt       |(0)(0)(0)(0| 1  0  0  1|  Rt2      |  SWP, SWPB
 * |-----------|--+--+--+--+-----------|-----------|-----------|-----------------------------------|------------------------------
 * Condition field             Byte bit
 *                             0 = WORD (4 bytes)
 *                             1 = BYTE (1 byte)
 *
 *
 * For the case when cond is 0b1111, see Unconditional instructions on page A5-216.
 *      t = UInt(Rt); t2 = UInt(Rt2); n = UInt(Rn); size = if B == ‘1’ then 1 else 4;
 *      if t == 15 || t2 == 15 || n == 15 || n == t || n == t2 then UNPREDICTABLE;
 *
 *
 * Assembler syntax
 *  SWP{B}{<c>}{<q>} <Rt>, <Rt2>, [<Rn>]
 * where:
 *  B           If B is present, the instruction operates on a byte. Otherwise, it operates on a word.
 *  <c>, <q>    See Standard assembler syntax fields on page A8-287.
 *  <Rt>        The destination register.
 *  <Rt2>       Contains the value that is stored to memory.
 *  <Rn>        Contains the memory address to load from.
 *  The pre-UAL syntax SWP<c>B is equivalent to SWPB<c>.
 * Operation
 *  if ConditionPassed() then
 *      EncodingSpecificOperations();
 *      if CurrentModeIsHyp() then UNDEFINED;
 *      // The MemA[] accesses in the next two statements are locked together, that is, the memory
 *      // system must ensure that no other access to the same location can occur between them.
 *      data = ZeroExtend(MemA[R[n], size],32);
 *      MemA[R[n], size] = R[t2]<8*size-1:0>;
 *      if size == 1 then // SWPB
 *          R[t] = data;
 *      else // SWP
 *          // Rotation in the following will always be by zero in ARMv7, due to alignment checks,
 *          // but can be nonzero in legacy configurations.
 *          R[t] = ROR(data, 8*UInt(R[n]<1:0>));
 * Exceptions
 *  Data Abort.
 *
 * @author Shao Yongqing
 * Date: 2017/10/17.
 */
public class Swap extends ArmInstruction {
    private  boolean byteFlag;

    public Swap() {
        super();
    }

    public Swap(int[] rawInstruction) {
        super(rawInstruction);
    }

    @Override
    public int getOpCode() {
        return 0;
    }

    @Override
    public boolean hasOpcode(int[] queue, int startIndex) {
        int raw = queue[startIndex];
        int cond = (raw>>28)&0xf;
        int op = (raw>>20) & 0b11111011;
        int op2 = (raw>>4) & 0b1111;
        if((cond!=0xf) && (op == 0b00010000) && (op2==0b1001))
            return true;

        return false;
    }
    @Override
    public void decode(int[] raw, int startIndex) {
        super.decode(raw, startIndex);

        Rt = (rawInstruction>>12) & 0xf;
        Rt2 = (rawInstruction>>0) & 0xf;
        Rn = (rawInstruction>>16) & 0xf;
        // BYTE flag
        byteFlag =  (rawInstruction & 0x00400000)!=0;

    }

}

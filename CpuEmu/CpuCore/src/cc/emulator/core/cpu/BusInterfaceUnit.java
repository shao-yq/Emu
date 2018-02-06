package cc.emulator.core.cpu;

import cc.emulator.core.cpu.register.ProgramCounter;
import cc.emulator.core.cpu.register.SegmentRegister;

/**
 * @author Shao Yongqing
 * Date: 2017/7/27.
 */
public interface BusInterfaceUnit {
    SegmentRegister[] getSegmentRegisters();
    AddressUnit getAddressUnit();
    InstructionQueue getInstructionQueue();
    ProgramCounter getProgramCounter();

    SegmentRegister getSegmentRegister(String name);
    MemoryAccessor getMemoryAccessor();


    void reset();

    void fetchInstructions(MemoryAccessor memoryAccessor, MemoryLocator instructionLocator);
    void fetchInstructions(MemoryAccessor memoryAccessor, MemoryLocator instructionLocator, int offset, InstructionQueue instructionQueue);

    int currentAddress(MemoryAccessor memoryAccessor, MemoryLocator instructionLocator);
}

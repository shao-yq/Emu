package cc.emulator.core.cpu;

import cc.emulator.core.cpu.register.PointerIndexer;
import cc.emulator.core.cpu.register.ProgramCounter;
import cc.emulator.core.cpu.register.SegmentRegister;

/**
 * @author Shao Yongqing
 * Date: 2017/7/27.
 */
public interface BusInterfaceUnit {
    SegmentRegister[] getSegmentRegisters();
    PointerIndexer[] getPointerIndexers();
    AddressGenerator getAddressGenerator();
    InstructionQueue getInstructionQueue();
    ProgramCounter getProgramCounter();

    SegmentRegister getSegmentRegister(String name);

    PointerIndexer getPointerIndexer(String name);

    void reset();

    void fetchInstructions(MemoryAccessor memoryAccessor, MemoryLocator instructionLocator);
}

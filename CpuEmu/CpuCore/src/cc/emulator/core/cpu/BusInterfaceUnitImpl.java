package cc.emulator.core.cpu;

import cc.emulator.core.MemoryManager;
import cc.emulator.core.cpu.bus.DataBus;
import cc.emulator.core.cpu.register.ProgramCounter;
import cc.emulator.core.cpu.register.SegmentRegister;

public abstract class BusInterfaceUnitImpl implements BusInterfaceUnit {
    protected SegmentRegister segmentRegisters[];
    protected ProgramCounter programCounter;
    protected AddressUnit addressUnit;
    protected InstructionQueue instructionQueue;
    protected MemoryAccessor memoryAccessor;
    protected MemoryManager memoryManager;

    public  MemoryAccessor getMemoryAccessor(){
        return memoryAccessor;
    }
    @Override
    public ProgramCounter getProgramCounter() {
        return programCounter;
    }

    public BusInterfaceUnitImpl(MemoryManager mm, AddressUnit addressUnit){
        this.memoryManager = mm;
        this.addressUnit =  addressUnit;

        instructionQueue=createInstructionQueue();
        segmentRegisters=createSegmentRegisters();
        programCounter =createProgramCounter();
        memoryAccessor = createMemoryAccessor(mm);
    }

    protected abstract ProgramCounter createProgramCounter();

    public abstract SegmentRegister[] createSegmentRegisters();
    public abstract InstructionQueue createInstructionQueue();
    protected abstract MemoryAccessor createMemoryAccessor(MemoryManager mm);
    protected abstract DataBus createDataBus();

    @Override
    public SegmentRegister[] getSegmentRegisters() {
        return segmentRegisters;
    }


    @Override
    public AddressUnit getAddressUnit() {
        return addressUnit;
    }

    @Override
    public InstructionQueue getInstructionQueue() {
        return instructionQueue;
    }

    @Override
    public void reset() {
        addressUnit.reset();
        instructionQueue.reset();
        for(SegmentRegister segmentRegister:segmentRegisters)
            segmentRegister.reset();
        programCounter.reset();
    }
}

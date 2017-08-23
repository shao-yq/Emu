package cc.emulator.core.cpu;

import cc.emulator.core.cpu.register.ProgramCounter;
import cc.emulator.core.cpu.register.PointerIndexer;
import cc.emulator.core.cpu.register.SegmentRegister;

public abstract class BusInterfaceUnitImpl implements BusInterfaceUnit {
    protected SegmentRegister segmentRegisters[];
    protected PointerIndexer pointerIndexers[];
    protected ProgramCounter programCounter;
    protected AddressGenerator addressGenerator;
    protected InstructionQueue instructionQueue;

    @Override
    public ProgramCounter getProgramCounter() {
        return programCounter;
    }

    public BusInterfaceUnitImpl(){
        addressGenerator=createAddressGenerator();
        instructionQueue=createInstructionQueue();
        pointerIndexers=createPointerIndexers();
        segmentRegisters=createSegmentRegisters();
        programCounter =createProgramCounter();
    }

    protected abstract ProgramCounter createProgramCounter();

    public abstract SegmentRegister[] createSegmentRegisters();
    public abstract PointerIndexer[] createPointerIndexers();
    public abstract AddressGenerator createAddressGenerator();
    public abstract InstructionQueue createInstructionQueue();

    @Override
    public SegmentRegister[] getSegmentRegisters() {
        return segmentRegisters;
    }

    @Override
    public PointerIndexer[] getPointerIndexers() {
        return pointerIndexers;
    }

    @Override
    public AddressGenerator getAddressGenerator() {
        return addressGenerator;
    }

    @Override
    public InstructionQueue getInstructionQueue() {
        return instructionQueue;
    }

    @Override
    public void reset() {
        addressGenerator.reset();
        instructionQueue.reset();
        for(PointerIndexer pointerIndexer:pointerIndexers)
            pointerIndexer.reset();
        for(SegmentRegister segmentRegister:segmentRegisters)
            segmentRegister.reset();
        programCounter.reset();
    }
}

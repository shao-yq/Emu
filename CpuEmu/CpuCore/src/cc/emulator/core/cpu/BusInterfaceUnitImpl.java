package cc.emulator.core.cpu;

import cc.emulator.core.cpu.register.ProgramCounter;
import cc.emulator.core.cpu.register.SegmentRegister;

public abstract class BusInterfaceUnitImpl implements BusInterfaceUnit {
    protected SegmentRegister segmentRegisters[];
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
        segmentRegisters=createSegmentRegisters();
        programCounter =createProgramCounter();
    }

    protected abstract ProgramCounter createProgramCounter();

    public abstract SegmentRegister[] createSegmentRegisters();
    public abstract AddressGenerator createAddressGenerator();
    public abstract InstructionQueue createInstructionQueue();

    @Override
    public SegmentRegister[] getSegmentRegisters() {
        return segmentRegisters;
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
        for(SegmentRegister segmentRegister:segmentRegisters)
            segmentRegister.reset();
        programCounter.reset();
    }
}

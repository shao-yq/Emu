package cc.emulator.core.cpu;

import cc.emulator.core.ProgrammableInterruptController;
import cc.emulator.core.ProgrammableIntervalTimer;
import cc.emulator.core.MemoryManager;
import cc.emulator.core.Peripheral;
import cc.emulator.core.cpu.bus.DataBus;

/**
 * @author Shao Yongqing
 * Date: 2017/7/25.
 */
public abstract class Cpu {

    protected Peripheral[] peripherals;

    protected ExecutionUnit executionUnit;
    protected BusInterfaceUnit busInterfaceUnit;

    public ExecutionUnit getExecutionUnit() {
        return executionUnit;
    }

    public abstract ExecutionUnit createEU();
    public abstract BusInterfaceUnit createBIU();

    public BusInterfaceUnit getBusInterfaceUnit() {
        return busInterfaceUnit;
    }

    /**
     * Execute all instructions.
     */
    public void run() {
        while (tick());
    }

    //public abstract void setMemory(int[] memory) ;
    /**
     * Resets the CPU to its default state.
     */
    public abstract void reset();

    /**
     * Fetches and executes an instruction.
     *
     * @return true if instructions remain, false otherwise
     */
    public abstract boolean tick();

    protected AddressGenerator addressGenerator;
    protected MemoryAccessor memoryAccessor;

    protected InstructionUnit instructionUnit;

    public MemoryManager getMemoryManager() {
        return memoryManager;
    }

    protected MemoryManager memoryManager;

    protected MemoryLocator instructionLocator;
    protected MemoryLocator dataLocator;

    public Cpu(MemoryManager mm){
        busInterfaceUnit = createBIU();

        ////////////
        this.memoryManager = mm;
        addressGenerator =  busInterfaceUnit.getAddressGenerator();
        memoryAccessor =  createMemoryAccessor(mm);

        instructionUnit =  createInstructionUnit();

        instructionLocator =  createInstructionLocator();
        //dataLocator =  createDataLocator();
        executionUnit = createEU();
    }

    protected abstract MemoryLocator createInstructionLocator();

    protected abstract InstructionUnit createInstructionUnit();


    protected abstract MemoryAccessor createMemoryAccessor(MemoryManager mm);

    protected abstract AddressGenerator createAddressGenerator();
    protected abstract DataBus createDataBus();

    public AddressGenerator getAddressGenerator() {
        return addressGenerator;
    }

    public MemoryAccessor getMemoryAccessor() {
        return memoryAccessor;
    }

    public void setPeripherals(Peripheral[] peripherals) {
        this.peripherals = peripherals;
        executionUnit.setPeripherals(peripherals);
    }

    protected ProgrammableInterruptController pic;
    public void setPic(ProgrammableInterruptController pic) {
        this.pic = pic;
        executionUnit.setPic(pic);
    }
    protected ProgrammableIntervalTimer pit;

    public void setPit(ProgrammableIntervalTimer pit) {
        this.pit = pit;
        executionUnit.setPit(pit);
    }

    protected boolean pipelineExecute() {
        // Bus Unit to fetch instruction from memory
        fetchInstructions();

        // Instruction unit to decode the instruction from the raw instruction queue
        Instruction instruction =  decodeInstruction();

        // Execution Unit to execute the instruction in the Decodec Instruction Queue
        return executeInstrction(instruction);
    }

    protected abstract boolean executeInstrction(Instruction instruction);

    protected abstract Instruction decodeInstruction();

    protected abstract void fetchInstructions();
}

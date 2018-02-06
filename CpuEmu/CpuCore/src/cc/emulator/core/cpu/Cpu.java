package cc.emulator.core.cpu;

import cc.emulator.core.ProgrammableInterruptController;
import cc.emulator.core.ProgrammableIntervalTimer;
import cc.emulator.core.MemoryManager;
import cc.emulator.core.Peripheral;
import cc.emulator.core.cpu.register.PointerIndexer;
import cc.emulator.core.cpu.register.ProgramCounter;
import cc.emulator.core.cpu.register.SegmentRegister;
import cc.emulator.core.cpu.register.StatusRegister;

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

    public SegmentRegister[] getSegmentRegisters(){
        return getBusInterfaceUnit().getSegmentRegisters();
    }

    public PointerIndexer[] getPointerIndexers(){
        return getExecutionUnit().getPointerIndexers();
    }

    public ProgramCounter getProgramCounter(){
        return getBusInterfaceUnit().getProgramCounter();
    }

    /**
     * Execute all instructions.
     */
    public void run() {
        while (tick());
    }

    /**
     * Resets the CPU to its default state.
     */
    public void reset() {
        executionUnit.reset();
    }


    /**
     * Fetches and executes an instruction.
     *
     * @return true if instructions remain, false otherwise
     */
    public boolean tick() {
        // Single-step mode.
        executionUnit.trySingleStepMode();
        // External maskable interrupts.
        executionUnit.tryExternalMaskabkeInterrupts(pic);

        return pipelineExecute();
    }

    protected AddressUnit addressUnit;
    protected MemoryAccessor memoryAccessor;

    public InstructionUnit getInstructionUnit() {
        return instructionUnit;
    }

    protected InstructionUnit instructionUnit;

    public MemoryManager getMemoryManager() {
        return memoryManager;
    }

    protected MemoryManager memoryManager;

    protected MemoryLocator instructionLocator;
    protected MemoryLocator dataLocator;

    public Cpu(MemoryManager mm){
        this.memoryManager = mm;

        addressUnit = createAddressUnit();
        busInterfaceUnit = createBIU();
        ////////////
        memoryAccessor =  busInterfaceUnit.getMemoryAccessor();

        instructionUnit =  createInstructionUnit();
        instructionLocator =  createInstructionLocator();
        //dataLocator =  createDataLocator();
        executionUnit = createEU();
    }

    protected abstract MemoryLocator createInstructionLocator();

    protected abstract InstructionUnit createInstructionUnit();

    public abstract AddressUnit createAddressUnit();



    public AddressUnit getAddressUnit() {
        return addressUnit;
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
        fetchRawInstructions();

        // Instruction unit to decode the instruction from the raw instruction queue
        decodeInstruction();

        // Fetch and exeute instruction
        return executeInstruction();
    }

    protected  boolean executeInstruction(){
        Instruction instruction = fetchInstruction();
        if(instruction!=null) {
            // Execution Unit to execute the instruction in the Decodec Instruction Queue
            return executeInstruction(instruction);
        }

        // If no instruction available, just return TRUE for next tick.
        return true;
    }

    protected Instruction fetchInstruction() {
        return instructionUnit.nextInstruction();
    }

    protected abstract void waitStepCommand();

    protected boolean executeInstruction(Instruction instruction){
        // Validate the Instruction
        if(instruction==null){
            // If no instruction available, just return true for next tick
            return true;
        }
        if(executionUnit.isStepMode()) {
            notifyStepModeListener();

            // wait for next step confirmation
            waitStepCommand();
        }
        return executionUnit.execute(instruction);
    }


    protected Instruction decodeInstruction() {
        // Current instruction decoded
        Instruction instruction = instructionUnit.decode(busInterfaceUnit.getInstructionQueue());

        if(instruction!=null) {
            instructionLocator.incOffset(instruction.getLength());
        }

        return instruction;
    }

    public void fetchRawInstructions(int offset, InstructionQueue instructionQueue){
        busInterfaceUnit.fetchInstructions(getMemoryAccessor(),instructionLocator, offset, instructionQueue);
    }

    protected void fetchRawInstructions(){
        busInterfaceUnit.fetchInstructions(getMemoryAccessor(),instructionLocator);
    }

    public StatusRegister getStatusRegister() {
        return getExecutionUnit().getStatusRegister();
    }

    public void toggleStep() {
        executionUnit.toggleStep();
    }


    public void setStepModeListener(StepModeListener stepModeListener){
        this.stepModeListener = stepModeListener;
    }

    StepModeListener stepModeListener;

    public void notifyStepModeListener(){
        if (stepModeListener !=null) {
            stepModeListener.onStepping(this);
        }
    }

    public abstract void nextStep();

    public int currentAddress() {
        return busInterfaceUnit.currentAddress(getMemoryAccessor(),instructionLocator);
    }
}

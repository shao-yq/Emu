package cc.emulator.core.cpu;

import cc.emulator.core.ProgrammableInterruptController;
import cc.emulator.core.ProgrammableIntervalTimer;
import cc.emulator.core.computer.MemoryManager;
import cc.emulator.core.Peripheral;
import cc.emulator.core.cpu.bus.DataBus;

/**
 * @author Shao Yongqing
 * Date: 2017/7/25.
 */
public abstract class Cpu {

    protected Peripheral[] peripherals;

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

    protected Stack stack;
    protected AddressGenerator addressGenerator;
    protected MemoryAccessor memoryAccessor;

    protected Decodable decoder;

    public MemoryManager getMemoryManager() {
        return memoryManager;
    }

    protected MemoryManager memoryManager;

    protected MemoryLocator instructionLocator;
    protected MemoryLocator dataLocator;


    public Cpu(MemoryManager mm){
        this.memoryManager = mm;
        addressGenerator =  createAddressGenerator();
        memoryAccessor =  createMemoryAccessor(mm);

        stack = createStack();
        stack.setAddressGenerator(addressGenerator);
        stack.setMemoryAccessor(memoryAccessor);

        decoder = createDecoder();

        instructionLocator =  createInstructionLocator();
        //dataLocator =  createDataLocator();
    }

    protected abstract MemoryLocator createInstructionLocator();

    protected abstract Decodable createDecoder();

    protected abstract MemoryAccessor createMemoryAccessor(MemoryManager mm);

    protected abstract Stack createStack();

    protected abstract AddressGenerator createAddressGenerator();
    protected abstract DataBus createDataBus();

    public Stack getStack() {
        return stack;
    }

    public AddressGenerator getAddressGenerator() {
        return addressGenerator;
    }

    public MemoryAccessor getMemoryAccessor() {
        return memoryAccessor;
    }

    public void setPeripherals(Peripheral[] peripherals) {
        this.peripherals = peripherals;
    }

    protected ProgrammableInterruptController pic;
    public void setPic(ProgrammableInterruptController pic) {
        this.pic = pic;
    }
    protected ProgrammableIntervalTimer pit;

    public void setPit(ProgrammableIntervalTimer pit) {
        this.pit = pit;
    }


}

package cc.emulator.x86.i8086;

import cc.emulator.core.MemoryManager;
import cc.emulator.core.cpu.*;
import cc.emulator.core.cpu.bus.DataBus;
import cc.emulator.core.cpu.register.ProgramCounter;
import cc.emulator.core.cpu.register.SegmentRegister;
import cc.emulator.x86.intel.IntelAddressGenerator;
import cc.emulator.x86.intel.IntelDataBus;
import cc.emulator.x86.intel.IntelMemoryAccessor;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Shao Yongqing
 * Date: 2017/9/16.
 */
public class Intel8086 extends Cpu implements Intel8086InstructionSet {

    @Override
    protected MemoryAccessor createMemoryAccessor(MemoryManager mm) {
        return new IntelMemoryAccessor(mm, createDataBus());
    }

    protected DataBus createDataBus() {
        return new IntelDataBus();
    }

    @Override
    protected AddressGenerator createAddressGenerator() {
        return new IntelAddressGenerator();
    }

    @Override
    protected InstructionUnit createInstructionUnit() {
        return new IU8086();
    }

    /**
     * Entry point. For now it executes a little test program.
     */
    public static void main(final String[] args) {
        // Instantiate a new CPU.
        MemoryManager mm = new MemoryManager();
        final Intel8086 cpu = new Intel8086(mm);
        // Reset the CPU.
        cpu.reset();
        try {
            // Try loading IBM ROM BIOS.
            cpu.load(0xfe000, "bios.bin");
            // Try loading IBM ROM BASIC.
            cpu.load(0xf6000, "basic.bin");
            // Execute all instructions.
            cpu.run();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a binary file into memory at the specified address.
     *
     * @param addr
     *            the address
     * @param path
     *            the file path
     * @throws IOException
     */
    public void load(final int addr, final String path) throws IOException {
        final InputStream is = getClass().getClassLoader().getResourceAsStream(path);
        final byte[] bin = new byte[is.available()];
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(is);
            dis.readFully(bin);
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (dis != null)
                dis.close();
            is.close();
        }

        int memory[] = memoryManager.getMemoryBase();

        for (int i = 0; i < bin.length; i++)
            memory[addr + i] = bin[i] & 0xff;
    }

    @Override
    public ExecutionUnit createEU() {
        return new EU8086(instructionLocator,
                getAddressGenerator(),
                instructionUnit,
                busInterfaceUnit,
                memoryAccessor
                );
    }

    @Override
    public BusInterfaceUnit createBIU() {
        return new BIU8086();
    }

    @Override
    protected MemoryLocator createInstructionLocator() {
        SegmentRegister cs = busInterfaceUnit.getSegmentRegister("CS");
        ProgramCounter pc = busInterfaceUnit.getProgramCounter();
        return new RegisteredMemoryLocator(cs,pc);
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

    public Intel8086(MemoryManager mm){
        super(mm);
    }

    boolean pipelineExecute() {
        // Bus Unit to fetch instruction from memory
        fetchInstructions();

        // Instruction unit to decode the instruction from the raw instruction queue
        Instruction8086 instruction = decode();

        // Execution Unit to execute the instruction in the Decodec Instruction Queue
        return executionUnit.execute(instruction);
    }
    void fetchInstructions(){

        busInterfaceUnit.fetchInstructions(getMemoryAccessor(),instructionLocator);
    }
    private Instruction8086 decode() {
        // Current instruction decoded
        Instruction8086 instruction;

        //instruction = (Instruction8086) decoder.decode(busInterfaceUnit.getInstructionQueue());
        instruction = (Instruction8086) instructionUnit.decode(busInterfaceUnit.getInstructionQueue());
        instructionLocator.incOffset(instruction.getLength());
        return instruction;
    }

}

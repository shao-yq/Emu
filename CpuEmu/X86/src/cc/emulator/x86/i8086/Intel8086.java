package cc.emulator.x86.i8086;

import cc.emulator.core.MemoryManager;
import cc.emulator.core.cpu.*;
import cc.emulator.core.cpu.register.ProgramCounter;
import cc.emulator.core.cpu.register.SegmentRegister;
import cc.emulator.x86.intel.IntelAddressUnit;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * The 8086 CPU is characterized by:
 * - a standard operating speed of 5 MHz (200 ns a cycle time).
 * - chips housed in reliable 40-pin packages.
 * - a processor that operates on both 8- and 16-bit data types.
 * - up to 1 megabyte of memory that can be addressed, along with a separate
 * 64k byte I/O space.
 *
 * CPU Architecture
 *
 * CPU incorporates two separate processing units; the Execution Unit or "EU"
 * and the Bus Interface Unit or "BIU". The BIU for the 8086 incorporates a
 * 16-bit data bus and a 6-byte instruction queue.
 *
 * The EU is responsible for the execution of all instructions, for providing
 * data and addresses to the BIU, and for manipulating the general registers
 * and the flag register. Except for a few control pins, the EU is completely
 * isolated from the "outside world." The BIU is responsible for executing all
 * external bus cycles and consists of the segment and communication registers,
 * the instruction pointer and the instruction object code queue. The BIU
 * combines segment and offset values in its dedicated adder to derive 20-bit
 * addresses, transfers data to and from the EU on the ALU data bus and loads
 * or "prefetches" instructions into the queue from which they are fetched by
 * the EU.
 *
 * The EU, when it is ready to execute an instruction, fetches the instruction
 * object code byte from the BIU's instruction queue and then executes the
 * instruction. If the queue is empty when the EU is ready to fetch an
 * instruction byte, the EU waits for the instruction byte to be fetched. In
 * the course of instruction execution, if a memory location or I/O port must
 * be accessed, the EU requests the BIU to perform the required bus cycle.
 *
 * The two processing sections of the CPU operate independently. In the 8086
 * CPU, when two or more bytes of the 6-byte instruction queue are empty and
 * the EU does not require the BIU to perform a bus cycle, the BIU executes
 * instruction fetch cycles to refill the queue. Note that the 8086 CPU, since
 * it has a 16-bit data bus, can access two instructions object code bytes in a
 * single bus cycle. If the EU issues a request for bus access while the BIU is
 * in the process of an instruction fetch bus cycle, the BIU completes the
 * cycle before honoring the EU's request.
 *
 * @author Shao Yongqing
 * Date: 2017/9/16.
 */
public class Intel8086 extends Cpu implements Intel8086InstructionSet {



    @Override
    protected InstructionUnit createInstructionUnit() {
        return new IU8086();
    }

    @Override
    public AddressUnit createAddressUnit() {
        return new IntelAddressUnit();
    }

    @Override
    protected Instruction fetchInstruction() {
        return instructionUnit.nextInstruction();
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
                getAddressUnit(),
                instructionUnit,
                busInterfaceUnit,
                memoryAccessor
                );
    }

    @Override
    public BusInterfaceUnit createBIU() {
        return new BIU8086(getMemoryManager(), getAddressUnit());
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


    @Override
    public void fetchRawInstructions(){
        busInterfaceUnit.fetchInstructions(getMemoryAccessor(),instructionLocator);
    }

    @Override
    public Instruction decodeInstruction() {
        // Current instruction decoded
        Instruction instruction = instructionUnit.decode(busInterfaceUnit.getInstructionQueue());

        if(instruction!=null) {
            instructionLocator.incOffset(instruction.getLength());
        }

        return instruction;
    }

    public boolean executeInstruction(Instruction instruction){
        // Validate the Instruction
        if(instruction==null){
            // If no instruction available, just return true for next tick
            return true;
        }
        return executionUnit.execute(instruction);
    }

}

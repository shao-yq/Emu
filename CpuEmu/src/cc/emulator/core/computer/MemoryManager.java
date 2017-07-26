package cc.emulator.core.computer;

import cc.emulator.core.cpu.AddressBus;
import cc.emulator.core.cpu.DataBus;
import cc.emulator.core.cpu.DataListener;
import cc.emulator.core.cpu.MemoryAccessor;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author Shao Yongqing
 * Date: 2017/7/25.
 */
public class MemoryManager implements AddressBus{
    /**
     * Memory
     *
     * The 8086 can accommodate up to 1,048,576 bytes of memory in both minimum
     * and maximum mode. This section describes how memory is functionally
     * organized and used.
     *
     * Storage Organization
     *
     * From a storage point of view, the 8086 memory space is organized as an
     * array of 8-bit bytes. Instructions, byte data and word data may be
     * freely stored at any byte address without regard for alignment thereby
     * saving memory space by allowing code to be densely packed in memory.
     * Odd-addressed (unaligned) word variables, however, do not take advantage
     * of the 8086's ability to transfer 16-bits at a time. Instruction
     * alignment does not materially affect the performance of the processor.
     *
     * Following Intel convention, word data always is stored with the most-
     * significant byte in the higher memory location. Most of the time this
     * storage convention is "invisible" to anyone working with the processors;
     * exceptions may occur when monitoring the system bus or when reading
     * memory dumps.
     *
     * A special class of data is stored as doublewords; i.e., two consecutive
     * words. These are called pointers and are used to address data and code
     * that are outside the currently-addressable segments. The lower-addressed
     * word of a pointer contains an offset value, and the higher-addressed
     * word contains a segment base address. Each word is stored conventionally
     * with the higher-addressed byte containing the most significant eight
     * bits of the word.
     *
     * Segmentation
     *
     * 8086 programs "view" the megabyte of memory space as a group of segments
     * that are defined by the application. A segment is a logical unit of
     * memory that may be up to 64k bytes long. Each segment is made up of
     * contiguous memory locations and is an independent, separately-
     * addressable unit. Every segment is assigned (by software) a base
     * address, which is its starting location in the memory space. All
     * segments begin on 16-byte memory boundaries. There are no other
     * restrictions on segment locations; segments may be adjacent, disjoint,
     * partially overlapped, or fully overlapped. A physical memory location
     * may be mapped into (contained in) one or more logical segments.
     *
     * The segment registers point to (contain the vase address values of) the
     * four currently addressable segments. Programs obtain access to code and
     * data in other segments by changing the segment registers to point to the
     * desired segments.
     *
     * Every application will define and use segments differently. The
     * currently addressable segments provide a generous work space: 64k bytes
     * for code, a 64k byte stack and 128k bytes of data storage. Many
     * applications can be written to simply initialize the segments registers
     * and then forget them. Larger applications should be designed with
     * careful consideration given to segment definition.
     *
     * The segmented structure of the 8086 memory space supports modular
     * software design by discouraging huge, monolithic programs. The segments
     * also can be used to advantage in many programming situations. Take, for
     * example, the case of an editor for several on-line terminals. A 64k text
     * buffer (probably an extra segment) could be assigned to each terminal. A
     * single program could maintain all the buffers by simply changing
     * register ES to point to the buffer of the terminal requesting service.
     *
     * Physical Address Generation
     *
     * It is useful to think of every memory location as having two kinds of
     * addresses, physical and logical. A physical address is the 20-bit value
     * that uniquely identifies each byte location in the megabyte memory
     * space. Physical addresses may range from 0H through FFFFFH. All
     * exchanges between the CPU and memory components use this physical
     * address.
     *
     * Programs deal with logical, rather than physical addresses and allow
     * code to be developed without prior knowledge of where the code is to be
     * located in memory and facilitate dynamic management of memory resources.
     * A logical address consists of a segment base value and an offset value.
     * For any given memory location, the segment base value locate the first
     * byte of the containing segment and the offset value is the distance, in
     * bytes, of the target location from the beginning of the segment. Segment
     * base and offset values are unsigned 16-bit quantities; the lowest-
     * addressed byte in a segment has an offset of 0. Many different logical
     * addresses can map to the same physical location.
     *
     * Whenever the BIU accesses memory--to fetch an instruction or to obtain
     * or store a variable--it generates a physical address from a logical
     * address. This is done by shifting the segment base value four bit
     * positions and adding the offset. Note that this addition process
     * provides for modulo 64k addressing (addresses wrap around from the end
     * of a segment to the beginning of the same segment).
     *
     * The BIU obtains the logical address of a memory location from different
     * sources depending on the type of reference that is being made.
     * Instructions always are fetched from the current code segment; IP
     * contains the offset of the target instruction from the beginning of the
     * segment. Stack instructions always operate on the current stack segment;
     * SP contains the offset of the top of the stack. Most variables (memory
     * operands) are assumed to reside in the current data segment, although a
     * program can instruct the BIU to access a variable in one of the other
     * currently addressable segments. The offset of a memory variable is
     * calculated by the EU. This calculation is based on the addressing mode
     * specified in the instruction, the result is called the operand's
     * effective address (EA).
     *
     * In most cases, the BIU's segment assumptions are a convenience to
     * programmers. It is possible, however, for a programmer to explicitly
     * direct the BIU to access a variable in any of the currently addressable
     * segments (the only exception is the destination operand of a string
     * instruction which must be in the extra segment). This is done by
     * preceding an instruction with a segment override prefix. This one-byte
     * machine instruction tells the BIU which segment register to use to
     * access a variable referenced in the following instruction.
     *
     * Dedicated and Reserved Memory Locations
     *
     * Two areas in extreme low and high memory are dedicated to specific
     * processor functions or are reserved by Intel Corporation for use by
     * Intel hardware and software products. The locations are: 0H through 7FH
     * (128 bytes) and FFFF0H through FFFFFH (16 bytes). These areas are used
     * for interrupt and system reset processing; 8086 application systems
     * should not use these areas for any other purpose. Doing so may make
     * these systems incompatible with future Intel products.
     */
    protected final int[]      memory      = new int[0x100000];

    public int[] getMemoryBase(){
        return memory;
    }

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
        for (int i = 0; i < bin.length; i++)
            memory[addr + i] = bin[i] & 0xff;
    }

    public void reset() {
    }

    @Override
    public void putAddress(int addr, DataBus dataBus) {
        switch(dataBus.getMode()) {
            case  MemoryAccessor.READ:
                readMemory( addr,  dataBus);
                break;
            case MemoryAccessor.WRITE:
                writeMemory( addr,  dataBus);
                break;
            default:
                break;
        }
    }

    ArrayList<DataListener> dataListeners =  new ArrayList<DataListener>();

    void notifyDataReady(DataBus dataBus){
        for(DataListener dataListener : dataListeners) {
            dataListener.dataReady(dataBus);
        }
    }

    public void  addDataListener(DataListener l){
        dataListeners.add(l);
    }

    public DataListener removeDataListener(DataListener l){
        if(dataListeners.remove(l))
            return l;
        return null;
    }

    protected void readMemory(int addr, DataBus dataBus) {
        if(dataBus.getMode()== MemoryAccessor.READ) {
            int val = 0;
            switch (dataBus.getDataWidth()) {
                case MemoryAccessor.BYTE4:
                    val |= memory[addr + 3] << 24;
                    val |= memory[addr + 2] << 16;
                case MemoryAccessor.BYTE2:
                    val |= memory[addr + 1] << 8;
                case MemoryAccessor.BYTE1:
                    val |= memory[addr];
                    dataBus.writeData(val);

                    notifyDataReady(dataBus);


                    break;

                case MemoryAccessor.BYTE8:
                    val |= memory[addr];
                    val |= memory[addr + 1] << 8;
                    val |= memory[addr + 2] << 16;
                    val |= memory[addr + 3] << 24;

                    int val2 = memory[addr + 4];
                    val2 |= memory[addr + 5] << 8;
                    val2 |= memory[addr + 6] << 16;
                    val2 |= memory[addr + 7] << 24;
                    dataBus.writeData(val);
                    dataBus.writeData2(val2);
                    // dataBus.receiveData(val | (val2 << 32));
                    notifyDataReady(dataBus);
                    break;
                default:
                    break;
            }
        }
//        if (w == W) {
//            if ((addr & 0b1) == 0b1)
//                clocks += 4;
//            val |= memory[addr + 1] << 8;
//        }
//        return val;
    }

    protected void writeMemory(int addr, DataBus dataBus) {
        if (dataBus.getMode() == MemoryAccessor.WRITE) {
            int val = dataBus.readData();
            switch (dataBus.getDataWidth()) {
                case MemoryAccessor.BYTE8:
                    int val2 = dataBus.readData2();
                    memory[addr+7] = (val2>>24) & 0xFF;
                    memory[addr+6] = (val2>>16) & 0xFF;
                    memory[addr+5] = (val2>>8) & 0xFF;
                    memory[addr+4] = val2 & 0xFF;

                case MemoryAccessor.BYTE4:
                    memory[addr+3] = (val>>24) & 0xFF;
                    memory[addr+2] = (val>>16) & 0xFF;

                case MemoryAccessor.BYTE2:
                    memory[addr+1] = (val>>8) & 0xFF;

                case MemoryAccessor.BYTE1:
                    memory[addr] = val & 0xFF;
                    break;
            }
        }
    }

}

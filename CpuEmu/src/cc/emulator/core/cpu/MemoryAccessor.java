package cc.emulator.core.cpu;

import cc.emulator.core.cpu.bus.AddressBus;
import cc.emulator.core.cpu.bus.DataBus;

/**
 * @author Shao Yongqing
 * Date: 2017/7/26.
 */
public abstract class MemoryAccessor {
    AddressBus addressBus;
    DataBus dataBus;

    DataTemporaryRegister dataTemporaryRegister;


    public DataListener getDataTemporaryRegister() {
        return dataTemporaryRegister;
    }

    /** Instruction operates on byte data. */
    public static final int   BYTE1      = 0b00;
    /** Instruction operates on word (2 bytes) data. */
    public static final int   BYTE2    = 0b01;
    public static final int   BYTE4    = 0b10;
    public static final int   BYTE8    = 0b11;

    public static final int   READ     = 0b0;
    public static final int   WRITE    = 0b1;

    public MemoryAccessor(AddressBus addressBus, DataBus dataBus){
        dataTemporaryRegister = createDataRegister();
        this.dataBus = dataBus;
        this.addressBus = addressBus;
    }


    protected abstract DataTemporaryRegister createDataRegister() ;

    /**
     * Gets the value at the specified address.
     *
     * @param w
     *            word/byte operation
     * @param addr
     *            the address
     * @return the value
     */
    public int getMem(final int w, final int addr) {
        // Set data width, 1, 2, 4, 8 bytes respectively
        dataBus.setDataWidth(w);
        dataBus.setMode(READ);
        // Put the address value to the address bus to fetch data
        addressBus.putAddress(addr, dataBus);

        // Retrieve data from data bus
        int data = dataBus.readData();
        return data;
//        int val = memory[addr];
//        if (w == W) {
//            if ((addr & 0b1) == 0b1)
//                clocks += 4;
//            val |= memory[addr + 1] << 8;
//        }
//        return val;
    }


    /**
     * Sets the value at the specified address.
     *
     * @param w
     *            word/byte operation
     * @param addr
     *            the address
     * @param val
     *            the new value
     */
    public void setMem(final int w, final int addr, final int val) {
        // IBM BIOS and BASIC are ROM.
        if (addr >= 0xf6000)
            return;


        // Set data width, 1, 2, 4, 8 bytes respectively
        dataBus.setDataWidth(w);
        dataBus.setMode(WRITE);
        // Put data to the data bus
        dataBus.writeData(val);
        addressBus.putAddress(addr, dataBus);

//        memory[addr] = val & 0xff;
//        if (w == W) {
//            if ((addr & 0b1) == 0b1)
//                clocks += 4;
//            memory[addr + 1] = val >>> 8 & 0xff;
//        }
    }
}

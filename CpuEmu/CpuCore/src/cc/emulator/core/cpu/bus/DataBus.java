package cc.emulator.core.cpu.bus;

import cc.emulator.core.cpu.Bus;

/**
 * @author Shao Yongqing
 * Date: 2017/7/26.
 */
public interface DataBus extends Bus {
    void setMode(int mode);
    int getMode();

    int getDataWidth();
    void setDataWidth(int w);

    /**
     * Retrieve data from the bus, which is put before
     * @return data in 1, 2, 4 bytes
     */
    int readData();
    void writeData(int v);

    /**
     *
     * @return 4 high bytes data
     */
    int readData2();
    void writeData2(int v);
}

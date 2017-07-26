package cc.emulator.core.cpu;

/**
 * @author Shao Yongqing
 * Date: 2017/7/26.
 */
public interface DataBus {
    void setMode(int mode);
    int getMode();

    int getDataWidth();
    void setDataWidth(int w);

//    void receiveData(int v);
//    void receiveData(long v);

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

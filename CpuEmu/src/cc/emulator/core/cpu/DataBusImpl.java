package cc.emulator.core.cpu;

/**
 * @author Shao Yongqing
 * Date: 2017/7/26.
 */
public abstract class DataBusImpl implements DataBus{
    int mode;
    @Override
    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public int getMode() {
        return mode;
    }

    int dataWidth=0;
    @Override
    public int getDataWidth() {
        return dataWidth;
    }

    @Override
    public void setDataWidth(int w) {
        dataWidth = w;
    }

    int data;
    int data2;



    @Override
    public int readData() {
        return data;
    }

    @Override
    public void writeData(int v) {
        data = v;
    }

    @Override
    public int readData2() {
        return data2;
    }

    @Override
    public void writeData2(int v) {
        data2 = v;
    }

}

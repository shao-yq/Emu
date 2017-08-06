package cc.emulator.core.cpu.register;

import cc.emulator.core.cpu.Register;

/**
 * @author Shao Yongqing
 * Date: 2017/7/27.
 */
public  abstract class RegisterImpl implements Register {
    protected int data, data2;
    protected String name;
    protected int dataWidth;

    public RegisterImpl(String name, int dataWidth) {
        this.name = name;
        this.dataWidth = dataWidth;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getData() {
        return data;
    }

    @Override
    public long getDataLong() {
        return (data2<<32)|data;
    }

    @Override
    public void setData(int v) {
        data = v;
    }
    @Override
    public void setData(long v) {
        data = (int) (v&0xFFFFFFFF);
        data2 = (int) ((v>>32)&0xFFFFFFFF);
    }

    public int getDataWidth() {
        return dataWidth;
    }

    @Override
    public void reset() {
        data = 0;
        data2 = 0;
    }

}

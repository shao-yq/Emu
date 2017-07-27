package cc.emulator.core.cpu.register;

import cc.emulator.core.cpu.Register;

/**
 * @author Shao Yongqing
 * Date: 2017/7/27.
 */
public  abstract class RegisterImpl implements Register {
    protected int data;
    private String name;
    private int dataWidth;

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
    public void setData(int v) {
        data = v;
    }
    public int getDataWidth() {
        return dataWidth;
    }
}

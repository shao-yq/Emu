package cc.emulator.core.cpu.register;

import cc.emulator.core.cpu.Register;

/**
 * @author Shao Yongqing
 * Date: 2017/7/27.
 */
public abstract class StatusRegister implements Register {
    /**
     * Flags
     *
     * The 8086 has six 1-bit status flags that the EU posts to reflect certain
     * properties of the result of an arithmetic or logic operation. A group of
     * instructions is available that allow a program to alter its execution
     * depending of the state of these flags, that is, on the result of a prior
     * operation. Different instructions affect the status flags differently.
     */
    protected int                flags;

    public boolean hasFlag(int flag) {
        return (flags & flag) == flag;
    }

    @Override
    public int getData() {
        return flags;
    }

    @Override
    public void setData(int data) {
        flags =data;
    }

    public void setFlag( int flag){
        flags |= flag;
    }

    public void clearFlag( int flag){
        flags &= ~flag;
    }


}

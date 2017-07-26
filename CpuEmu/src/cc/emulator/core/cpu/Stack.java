package cc.emulator.core.cpu;

import cc.emulator.core.cpu.MemoryAccessor;

/**
 * @author Shao Yongqing
 * Date: 2017/7/26.
 */
public class Stack {
    MemoryAccessor memoryAccessor;
    AddressGenerator addressGenerator;

    public Stack() {
        sp = 0;
        ss = 0;
    }

    public int getSp() {
        return sp;
    }

    public int getSs() {
        return ss;
    }

    public void setSs(int ss) {
        this.ss = ss;
    }

    public void setSp(int sp) {
        this.sp = sp;
    }

    private int                sp;
    private int                ss;

    /**
     * Pushes a value to the top of the stack.
     *
     * @param val
     *            the value
     */
    public void push(final int val) {
        decSp();

        int address = addressGenerator.getAddr(ss, sp);
        memoryAccessor.setMem(MemoryAccessor.BYTE2, address, val);
    }

    public void setMemoryAccessor(MemoryAccessor memoryAccessor) {
        this.memoryAccessor = memoryAccessor;
    }

    public void setAddressGenerator(AddressGenerator addressGenerator) {
        this.addressGenerator = addressGenerator;
    }

    protected void decSp(){
        sp = sp - 2 & 0xffff;
    }

    /**
     * Pops a value at the top of the stack.
     *
     * @return the value
     */
    public int pop() {
        int address = addressGenerator.getAddr(ss, sp);
        final int val = memoryAccessor.getMem(MemoryAccessor.BYTE2, address);
        //final int val = getMem(W, getAddr(ss, sp));

        incSp(); // sp = sp + 2 & 0xffff;

        return val;
    }

    void incSp() {
        sp = sp + 2 & 0xffff;
    }

}

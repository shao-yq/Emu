package cc.emulator.core.cpu;

import cc.emulator.core.cpu.register.PointerIndexer;
import cc.emulator.core.cpu.register.SegmentRegister;

/**
 * @author Shao Yongqing
 * Date: 2017/7/26.
 */
public abstract class Stack {
    MemoryAccessor memoryAccessor;
    AddressGenerator addressGenerator;

    protected RegisteredMemoryLocator memoryLocator;
    public Stack(SegmentRegister ss,PointerIndexer sp) {
//        sp = 0;
//        ss = 0;
        //sp = createPointerIndexer(); // new PointerIndexer("SP",2);
        //ss = createSegmentRegister(); // new SegmentRegister("SS",2);
        memoryLocator =  createMemoryLocator(ss,sp); // new  RegisteredMemoryLocator(ss, sp);
    }

    protected abstract RegisteredMemoryLocator createMemoryLocator(SegmentRegister ss,PointerIndexer sp);

//    protected abstract PointerIndexer createPointerIndexer();
//    protected abstract SegmentRegister createSegmentRegister();

    public int getSp() {
        //return sp.getData();
        return memoryLocator.getOffset();
    }

    public int getSs() {
        //return ss.getData();
        return memoryLocator.getBase();
    }

    public void setSs(int ss) {
        //this.ss.setData(ss);
        memoryLocator.setBase(ss);
    }

    public void setSp(int sp) {
        //this.sp.setData(sp);
        memoryLocator.setOffset(sp);
    }

    public void addSp(int delta) {
        //int v = sp.getData();
        //this.sp.setData(v+delta);
        memoryLocator.incOffset(delta);
    }

    //protected int                sp;
    //protected int                ss;

    //protected SegmentRegister ss;
    //protected PointerIndexer sp;

    /**
     * Pushes a value to the top of the stack.
     *
     * @param val
     *            the value
     */
    public void push(final int val) {
        decSp();

        int address = getTopAddress();
        memoryAccessor.setMem(MemoryAccessor.BYTE2, address, val);
    }

    private int getTopAddress() {
        return addressGenerator.getAddr(memoryLocator.getBase(), memoryLocator.getOffset()); // addressGenerator.getAddr(ss.getData(), sp.getData());
    }


    public void setMemoryAccessor(MemoryAccessor memoryAccessor) {
        this.memoryAccessor = memoryAccessor;
    }

    public void setAddressGenerator(AddressGenerator addressGenerator) {
        this.addressGenerator = addressGenerator;
    }


    /**
     * Pops a value at the top of the stack.
     *
     * @return the value
     */
    public int pop() {
        int address = getTopAddress();      // addressGenerator.getAddr(ss.getData(), sp.getData());
        final int val = memoryAccessor.getMem(MemoryAccessor.BYTE2, address);
        //final int val = getMem(W, getAddr(ss, sp));

        incSp(); // sp = sp + 2 & 0xffff;

        return val;
    }

    protected abstract void decSp();

    protected abstract void incSp();

}

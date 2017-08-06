package cc.emulator.arch.x86.intel;

import cc.emulator.core.cpu.RegisteredMemoryLocator;
import cc.emulator.core.cpu.Stack;
import cc.emulator.core.cpu.register.PointerIndexer;
import cc.emulator.core.cpu.register.SegmentRegister;

/**
 * @author Shao Yongqing
 * Date: 2017/7/26.
 */
public class IntelStack extends Stack {
    public IntelStack(SegmentRegister ss, PointerIndexer sp) {
        super(ss, sp);
    }

    protected void decSp(){
        memoryLocator.decOffset();
        //sp.decrease();

        //sp = sp - 2 & 0xffff;

//        int v = sp.getData();
//        v = v - 2 & 0xffff;
//        sp.setData(v);

    }
    protected void incSp() {
        memoryLocator.incOffset();

        //sp.increase();

        //sp = sp + 2 & 0xffff;

//        int v = sp.getData();
//        v = v + 2 & 0xffff;
//        sp.setData(v);

    }
//    protected PointerIndexer createPointerIndexer(){
//        return new PointerIndexer("SP",2);
//
//    }
//    protected  SegmentRegister createSegmentRegister(){
//        return new SegmentRegister("SS",2);
//    }

    protected RegisteredMemoryLocator createMemoryLocator(SegmentRegister ss,PointerIndexer sp){
        return new  RegisteredMemoryLocator(ss, sp);
    }
}

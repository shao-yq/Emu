package cc.emulator.core.cpu.intel;

import cc.emulator.core.cpu.Stack;
import cc.emulator.core.cpu.register.PointerIndexer;
import cc.emulator.core.cpu.register.SegmentRegister;

/**
 * @author Shao Yongqing
 * Date: 2017/7/26.
 */
public class IntelStack extends Stack {
    protected void decSp(){
        sp.decrease();

        //sp = sp - 2 & 0xffff;

//        int v = sp.getData();
//        v = v - 2 & 0xffff;
//        sp.setData(v);

    }
    protected void incSp() {
        sp.increase();

        //sp = sp + 2 & 0xffff;

//        int v = sp.getData();
//        v = v + 2 & 0xffff;
//        sp.setData(v);

    }
    protected PointerIndexer createPointerIndexer(){
        return new PointerIndexer("SP",2);

    }
    protected  SegmentRegister createSegmentRegister(){
        return new SegmentRegister("SS",2);
    }
}

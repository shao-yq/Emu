package cc.emulator.core.cpu.intel;

import cc.emulator.core.cpu.Stack;

/**
 * @author Shao Yongqing
 * Date: 2017/7/26.
 */
public class IntelStack extends Stack {
    protected void decSp(){
        sp = sp - 2 & 0xffff;
    }
    protected void incSp() {
        sp = sp + 2 & 0xffff;
    }

}

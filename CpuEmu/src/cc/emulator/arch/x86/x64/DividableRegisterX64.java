package cc.emulator.arch.x86.x64;

import cc.emulator.arch.x86.i386.DividableRegister386;

/**
 * @author  Shao Bofeng
 * Date: 2017/8/13
 */
public class DividableRegisterX64 extends DividableRegister386 {
    protected DividableRegisterX64(String name, int dataWidth) {
        super(name, dataWidth);
    }

    public DividableRegisterX64(String name) {
        this(name,8);
    }
    public long getRX(){
        return (data2<<32)|data;
    }

    public void setRX(long rx){
        data= (int) (rx&0xFFFFFFFF);
        data2= (int) (rx>>32);
    }

}

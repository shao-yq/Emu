package cc.emulator.x86.i386;

import cc.emulator.x86.i8086.DividableRegister8086;

/**
 * @author  Shao Bofeng
 * Date: 2017/8/13
 */
public class DividableRegister386 extends DividableRegister8086 {
    protected DividableRegister386(String name, int dataWidth) {
        super(name, dataWidth);
    }

    public DividableRegister386(String name) {
        this(name,4);
    }

    public int getEX(){
        return data;
    }

    public void setEX(int ex){
        data=ex;
    }
}

package cc.emulator.arch.x86.i8086;

import cc.emulator.core.cpu.register.DividableRegister;

/**
 * @author Shao Yongqing
 * Date: 2017/7/28.
 */
public class DividableRegister8086 extends DividableRegister {
    protected DividableRegister8086(String name,int dataWidth) {
        super(name, dataWidth);
    }
    public DividableRegister8086(String name) {
        this(name, 2);
    }

    @Override
    public int getL(){
        return data&0xFF;
    }

    @Override
    public int getH(){
        return (data>>8)&0xFF;
    }

    @Override
    public void setH(int v){
        data = (data & 0xFFFF00FF) | (v << 8);
    }
    @Override
    public void setL(int v){
        data = (data & 0xFFFFFF00) | v;
    }

    public int getX(){
        return getData()&0xFFFF;
    }

    public void setX(int v){
        data = (data&0xFFFF0000)|v;
    }
}

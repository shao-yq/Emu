package cc.emulator.core.cpu.register;

import cc.emulator.core.cpu.register.RegisterImpl;

/**
 * @author Shao Yongqing
 * Date: 2017/7/27.
 */
public  class GeneralRegister extends RegisterImpl {
    public GeneralRegister(String name, int dataWidth) {
        super(name, dataWidth);
    }

    public int getL(){
        switch(getDataWidth()){
            case 2:
                return data&0xFF;
            case 4:
                return data&0xFFFF;
            case 8:
                return data;
            default:
                break;
        }
        return data;
    }

    public int getH(){
        switch(getDataWidth()){
            case 2:
                return (data>>8)&0xFF;
            case 4:
                return (data>>16)&0xFFFF;
            case 8:
                return data2;
            default:
                break;
        }
        return data;
    }


    public void setH(int v){
        switch(getDataWidth()) {
            case 2:
                data = (data & 0xFF) | (v << 8);
                break;
            case 4:
                data = (data & 0xFFFF) | (v << 16);
            case 8:
                data2 = v;
                break;
        }
    }
    public void setL(int v){
        switch(getDataWidth()) {
            case 2:
                data = (data & 0xFF00) | v;
                break;
            case 4:
                data = (data & 0xFFFF0000) | v;
            case 8:
                data = v;
                break;
        }
    }

    public void incH(int delta){
        int v = getH();
        v += delta;
        setH(v);
    }

    public void incL(int delta){
        int v = getL();
        v += delta;
        setL(v);
    }
}

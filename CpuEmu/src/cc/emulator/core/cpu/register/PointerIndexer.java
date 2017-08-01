package cc.emulator.core.cpu.register;

/**
 * @author Shao Yongqing
 * Date: 2017/7/27.
 */
public class PointerIndexer extends GeneralRegister {
    public PointerIndexer(String name, int dataWidth) {
        super(name,dataWidth);
    }
    public void decrease(){
        decrease(dataWidth);
    }
    public void decrease(int delta){
        data = data - delta;
        //data = data - delta & 0xffff;
    }
    public void increase(){
        increase(dataWidth);
    }
    public void increase(int delta){
        data = data + delta;
        //data = data + delta & 0xffff;

    }

}

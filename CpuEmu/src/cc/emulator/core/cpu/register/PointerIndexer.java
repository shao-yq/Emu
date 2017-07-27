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
        data -= getDataWidth();
    }

    public void increase(){
        data += getDataWidth();
    }


}

package cc.emulator.core.cpu.register;

/**
 * @author Shao Yongqing
 * Date: 2017/7/27.
 */
public  class GeneralRegister extends RegisterImpl {
    public GeneralRegister(String name, int dataWidth) {
        super(name, dataWidth);
    }

    public void inc(int df, int w) {
        //si = si + (getFlag(DF) ? -1 : 1) * (1 + w) & 0xffff;
        data = (data + df * (1 + w)) & 0xffff;
    }
}

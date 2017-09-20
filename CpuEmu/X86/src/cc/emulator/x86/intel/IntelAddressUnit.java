package cc.emulator.x86.intel;

import cc.emulator.core.cpu.AddressUnit;

/**
 * @author Shao Yongqing
 * Date: 2017/7/26.
 */
public class IntelAddressUnit implements AddressUnit {
    protected int seg;

    @Override
    public int getAddr(int seg, int off) {
        return (seg << 4) + off;
    }

    @Override
    public int getAddr(int off) {
        // With default segment selector
        return getAddr( seg,  off);
    }

    @Override
    public void reset() {
        seg=0;
    }

}

package cc.emulator.core.cpu.intel;

import cc.emulator.core.cpu.AddressGenerator;

/**
 * @author Shao Yongqing
 * Date: 2017/7/26.
 */
public class IntelAddressGenerator implements AddressGenerator {
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

}

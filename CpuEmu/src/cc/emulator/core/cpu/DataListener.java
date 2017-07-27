package cc.emulator.core.cpu;

import cc.emulator.core.cpu.bus.DataBus;

/**
 * @author Shao Yongqing
 * Date: 2017/7/26.
 */
public interface DataListener {
    void dataReady(DataBus dataBus);
}

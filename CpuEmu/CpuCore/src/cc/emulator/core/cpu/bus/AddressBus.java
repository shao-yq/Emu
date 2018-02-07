package cc.emulator.core.cpu.bus;

import cc.emulator.core.cpu.Bus;

/**
 * @author Shao Yongqing
 * Date: 2017/7/26.
 */
public interface AddressBus extends Bus {
     void putAddress(int address, DataBus dataBus);

}

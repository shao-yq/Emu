package cc.emulator.arch.x86.intel;

import cc.emulator.core.cpu.bus.AddressBus;
import cc.emulator.core.cpu.bus.DataBus;
import cc.emulator.core.cpu.DataTemporaryRegister;
import cc.emulator.core.cpu.MemoryAccessor;

/**
 * @author Shao Yongqing
 * Date: 2017/7/26.
 */
public class IntelMemoryAccessor extends MemoryAccessor {

    public IntelMemoryAccessor(AddressBus addressBus, DataBus dataBus) {
        super(addressBus, dataBus);
    }

    @Override
    protected DataTemporaryRegister createDataRegister() {
        return new DataTemporaryRegister("DR", 2);
    }
}

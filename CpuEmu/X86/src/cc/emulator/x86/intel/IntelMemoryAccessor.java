package cc.emulator.x86.intel;

import cc.emulator.core.MemoryManager;
import cc.emulator.core.cpu.bus.AddressBus;
import cc.emulator.core.cpu.bus.DataBus;
import cc.emulator.core.cpu.DataTemporaryRegister;
import cc.emulator.core.cpu.MemoryAccessor;

/**
 * @author Shao Yongqing
 * Date: 2017/7/26.
 */
public class IntelMemoryAccessor extends MemoryAccessor {

    private  MemoryManager memoryManager;

    public IntelMemoryAccessor(MemoryManager memoryManager, DataBus dataBus) {
        super(memoryManager, dataBus);
        this.memoryManager = memoryManager;
    }

    @Override
    protected DataTemporaryRegister createDataRegister() {
        return new DataTemporaryRegister("DR", 2);
    }

    @Override
    public boolean isValidAddress(int addr) {
        return memoryManager.isValidAddress(addr);
    }
}

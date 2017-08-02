package cc.emulator.core.computer;

import cc.emulator.core.cpu.Cpu;

public abstract class AbstractMainBoard implements MainBoard{
    private MemoryManager memory;

    @Override
    public void load(int base, String biosResource) throws Exception{
        memory.load(base, biosResource);
    }
    public void loadBootloader(int base, String res) throws Exception{
        memory.load(base, res);
    }
    @Override
    public Cpu getCpu() {
        return cpu;
    }

    @Override
    public void run() {
        cpu.run();
    }

    private Cpu cpu;

    @Override
    public void reset() {
        memory =  createMemoryManager();
        memory.reset();
        cpu =  createCpu(memory);
        cpu.reset();
        //cpu.setMemory(memory.getMemoryBase());

        memory.addDataListener(cpu.getMemoryAccessor().getDataTemporaryRegister());
    }
    protected abstract Cpu createCpu(MemoryManager mm);
    protected abstract MemoryManager createMemoryManager();

}

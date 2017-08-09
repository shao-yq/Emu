package cc.emulator.computer;

import cc.emulator.arch.x86.i8086.Intel8086;
import cc.emulator.core.computer.AbstractMainBoard;
import cc.emulator.core.computer.MemoryManager;
import cc.emulator.core.cpu.Cpu;

public class PC5150MainBoard extends AbstractMainBoard {


    @Override
    public void reset() {
        super.reset();
    }

    protected Cpu createCpu(MemoryManager mm){
        Intel8086 cpu = new Intel8086(mm);

        return cpu;
    }

    protected MemoryManager createMemoryManager(){
        return new  MemoryManager();
    }

}

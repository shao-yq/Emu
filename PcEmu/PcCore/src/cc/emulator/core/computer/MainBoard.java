package cc.emulator.core.computer;

import cc.emulator.core.MemoryManager;
import cc.emulator.core.cpu.Cpu;

public interface MainBoard {
    void reset();
    Cpu getCpu(int index);
    // Default the first CPU
    Cpu getCpu();

    void run();

    int loadBios(int base, String biosResource)throws Exception;

    int loadBootloader(int base, String res)throws Exception;

    MemoryManager getMemoryManager();

    int load(int base, String resource)throws Exception;

    ProgramMemoryInfo getProgramMemoryInfo(String programName);
}

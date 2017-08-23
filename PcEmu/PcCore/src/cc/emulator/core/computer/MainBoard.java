package cc.emulator.core.computer;

import cc.emulator.core.cpu.Cpu;

public interface MainBoard {
    void reset();
    Cpu getCpu();

    void run();

    void load(int base, String biosResource)throws Exception;

    void loadBootloader(int base, String res)throws Exception;
}

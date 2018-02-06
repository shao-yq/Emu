package cc.emulator.core.computer;

import cc.emulator.core.cpu.Cpu;

/**
 * @author Shao Yongqing
 * Date: 2017/7/25.
 */
public interface Computer {
    MainBoard getMainBoard();
    KeyBoard getKeyBoard();
    VideoAdapter getVideoAdapter();
    AudioAdapter getAudioAdapter();
    NetworkAdapter getNetworkAdapter();
    Display getDisplay();
    Box getBox();
    StorageDriver[] getStorageDrivers();
    abstract void powerUp();
    abstract void powerDown();
    abstract void reset();
    abstract void start();

    ProgramMemoryInfo getProgramMemoryInfo(String bios);

    Cpu getCpu();
    Cpu getCpu(int index);
}

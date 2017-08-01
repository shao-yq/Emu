package cc.emulator.core.computer;

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
}

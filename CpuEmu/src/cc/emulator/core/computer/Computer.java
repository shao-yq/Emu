package cc.emulator.core.computer;

/**
 * @author Shao Yongqing
 * Date: 2017/7/25.
 */
public interface Computer {
//    MainBoard mainBoard;
//    KeyBoard keyBoard;
//    VideoAdapter videoAdapter;
//    AudioAdapter audioAdapter;
//    NetworkAdapter networkAdapter;
//    Display display;
//    Box box;
//    StorageDriver storageDrivers[];
    abstract void powerUp();
    abstract void powerDown();
    abstract void reset();
}

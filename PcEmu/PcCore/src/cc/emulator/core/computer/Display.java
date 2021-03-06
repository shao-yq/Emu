package cc.emulator.core.computer;

/**
 * @author Shao Yongqing
 * Date: 2017/7/27.
 */
public interface Display {
    int getScreenColumn();

    int getScreenRow();

    int getCursorAttribute();

    int getCursorLocation();

    void run();
}

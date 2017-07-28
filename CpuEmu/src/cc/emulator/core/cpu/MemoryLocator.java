package cc.emulator.core.cpu;

/**
 * @author Shao Yongqing
 * Date: 2017/7/28.
 */
public interface MemoryLocator {
    int getBase();
    void setBase(int base);

    int getOffset();
    void setOffset(int offset);

    void incOffset();
    void decOffset();

    void incOffset(int delt);
    void decOffset(int delta);
}

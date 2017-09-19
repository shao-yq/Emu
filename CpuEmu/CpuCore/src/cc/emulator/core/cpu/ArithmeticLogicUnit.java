package cc.emulator.core.cpu;

/**
 * @author Shao Yongqing
 * Date: 2017/7/27.
 */
public interface ArithmeticLogicUnit {
    void reset();
    void logic(final int w, final int res);
    int inc(final int w, final int dst);
    int dec(final int w, final int dst);
    int sbb(final int w, final int dst, final int src);
    int sub(final int w, final int dst, final int src);
    int add(final int w, final int dst, final int src);
    int adc(final int w, final int dst, final int src);

    int and(int w, int dst, int src);

    int or(int w, int dst, int src);

    int xor(int w, int dst, int src);
}

package cc.emulator.core.cpu;

/**
 * @author Shao Yongqing
 * Date: 2017/7/27.
 */
public interface Register {
    String getName();
    int getData();
    void setData(int data);

    int getDataWidth();
}

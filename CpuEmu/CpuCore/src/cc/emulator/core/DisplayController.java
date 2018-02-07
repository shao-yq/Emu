package cc.emulator.core;

/**
 * @author Shao Yongqing
 * Date: 2017/7/27.
 */
public interface DisplayController extends Peripheral{
    /**
     * Returns the value of the register with the specified index.
     *
     * @param index
     *            the index
     * @return the value
     */
    public int getRegister(final int index);
}

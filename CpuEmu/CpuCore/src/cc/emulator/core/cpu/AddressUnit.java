package cc.emulator.core.cpu;

/**
 * @author Shao Yongqing
 * Date: 2017/7/26.
 */
public interface AddressUnit {
    /**
     * Gets the absolute address from a segment/selector and an offset.
     *
     * @param seg
     *            the segment
     * @param off
     *            the offset
     * @return the value
     */
    public int getAddr(final int seg, final int off);

    /**
     * Gets the absolute address from a default segment/selector and an offset.
     *
     * @param off
     *            the offset
     * @return the value
     */
    public int getAddr(final int off);

    void reset();
}

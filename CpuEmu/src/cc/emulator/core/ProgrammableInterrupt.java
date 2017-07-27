package cc.emulator.core;

/**
 * @author Shao Yongqing
 * Date: 2017/7/27.
 */
public interface ProgrammableInterrupt extends Peripheral{
    /**
     * Call an interruption request on the specified line.
     *
     * @param line
     *            the line
     */
    void callIRQ(final int line);

    /**
     * Returns if an interrupt request is waiting to be serviced.
     *
     * @return true if there is one, false otherwise
     */
    boolean hasInt();

    /**
     * Returns the type of the interrupt request waiting to be serviced.
     *
     * @return the interrupt-type
     */
    int nextInt();
}

package cc.emulator.core.cpu;

/**
 * @author Shao Yongqing
 * Date: 2017/7/27.
 */
public interface Instruction {
    /**
     * The operation code value
     * @return op code
     */
    int getOpCode();

    /**
     * Fetch the operand of current operation
     * @param index the operand index
     * @return the value of the specified operand
     */
    int getOperand(int index);
    //int getOperand2();
    //int getOperand3();
    //int getOperand4();

    int getClocks();

    int getImmediate();

    /**
     * Instruction length in bytes;
     * @return Instruction length in bytes;
     */
    int getLength();

    int getAddress();

    public Object clone();
    public boolean hasOpcode(int[] queue, int startIndex);
}

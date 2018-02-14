package cc.emulator.core.cpu;

/**
 * @author Shao Yongqing
 * Date: 2017/7/27.
 */
public interface Instruction {
    /**
     * The data transfer instructions move data between memory and the general-purpose and
     * segment registers, and perform operations such as conditional moves, stack access, and data
     * conversion.
     */
    int DATA_TRANSFER     =   1;

    /**
     * The binary arithmetic instructions perform basic integer computions on operands in memory
     * or the general-purpose registers.
     */
    int BINARY_ARITHMETIC =   2;

    /**
     * The decimal arithmetic instructions perform decimal arithmetic on binary coded decimal (BCD) data.
     */
    int DECIMAL_ARITHMETIC =  3;

    /**
     * The logical instructions perform basic logical operations on their operands
     */
    int BITWISE_LOGIC       =   4;

    /**
     * The shift and rotate instructions shift and rotate the bits in their operands
     */
    int SHIFT_ROTATE       =   5;

    /**
     * The bit instructions test and modify individual bits in operands. The byte instructions set the
     * value of a byte operand to indicate the status of flags in the %eflags register.
     */
    int BIT_BYTE = 6;

    /**
     * The control transfer instructions control the flow of program execution.
     */
    int CONTROL_TRANSFER  =   7;

    /**
     * String Instructions
     * The string instructions operate on strings of bytes. Operations include storing strings in
     * memory, loading strings from memory, comparing strings, and scanning strings for substrings.
     */
    int STRING_MANIPULATION = 8;

    /**
     * I/O Instructions
     * The input/output instructions transfer data between the processor's I/O ports, registers, and
     * memory.
     */
    int INPUT_OUTPUT = 9;

    /**
     * Flag Control (EFLAG) Instructions
     * The status flag control instructions operate on the bits in the %eflags register.
     */
    int FLAG_CONTROL = 10;

    /**
     * Segment Register Instructions
     * The segment register instructions load far pointers (segment addresses) into the segment registers.
     */
    int SEGMENT_REGISTER = 11;

    /**
     * Miscellaneous Instructions
     * The instructions documented in this section provide a number of useful functions.
     */
    int MISCELLANEOUS = 12;

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

    public  void decodeMe(int[] raw, int startIndex);

    String toAsm();

    String toBinary();

    String toHexadecimal();

    String getMnemonic();
}

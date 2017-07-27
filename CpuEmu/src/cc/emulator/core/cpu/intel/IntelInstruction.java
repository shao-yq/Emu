package cc.emulator.core.cpu.intel;

/**
 * @author Shao Yongqing
 * Date: 2017/7/27.
 */
public class IntelInstruction implements cc.emulator.core.cpu.Instruction{
        /*
     * Typical 8086 Machine IntelInstruction Format
     *
     * |     BYTE 1     |     BYTE 2      |     BYTE 3    |     BYTE 4     |  BYTE 5  |  BYTE 6   |
     * | OPCODE | D | W | MOD | REG | R/M | LOW DISP/DATA | HIGH DISP/DATA | LOW DATA | HIGH DATA |
     */
    /** Operation (IntelInstruction) code */
    public int                op;
    /** Direction is to register/Direction is from register */
    public int                d;
    /** Word/Byte operation */
    public int                w;
    /** Register mode/Memory mode with displacement length */
    public int                mod;
    /** Register operand/Extension of opcode */
    public int                reg;
    /** Register operand/Registers to use in EA calculation */
    public int                rm;


}

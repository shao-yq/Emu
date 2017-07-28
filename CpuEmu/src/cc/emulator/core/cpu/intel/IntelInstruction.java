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
     *
     *   opcodeDW    MOREGR/M    LOW DISP    HIGHDISP    LOW DATA     HIGHDATA
     */

    /** Operation (Instruction) code , 6 bits: opcodeDW*/
    public int                op;

    /**
     * Direction is to register/Direction is from register. 1 bit
     *  0   from register
     *  1   to register
     */
    public int                d;

    /**
     * Word/Byte operation. 1 bit
     *  0  byte   (1 byte )
     *  1  word   (2-bytes)
     * */
    public int                w;

    /**
     * Register mode/Memory mode with displacement length
     *  CODE        Explanation
     *   00         Memory Mode, no displacement follows (except whe R/M=110, 16-bit displacement follows)
     *   01         Memory Mode,  8-bit displacement follows
     *   10         Memory Mode, 16-bit displacement follows
     *   11         Register Mode (no displacement)
     */
    public int                mod;

    /**
     * Register operand/Extension of opcode, 3 bits
     *
     *  REG     W=0     W=1
     *
     *  000     AL      AX
     *  001     CL      CX
     *  010     DL      DX
     *  011     BL      BX
     *
     *  100     AH      SP
     *  101     CH      BP
     *  110     DH      SI
     *  111     BH      DI
     */
    public int                reg;

    /**
     * Register operand/Registers to use in EA calculation
     *          MOD=11      |            EFFECTIVE ADDRESS CALCULATION
     *   --------------------------------------------------------------------------------------------
     *    R/M   W=0     W=1 |   R/M     MOD=00              MOD=01              MOD=10
     *   --------------------------------------------------------------------------------------------
     *    000   AL      AX  |   000     (BX)+(SI)           (BX)+(SI)+D8        (BX)+(SI)+D16
     *    001   CL      CX  |   001     (BX)+(DI)           (BX)+(DI)+D8        (BX)+(DI)+D16
     *    010   DL      DX  |   010     (BP)+(SI)           (BP)+(SI)+D8        (BP)+(SI)+D16
     *    011   BL      BX  |   011     (BP)+(DI)           (BP)+(DI)+D8        (BP)+(DI)+D16
     *
     *    100   AH      SP  |   100     (SI)                (SI)+D8             (SI)+D16
     *    101   CH      BP  |   101     (DI)                (DI)+D8             (DI)+D16
     *    110   DH      SI  |   110     DIRECT ADDRESS      (BP)+D8             (BP)+D16
     *    111   BH      DI  |   111     (BX)                (BX)+D8             (BX)+D16
     *   --------------------------------------------------------------------------------------------
     */
    public int                rm;

    public int  disp;

    public int data;


    @Override
    public int getOpCode() {
        return op;
    }

    @Override
    public int getOperand(int index) {
        return 0;
    }
}

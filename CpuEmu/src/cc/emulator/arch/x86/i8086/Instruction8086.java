package cc.emulator.arch.x86.i8086;

import cc.emulator.arch.x86.intel.IntelInstruction;

/**
 * @author Shao Yongqing
 * Date: 2017/8/9.
 */
public class Instruction8086 extends IntelInstruction {

    /*
     * Typical 8086 Machine IntelInstruction Format
     *
     * |     BYTE 1     |     BYTE 2      |     BYTE 3    |     BYTE 4     |  BYTE 5  |  BYTE 6   |
     * | OPCODE | D | W | MOD | REG | R/M | LOW DISP/DATA | HIGH DISP/DATA | LOW DATA | HIGH DATA |
     *
     *   opcodeDW    MOREGR/M    LOW DISP    HIGHDISP    LOW DATA     HIGHDATA
     */

}

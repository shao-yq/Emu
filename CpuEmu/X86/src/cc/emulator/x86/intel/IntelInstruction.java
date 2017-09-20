package cc.emulator.x86.intel;

import cc.emulator.core.cpu.AbstractInstruction;
import cc.emulator.core.cpu.Instruction;
import cc.emulator.x86.i8086.Intel8086InstructionSet;

/**
 *
 *  Intelx86  Instruction Format
 *
 * |-------------------------------------------------------------------------------------------------|
 * |Instruction Prefixes| Opcode         | ModR/M      |  SIB        | Displacement  |   Immediate   |
 * |-------------------------------------------------------------------------------------------------|
 * |Up to four          | 1-,2-,or 3-byte| 1 byte      | 1 byte      | Address       |  Immediate    |
 * |prefixes of         | opcode         |(if required)|(if required)| displacement  |  data of      |
 * |1 byte each         |                |             |             | of 1, 2, or 4 |  1, 2, or 4   |
 * |(optional)          |                |             |             | bytes or none |  bytes or none|
 * |-------------------------------------------------------------------------------------------------|
 *                                            /               \
 *                                 -------------------    --------------------
 *                                 |7 6| 5 4 3| 2 1 0|    |7  6 | 5 4 3| 2 1 0|
 *                                 -------------------    -------------------
 *                                 |Mod| Reg/ | R/M  |    |Scale| index| Base |
 *                                 |   |Opcode|      |    ---------------------
 *                                 -------------------
 *
 *
 * @author Shao Yongqing
 * Date: 2017/7/27.
 */
public abstract class IntelInstruction extends AbstractInstruction implements  Intel8086InstructionSet, Cloneable {

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

    @Override
    public int getOpCode() {
        return op;
    }

    @Override
    public int getOperand(int index) {
        return 0;
    }

    @Override
    public int getImmediate() {
        return immediate;
    }

    public void setImmediate(int immediate) {
        this.immediate = immediate;
    }

    public int immediate = 0;

//    @Override
//    public void execute(ExecutionUnit  executionUnit, Stack stack) {
//        // Dummy, here, no op
//    }
//
//    public void execute(ExecutionUnit  executionUnit) {
//        // Dummy, here, no op
//        execute(executionUnit, null);
//    }

    protected int rawData[] = null;
    protected int prefixes[] = null;
    protected int prefixCount=0;
    public boolean hasPrefix(){
        return prefixCount>0;
    }
    public int getPrefixCount(){
        return prefixCount;
    }
    public int getPrefix(int ind){
        return prefixes[ind];
    }

    public abstract void decodeMe(int[] raw, int startIndex);



    protected void copyOthers(Instruction instruction) {
        IntelInstruction intelInstruction = (IntelInstruction) instruction;
        if(prefixes!=null) {
            intelInstruction.prefixes = new int[prefixes.length];
            for(int i=0; i<prefixes.length; i++){
                intelInstruction.prefixes[i] = prefixes[i];
            }
        }
        if(rawData!=null) {
            intelInstruction.rawData = new int[rawData.length];
            for(int i=0; i<rawData.length; i++){
                intelInstruction.rawData[i] = rawData[i];
            }
        }
    }

    public abstract boolean hasOpcode(int[] queue, int startIndex);
}

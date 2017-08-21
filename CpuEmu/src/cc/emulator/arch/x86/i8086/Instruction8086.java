package cc.emulator.arch.x86.i8086;

import cc.emulator.arch.x86.intel.IntelInstruction;

/**
 * @author Shao Yongqing
 * Date: 2017/8/9.
 */
public  class Instruction8086 extends IntelInstruction {
    /*
     * Typical 8086 Machine IntelInstruction Format
     *
     * |     BYTE 1     |     BYTE 2      |     BYTE 3    |     BYTE 4     |  BYTE 5  |  BYTE 6   |
     * | OPCODE | D | W | MOD | REG | R/M | LOW DISP/DATA | HIGH DISP/DATA | LOW DATA | HIGH DATA |
     *
     *   opcodeDW    MOREGR/M    LOW DISP    HIGHDISP    LOW DATA     HIGHDATA
     */

    public Instruction8086(int[] raw) {
        // Decode First byte
        op = raw[0];
        d = op >>> 1 & 0b1;
        w = op & 0b1;

        setLength(1);
    }

    protected void setLength(int len) {
        length = len;
    }


    @Override
    public int getClocks() {
        return 1;
    }

    @Override
    public int getImmediate() {
        return immediate;
    }

    public void setImmediate(int immediate) {
        this.immediate = immediate;
    }

    protected int immediate = 0;


    protected int length;

    @Override
    public int getLength(){
        return length;
    }

    @Override
    public int getAddress() {
        return address;
    }

    protected void setAddress(int address) {
        this.address = address;
    }

    int address;

}

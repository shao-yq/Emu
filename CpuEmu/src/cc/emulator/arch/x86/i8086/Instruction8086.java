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
        this(raw,1);
    }
    protected Instruction8086(int[] raw, int cnt) {
        switch(cnt){
            case 2:
                decodeByte1(raw[1]);

            case 1:
                decodeByte0(raw[0]);
                break;
        }

        setLength(cnt);

        //if(cnt>1)
        //    decodeDisplacement(raw);
    }

    public void decodeByte0(int raw){
        op = raw;
        d = op >>> 1 & 0b1;
        w = op & 0b1;
    }

    public void decodeByte1(int raw){
        mod = raw >>> 6 & 0b11;
        reg = raw >>> 3 & 0b111;
        rm  = raw       & 0b111;

    }

    public void decodeDisplacement(int queue[]) {
        switch(mod){
            case MOD_MEMORY_DISP0:
                if(rm == 0b110){
                    //immediate = queue[3] << 8 | queue[2];
                    disp = queue[3] << 8 | queue[2];
                    incLength(2);
                }
                break;
            case MOD_MEMORY_DISP8:
                // 8-bit displacement follows
                disp = queue[2];
                incLength(1);
                break;
            case MOD_MEMORY_DISP16:
                // 16-bit displacement follows
                disp = queue[3] << 8 | queue[2];
                incLength(2);
                break;
        }
    }

    public boolean hasDisp(){
        if(hasDisp8() || hasDisp16())
            return true;
        return false;
    }

    public boolean hasDisp8(){
        return mod==MOD_MEMORY_DISP8;
    }
    public boolean hasDisp16(){
        return mod==MOD_MEMORY_DISP16;
    }

    public void decodeDispData(int queue[]) {
        decodeDisplacement(queue);

        decodeData(queue);
    }
    public boolean isRegisterToRegister() {
        return mod == MOD_REGISTER_TO_REGISTER;
    }
    public void decodeData(int[] queue) {
        if(isRegisterToRegister())
            return;

        int cnt = 2;

        if(mod==MOD_MEMORY_DISP0 && rm == 0b110) {
            cnt = 4;
            if(w==B) {
                immediate = queue[cnt];
                incLength(1);
            }else{
                immediate = queue[cnt + 1] << 8 | queue[cnt];
                incLength(2);
            }

            return;
        }

        if(hasDisp8())
            cnt +=1 ;
        if(hasDisp16())
            cnt +=2;

        if(w==B){
            immediate = queue[cnt];
            incLength(1);
        } else {
            immediate = queue[cnt+1]<<8 | queue[cnt];
            incLength(2);
        }
    }

    protected void setLength(int len) {
        length = len;
    }

    protected void incLength(int delta){
        length += delta;
    }


    @Override
    public int getClocks() {
        return 1;
    }


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

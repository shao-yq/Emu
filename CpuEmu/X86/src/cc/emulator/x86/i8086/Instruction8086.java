package cc.emulator.x86.i8086;

import cc.emulator.x86.intel.IntelInstruction;

/**
 *
 * Typical 8086 Machine IntelInstruction Format
 *
 * | 0~4 byte (optional)|     BYTE 1     |     BYTE 2      |     BYTE 3    |     BYTE 4     |  BYTE 5  |  BYTE 6   |
 * |Instruction Prefixes| OPCODE | D | W | MOD | REG | R/M | LOW DISP/DATA | HIGH DISP/DATA | LOW DATA | HIGH DATA |
 *
 *   Prefi(optional)           opcodeDW    MOREGR/M          LOW DISP      HIGHDISP        LOW DATA     HIGHDATA
 *
 *
 * Instruction Prefixes
 * Instruction prefixes are divided into four groups, each with a set of allowable prefix codes. For each instruction, it
 * is only useful to include up to one prefix code from each of the four groups (Groups 1, 2, 3, 4). Groups 1 through 4
 * may be placed in any order relative to each other.
 *
 * • Group 1
 * — Lock and repeat prefixes:
 *   • LOCK prefix is encoded using F0H
 *   • REPNE/REPNZ prefix is encoded using F2H. Repeat-Not-Zero prefix applies only to string and input/output instructions.
 *   (F2H is also used as a mandatory prefix for some instructions)
 * REP or REPE/REPZ is encoded using F3H. The repeat prefix applies only to string and input/output instructions. F3H
 * is also used as a mandatory prefix for POPCNT, LZCNT and ADOX instructions.
 * • Group 2
 * — Segment override prefixes:
 *   • 2EH—CS segment override (use with any branch instruction is reserved)
 *   • 36H—SS segment override prefix (use with any branch instruction is reserved)
 *   • 3EH—DS segment override prefix (use with any branch instruction is reserved)
 *   • 26H—ES segment override prefix (use with any branch instruction is reserved)
 *   • 64H—FS segment override prefix (use with any branch instruction is reserved)
 *   • 65H—GS segment override prefix (use with any branch instruction is reserved)
 * — Branch hints:
 *   • 2EH—Branch not taken (used only with Jcc instructions)
 *   • 3EH—Branch taken (used only with Jcc instructions)
 * • Group 3
 *   • Operand-size override prefix is encoded using 66H (66H is also used as a mandatory prefix for some instructions).
 * • Group 4
 *   • 67H—Address-size override prefix
 *
 *
 *  @author Shao Yongqing
 * Date: 2017/8/9.
 *
 */
public  class Instruction8086 extends IntelInstruction {
    public Instruction8086(){}

    public Instruction8086(int[] raw, int startIndex) {
        // Decode First byte
        //this(raw,1, startIndex);

        decode(raw, startIndex);
    }

    public Instruction8086(int[] raw) {
        // Decode First byte
        this(raw,0);
    }

    int startIndex=0;
    protected Instruction8086(int[] raw, int cnt, int startIndex) {
        decode( raw,  cnt,  startIndex);
//        this.startIndex=startIndex;
//        switch(cnt){
//            case 2:
//                decodeByte1(raw[1+startIndex]);
//
//            case 1:
//                decodeByte0(raw[startIndex]);
//                break;
//        }
//
//        setLength(cnt+startIndex);
//        // Copy the prefix(es)
//        if(startIndex>0) {
//            prefixCount = startIndex;
//            prefixes = new int[prefixCount];
//            for (int i = 0; i < startIndex; i++) {
//                prefixes[i] = raw[i];
//            }
//        }
    }

    public void decode(int[] raw, int startIndex) {
        decode(raw, 1, startIndex);
    }

    @Override
    public boolean hasOpcode(int[] queue, int startIndex) {
        return true;
    }

//    @Override
//    public boolean canDecode(int[] queue, int startIndex) {
//        return false;
//    }

    public void decode(int[] raw, int cnt, int startIndex) {
        this.startIndex=startIndex;
        switch(cnt){
            case 2:
                decodeByte1(raw[1+startIndex]);

            case 1:
                decodeByte0(raw[startIndex]);
                break;
        }

        setLength(cnt+startIndex);
        // Copy the prefix(es)
        if(startIndex>0) {
            prefixCount = startIndex;
            prefixes = new int[prefixCount];
            for (int i = 0; i < startIndex; i++) {
                prefixes[i] = raw[i];
            }
        }
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
                    disp = queue[3+startIndex] << 8 | queue[2+startIndex];
                    incLength(2);
                }
                break;
            case MOD_MEMORY_DISP8:
                // 8-bit displacement follows
                disp = queue[2+startIndex];
                incLength(1);
                break;
            case MOD_MEMORY_DISP16:
                // 16-bit displacement follows
                disp = queue[3+startIndex] << 8 | queue[2+startIndex];
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

        int cnt = 2+startIndex;

        if(mod==MOD_MEMORY_DISP0 && rm == 0b110) {
            cnt = 4+startIndex;
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

    protected void decodeDataExt(int[] raw){
        immediate = raw[length];
        incLength(1);
        if(op==EXT_0X81){
            immediate |= raw[length]<<8;
            incLength(1);
        } else if(op==EXT_0X83){
            // Extend sign 0f byte?
            if((immediate &0x80) != 0){
                immediate  |= 0xff00;
            }
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

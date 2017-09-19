package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class MovImmediateToRegMem extends MOV {
    public MovImmediateToRegMem(){}
    public MovImmediateToRegMem(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    public void decode(int[] raw, int startIndex) {
        decode(raw, 2, startIndex);
        decodeDisplacement(raw);
        if(reg == 0b000)
            decodeData(raw);
    }

    public  boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch(raw) {
            // Immediate to Register/Memory
            case MOV_REG8__MEM8_IMMED8   : // 0xc6: // MOV REG8/MEM8,IMMED8
            case MOV_REG16__MEM16_IMMED16: // 0xc7: // MOV REG16/MEM16,IMMED16
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return mod == MOD_REGISTER_TO_REGISTER ? 4 : 10;
    }
}

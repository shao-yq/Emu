package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class MovRegMemFromToSegReg extends MOV {
    public MovRegMemFromToSegReg(int[] raw) {
        super(raw, 2);
        decodeDisplacement(raw);
    }
    public static boolean hasOpcode(int raw) {
        switch (raw) {
            // Register/Memory to/from Segment Register
            case MOV_REG16_MEM16__SEGREG: //  0x8c: // MOV REG16/MEM16,SEGREG
            case MOV_SEGREG_REG16__MEM16: //  0x8e: // MOV SEGREG,REG16/MEM16
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return mod == 0b11 ? 2 : 9;
    }
}

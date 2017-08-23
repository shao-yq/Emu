package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class ImmediateToRegMem extends Instruction8086 {
    public ImmediateToRegMem(int[] raw) {
        super(raw, 2);
        decodeDisplacement(raw);
        if(reg == 0b000)
            decodeData(raw);
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

//    public void decodeDisplacement(int queue[]) {
//        switch (reg) {
//            case 0b000:
//                disp = (queue[3]<<8) | queue[2];
//                if(w==0){
//                    // MOV MEM8, IMMED8
//                    data = queue[4];
//                } else {
//                    // MOV MEM16, IMMED16
//                    data = (queue[5]<<8)|queue[4];
//                }
//                break;
//        }
//    }

    @Override
    public int getClocks() {
        return mod == MOD_REGISTER_TO_REGISTER ? 4 : 10;
    }
}

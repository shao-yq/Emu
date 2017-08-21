package cc.emulator.arch.x86.i8086.instruction;

import cc.emulator.arch.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class ImmediateToRegister extends Instruction8086 {

    public ImmediateToRegister(int raw[]) {
        super(raw);
        w = op >>> 3 & 0b1;
        reg = op & 0b111;
        if(w==0) {  // DATA-8
            setImmediate(raw[1]);
            setLength(2);
        } else {    // DATA-16
            setImmediate((raw[2]<<8) | raw[1]);
            setLength(3);
        }
    }

    public static boolean hasOpcode(int raw) {
//        // Immediate to Register
//        case MOV_AL_IMMED8 : //  0xb0: // MOV AL,IMMED8
//        case MOV_CL_IMMED8 : //  0xb1: // MOV CL,IMMED8
//        case MOV_DL_IMMED8 : //  0xb2: // MOV DL,IMMED8
//        case MOV_BL_IMMED8 : //  0xb3: // MOV BL,IMMED8
//        case MOV_AH_IMMED8 : //  0xb4: // MOV AH,IMMED8
//        case MOV_CH_IMMED8 : //  0xb5: // MOV CH,IMMED8
//        case MOV_DH_IMMED8 : //  0xb6: // MOV DH,IMMED8
//        case MOV_BH_IMMED8 : //  0xb7: // MOV BH,IMMED8
//        case MOV_AX_IMMED16: //  0xb8: // MOV AX,IMMED16
//        case MOV_CX_IMMED16: //  0xb9: // MOV CX,IMMED16
//        case MOV_DX_IMMED16: //  0xba: // MOV DX,IMMED16
//        case MOV_BX_IMMED16: //  0xbb: // MOV BX,IMMED16
//        case MOV_SP_IMMED16: //  0xbc: // MOV SP,IMMED16
//        case MOV_BP_IMMED16: //  0xbd: // MOV BP,IMMED16
//        case MOV_SI_IMMED16: //  0xbe: // MOV SI,IMMED16
//        case MOV_DI_IMMED16: //  0xbf: // MOV DI,IMMED16
        int opcode = raw>>>4;
        if(opcode==0b1011){
            return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 4;
    }
}

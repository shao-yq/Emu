package cc.emulator.x86.i8086.instruction;

import cc.emulator.core.cpu.ExecutionUnit;
import cc.emulator.core.cpu.Stack;
import cc.emulator.x86.i8086.EU8086;
import cc.emulator.x86.i8086.Instruction8086;
import cc.emulator.x86.i8086.IntelInstructionHelper;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class MovRegMemFromToReg extends OrRegisterMemory {
    public MovRegMemFromToReg(){}
    public MovRegMemFromToReg(int[] raw, int startIndex) {
        super(raw, startIndex);
    }
    public void decode(int[] raw, int startIndex) {
        decode(raw, 2, startIndex);
        decodeDisplacement(raw);
    }
    public int getClocks(int c) {
        if (mod == 0b11) {
           return 2;
        } else {
            if (d == 0b0) {
                return 9;
            }
            return 8;
        }
    }

    public  boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch (raw) {
            // Register/Memory to/from Register
            case MOV_REG8__MEM8_REG8: //   0x88: // MOV REG8/MEM8,REG8
            case MOV_REG16__MEM16_REG16: //   0x89: // MOV REG16/MEM16,REG16
            case MOV_REG8_REG8__MEM8: //   0x8a: // MOV REG8,REG8/MEM8
            case MOV_REG16_REG16__MEM16: //   0x8b: // MOV REG16,REG16/MEM16
                return true;
        }
        return false;
    }

    public void execute(ExecutionUnit executionUnit, Stack stack) {
//        int src;
//        EU8086 eu = (EU8086) executionUnit;
//        if (d == 0b0) {
//            src = eu.getReg(w, reg);
//            eu.setRM(w, mod, rm, src);
//            //clocks += mod == 0b11 ? 2 : 9;
//        } else {
//            src = eu.getRM(w, mod, rm);
//            eu.setReg(w, reg, src);
//            //clocks +=  mod == 0b11 ? 2 : 8;
//        }
    }

    @Override
    public String getMnemonic() {
        return "MOV";
    }

    @Override
    int oprandMode(int op) {
        return op-MOV_REG8__MEM8_REG8;
    }
//
//    @Override
//    protected String getOperandPart() {
//        StringBuffer asm = new StringBuffer();
//        switch (op) {
//            // Register/Memory to/from Register
//            case MOV_REG8__MEM8_REG8: //   0x88: // MOV REG8/MEM8,REG8
//            case MOV_REG16__MEM16_REG16: //   0x89: // MOV REG16/MEM16,REG16
//                //dest
//                asm.append(" "+ IntelInstructionHelper.getRMFieldString(w, mod, reg, rm,disp));
//
//                // src
//                asm.append(", "+IntelInstructionHelper.getRegMnemonic(reg,w));
//
//                break;
//            case MOV_REG8_REG8__MEM8: //   0x8a: // MOV REG8,REG8/MEM8
//            case MOV_REG16_REG16__MEM16: //   0x8b: // MOV REG16,REG16/MEM16
//                //dest
//                asm.append(" "+IntelInstructionHelper.getRegMnemonic(reg,w));
//
//                //src
//                asm.append(", "+IntelInstructionHelper.getRMFieldString(w, mod, reg, rm, disp));
//                break;
//        }
//
//        return asm.toString();
//    }



}

package cc.emulator.x86.i8086.instruction;

import cc.emulator.core.cpu.ExecutionUnit;
import cc.emulator.core.cpu.Stack;
import cc.emulator.x86.i8086.EU8086;
import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class PopGeneralRegister extends Instruction8086 {
    public PopGeneralRegister(){}
    public PopGeneralRegister(int[] raw, int startIndex) {
        super(raw, startIndex);
    }
    public void decode(int[] raw, int startIndex) {
        decode(raw, 1, startIndex);
        reg = op & 0b111;
    }

    public  boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch (raw) {
            // Register
            case POP_AX: //  0x58: // POP AX
            case POP_CX: //  0x59: // POP CX
            case POP_DX: //  0x5a: // POP DX
            case POP_BX: //  0x5b: // POP BX
            case POP_SP: //  0x5c: // POP SP
            case POP_BP: //  0x5d: // POP BP
            case POP_SI: //  0x5e: // POP SI
            case POP_DI: //  0x5f: // POP DI
                return true;
        }
        return false;
    }
    public int getClocks() {
        return 8;
    }

//    public  void execute(ExecutionUnit eu, Stack stack) {
//        int src;
//        //reg = instruction.reg;        //  op & 0b111;
//        src = stack.pop();         /// pop();
//        ((EU8086)eu).setReg(W, reg, src);
//    }


    @Override
    protected String getOperandPart() {
        StringBuffer asm = new StringBuffer();
        switch (op) {
            // Register
            case POP_AX: //  0x58: // POP AX
                asm.append(" AX");
                break;
            case POP_CX: //  0x59: // POP CX
                asm.append(" CX");
                break;
            case POP_DX: //  0x5a: // POP DX
                asm.append(" DX");
                break;
            case POP_BX: //  0x5b: // POP BX
                asm.append(" BX");
                break;
            case POP_SP: //  0x5c: // POP SP
                asm.append(" SP");
                break;
            case POP_BP: //  0x5d: // POP BP
                asm.append(" BP");
                break;
            case POP_SI: //  0x5e: // POP SI
                asm.append(" SI");
                break;
            case POP_DI: //  0x5f: // POP DI
                asm.append(" DI");
                break;
        }

        return asm.toString();
    }

    @Override
    public String getMnemonic() {
        return "POP";
    }
}
package cc.emulator.arch.arm.instruction;

import cc.emulator.arch.arm.ArmInstruction;
import cc.emulator.core.cpu.Instruction;

public class Branch extends ArmInstruction {
    private final int link;

    public Branch(int[] queue) {
        super(queue);
        // Immediate
        extractImmediate();
        // Link flag
        link =  instruction & 0x01000000;

        setWritesPC (true);
        setFixedJump(true);
    }

    private void extractImmediate() {
        immediate = instruction & 0x00FFFFFF;
        if ((immediate & 0x00800000) != 0) {
            // Extends sign
            immediate |= 0xFF000000;
        }
        immediate <<= 2;
    }

    public static boolean canHandle(int op) {
        if( op ==  0x0A000000)
            return true;
        return false;
    }

    public static Instruction createInstruction(int[] queue) {
        return new Branch(queue);

//        int instruction = queue[0];
//        ArmInstruction armInst;
//
//        int op = instruction & 0x0E000000;
//        // Branch
//        int immediate = instruction & 0x00FFFFFF;
//        if ((immediate & 0x00800000) != 0) {
//            // Extend sign bit
//            immediate |= 0xFF000000;
//        }
//        immediate <<= 2;
//        int link = instruction & 0x01000000;
//        // Condition
//        int cond = instruction >>> 28;
//
//        if (link != 0) {
//            armInst = new BranchLink(queue);    // constructBL(immediate, cond);
//        } else {
//            armInst = new Branch(queue);             // constructB(immediate, cond);
//        }
//        return armInst;
    }
}

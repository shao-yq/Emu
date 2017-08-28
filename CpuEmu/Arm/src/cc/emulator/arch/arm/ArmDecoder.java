package cc.emulator.arch.arm;

import cc.emulator.arch.arm.instruction.ArmInstructionBase;
import cc.emulator.arch.arm.instruction.Branch;
import cc.emulator.arch.arm.instruction.BranchExchange;
import cc.emulator.core.cpu.Instruction;
import cc.emulator.core.cpu.InstructionDecoder;
import cc.emulator.core.cpu.InstructionQueue;

public class ArmDecoder implements InstructionDecoder {
    private Instruction instr;

    @Override
    public Instruction decode(InstructionQueue queue) {
        return decode(queue.getQueue());
    }

    private Instruction decode(int[] queue) {
        Instruction instr = null;
        int instruction = queue[0];

        if(BranchExchange.canHandle(instruction)){
            instr = new BranchExchange(queue);
        } else {
            int op = instruction & 0x0E000000;
            if(Branch.canHandle(op)){
                instr = new Branch(queue);
            }
        }

        if(instr == null){
            instr = new ArmInstructionBase(queue);
        }

        return instr;
    }

    @Override
    public Instruction decode2(InstructionQueue queue) {
        return null;
    }

    @Override
    public void reset() {

    }
}

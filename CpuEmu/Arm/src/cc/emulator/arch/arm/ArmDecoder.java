package cc.emulator.arch.arm;

import cc.emulator.arch.arm.instruction.Branch;
import cc.emulator.arch.arm.instruction.BranchExchange;
import cc.emulator.core.cpu.AbstractInstructionDecoder;
import cc.emulator.core.cpu.Instruction;
import cc.emulator.core.cpu.InstructionDecoder;
import cc.emulator.core.cpu.InstructionQueue;

public class ArmDecoder extends AbstractInstructionDecoder {
    private Instruction instr;
    protected String getPackagePrefix() {
        return  "cc.emulator.arch.arm.rawInstruction.";
    }

    protected  void initInstructionNames() {
        instructionNames = new String[]{
                "Branch",
                "BranchExchange"
        };
    }

    @Override
    public Instruction decode(InstructionQueue queue) {
        return decode(queue.getQueue());
    }

    public Instruction decode(int[] queue) {
        Instruction instr = null;
        int startIndex = 0;
        int instruction = queue[0];

        // Check if there is any Instruction has such opcode
        for(int i=0; i<instructions.length; i++){
            if(instructions[i].hasOpcode(queue, startIndex)){
                instr = (Instruction) instructions[i].clone();
                instr.decodeMe(queue, startIndex);
                break;
            }
        }
//
//        if(BranchExchange.canHandle(rawInstruction)){
//            instr = BranchExchange.createInstruction(queue);
//        } else {
//            int op = rawInstruction & 0x0E000000;
//            if(Branch.canHandle(op)){
//                instr = Branch.createInstruction(queue);
//            }
//        }

        if(instr == null){
            instr =  ArmInstruction.createInstruction(queue);
        }

        return instr;
    }

    @Override
    public void reset() {

    }
}

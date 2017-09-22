package cc.emulator.arch.arm;

import cc.emulator.arch.arm.decoder.DataProcessingDecoder;
import cc.emulator.arch.arm.decoder.UnconditionalDecoder;
import cc.emulator.arch.arm.instruction.Branch;
import cc.emulator.arch.arm.instruction.BranchExchange;
import cc.emulator.arch.arm.instruction.UnconditionalInstruction;
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

    UnconditionalDecoder unconditionalDecoder =  new UnconditionalDecoder();
    DataProcessingDecoder dataProcessingDecoder =  new DataProcessingDecoder();

    @Override
    public Instruction decode(InstructionQueue queue) {
        return decode(queue.getQueue());
    }

    /**
     *
     *
     |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7  6  5 | 4| 3  2  1  0|Instruction Type
     |-----------+--------+------------------------------------------------------------+--+-----------------------------
     | cond      | op1    |                                                            |op|           | Instruction classes(指令分类)
     |-----------+--------+------------------------------------------------------------+--+-----------+---------
     |           | 0 0 x  | -	 | 数据处理和杂项指令）
     |NOT        |------------------------------------------------------------------
     |1111       | 010   | -  | 加载/存储字或无符号的字节
     |           |------------------------------------------------------------------
     |           | 011   | 0  | 加载/存储字或无符号的字节
     |           |       | 1  | 媒体指令
     |           |------------------------------------------------------------------
     |           | 10x   | -  | 分支、带链接分支、块数据传输
     |           | 11x   | -  | 协处理器指令或软中断，包括浮点指令和先进SIMD数据传输
     |-----------+------------------------------------------------------------------
     | 1111     |  -    | -  | 如果cond字段为0b1111，只能无条件地执行指令
     ---------------------------------------------------------------------------
     表格中的op1、op字段中的x、-表示可以是0，也可以是1

     * @param raw
     * @return
     */
    public Instruction decode(int[] raw) {
        Instruction instr = null;
        int startIndex = 0;
        int instruction = raw[0];

//        // Check if there is any Instruction has such opcode
//        for(int i=0; i<instructions.length; i++){
//            if(instructions[i].hasOpcode(raw, startIndex)){
//                instr = (Instruction) instructions[i].clone();
//                instr.decodeMe(raw, startIndex);
//                break;
//            }
//        }

        int rawInstruction = raw[startIndex];
        if(unconditionalDecoder.hasInstruction(rawInstruction)){
            instr = unconditionalDecoder.decode(rawInstruction);
        } else if(dataProcessingDecoder.hasOpcode(rawInstruction)){
            instr = dataProcessingDecoder.decode(rawInstruction);
        } else {
            // Other decoder
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


//        if(instr == null){
//            instr =  ArmInstruction.createInstruction(queue);
//        }

        return instr;
    }




    @Override
    public void reset() {

    }
}

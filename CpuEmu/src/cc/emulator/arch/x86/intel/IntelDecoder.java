package cc.emulator.arch.x86.intel;

import cc.emulator.core.cpu.InstructionDecoder;
import cc.emulator.core.cpu.Instruction;
import cc.emulator.core.cpu.InstructionQueue;

/**
 * @author Shao Yongqing
 * Date: 2017/7/27.
 */
public abstract class IntelDecoder implements InstructionDecoder {
    protected IntelInstruction instr;

    @Override
    public Instruction decode(InstructionQueue queue) {
        return decode(queue.getQueue());
    }

    protected abstract Instruction decode(int[] queue);

    @Override
    public Instruction decode2(InstructionQueue queue) {
        return decode2(queue.getQueue());
    }

    protected abstract Instruction decode2(int[] queue);

    protected abstract IntelInstruction newInstruction(int raw[]) ;


    @Override
    public void reset() {

    }
}

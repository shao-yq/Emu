package cc.emulator.core.cpu;

/**
 * @author Shao Yongqing
 * Date: 2017/7/27.
 */
public interface InstructionDecoder {
    Instruction decode(InstructionQueue queue);
    Instruction decode2(InstructionQueue queue);

    void reset();
}

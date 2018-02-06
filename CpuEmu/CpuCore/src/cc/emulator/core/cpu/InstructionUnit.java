package cc.emulator.core.cpu;

/**
 * @author Shao Yongqing
 * Date: 2017/9/14.
 */
public interface InstructionUnit {
    InstructionDecoder getDecoder();

    InstructionDecoder createDecoder();

    DecodedInstructionQueue getDecodedInstructionQueue();
    Instruction decode(InstructionQueue queue);

    Instruction nextInstruction();
}

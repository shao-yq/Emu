package cc.emulator.core.cpu;

public interface DecodedInstructionQueue {
    void reset();
    void fill(Instruction instruction) throws Exception;
    Instruction fetch() throws Exception;
    Instruction[] getQueue();
}

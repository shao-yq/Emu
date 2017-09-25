package cc.emulator.core.cpu;

public interface InstructionQueue {
    void reset();
    void fillInstructionQueue(int instruction);

    int[] getQueue();

    int getQueueSize();
}

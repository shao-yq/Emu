package cc.emulator.core.cpu;

/**
 * @author Shao Yongqing
 * Date: 2017/9/14.
 */
public abstract class InstructionUnitImpl implements InstructionUnit {
    InstructionDecoder decoder;
    DecodedInstructionQueue decodedInstructionQueue;

    public InstructionUnitImpl(){
        decoder=createDecoder();
        decodedInstructionQueue =createDecodedInstructionQueue();
    }

    protected abstract DecodedInstructionQueue createDecodedInstructionQueue();

    protected abstract InstructionDecoder createDecoder();

    @Override
    public InstructionDecoder getDecoder() {
        return decoder;
    }

    @Override
    public DecodedInstructionQueue getDecodedInstructionQueue() {
        return decodedInstructionQueue;
    }

    @Override
    public Instruction decode(InstructionQueue queue) {
        return decoder.decode(queue);
    }
}

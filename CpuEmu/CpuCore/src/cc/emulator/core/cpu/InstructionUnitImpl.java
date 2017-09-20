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
        if(decodedInstructionQueue==null)
            decodedInstructionQueue = decodedInstructionQueue =createDecodedInstructionQueue();
        return decodedInstructionQueue;
    }

    @Override
    public Instruction decode(InstructionQueue queue) {
        // Decode the raw data into instruction
        Instruction instruction = decoder.decode(queue);
        // Fill to the decodec queuq
        if(instruction!=null){
            try {
                getDecodedInstructionQueue().fill(instruction);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instruction;
    }

    @Override
    public Instruction nextInstruction() {
        Instruction instruction = null;

        try {
            instruction = getDecodedInstructionQueue().fetch();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return instruction;
    }
}

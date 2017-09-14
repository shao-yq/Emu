package cc.emulator.core.cpu;

import cc.emulator.core.cpu.DecodedInstructionQueue;
import cc.emulator.core.cpu.Instruction;
import cc.emulator.util.LoopQueue;

/**
 * @author Shao Yongqing
 * Date: 2017/9/14.
 */
public class DecodedInstructionQueueImpl implements DecodedInstructionQueue {
    LoopQueue loopQueue;

    int queueSize = 5;

    Instruction[] instructions;
    public DecodedInstructionQueueImpl(int queueSize){
        this.queueSize = queueSize;
        instructions =  new Instruction[queueSize];
        loopQueue =  new LoopQueue(queueSize);
    }
    public DecodedInstructionQueueImpl(){
        this(5);
    }
    @Override
    public void reset() {
        loopQueue.reset();
    }

    @Override
    public void fill(Instruction instruction) throws Exception {
        if(loopQueue.isFull()){
            throw new Exception("Queue is full");
        }

        instructions[loopQueue.getTail()] = instruction;
        loopQueue.tailNextValue();
    }

    @Override
    public Instruction fetch() throws Exception {
        if(loopQueue.isEmpty()){
            throw new Exception("Queue is empty");
        }

        Instruction instruction = instructions[loopQueue.getHead()];
        loopQueue.headNextValue();

        return instruction;
    }

    @Override
    public Instruction[] getQueue() {
        return  instructions;
    }


    public boolean isEmpty() {
       return loopQueue.isEmpty();
    }

    private boolean isFull() {
        return loopQueue.isFull();
    }

    public int size(){
        return loopQueue.size();
    }

}

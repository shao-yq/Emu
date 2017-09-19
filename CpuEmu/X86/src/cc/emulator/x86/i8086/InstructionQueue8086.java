package cc.emulator.x86.i8086;

import cc.emulator.core.cpu.InstructionQueue;

public class InstructionQueue8086 implements InstructionQueue {
    /**
     * Queue
     *
     * During periods when the EU is busy executing instructions, the BUI
     * "looks ahead" and fetches more instructions from memory. The
     * instructions are stored in an internal RAM array called the instruction
     * stream queue. The 8086 queue can store up to six instruction bytes. This
     * queue size allow the BIU to keep the EU supplied with prefetched
     * instructions under most conditions without monopolizing the system bus.
     * The 8086 BIU fetches another instruction byte whenever two bytes in its
     * queue are empty and there is no active request for bus access from the
     * EU. It normally obtains two instruction bytes per fetch; if a program
     * transfer forces fetching for an odd address, the 8086 BIU automatically
     * reads one byte for the odd address and then resumes fetching two-byte
     * word from the subsequent even addresses.
     *
     * Under most circumstances the queue contain at least one byte of
     * instruction stream and EU does not have to wait for instructions to be
     * fetched. The instructions in the queue are those stored in the memory
     * locations immediately adjacent to and higher than the instruction
     * currently being executed. That is, they are the next logical
     * instructions so long as execution proceeds serially. If the EU executes
     * an instruction that transfers control to another location, the BIU
     * resets the queue, fetches the instruction from the new address, passes
     * it immediately to the EU, and then begin refilling the queue from the
     * new location. In addition, the BIU suspends instruction fetching
     * whenever the EU requests a memory or I/O read or write (except that a
     * fetch already in progress is completed before executing the EU's bus
     * request).
     */
    private final int[]        queue       = new int[6];

    @Override
    public void reset(){
        //for (int i = 0; i < 6; i++)
        //    queue[i] = 0;
        current=0;
    }
    int current;
    @Override
    public void fillInstructionQueue(int instruction){
        queue[current]=instruction;
        current++;
    }

    @Override
    public int[] getQueue() {
        return queue;
    }

}

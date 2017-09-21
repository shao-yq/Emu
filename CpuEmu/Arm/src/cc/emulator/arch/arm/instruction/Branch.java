package cc.emulator.arch.arm.instruction;

import cc.emulator.arch.arm.ArmInstruction;
import cc.emulator.core.cpu.Instruction;

/**
 *  Instruction Branch:
 *  B
 *  BL
 *
 */
public class Branch extends ArmInstruction {
    private  int link;

    public Branch(){
    }

    public Branch(int[] queue) {
        super(queue);
    }

    @Override
    public void decode(int[] raw, int startIndex) {
        super.decode(raw, startIndex);

        // Immediate
        extractImmediate();
        // Link flag
        link =  rawInstruction & 0x01000000;

        setWritesPC (true);
        setFixedJump(true);
    }

    private void extractImmediate() {
        immediate = rawInstruction & 0x00FFFFFF;
        if ((immediate & 0x00800000) != 0) {
            // Extends sign
            immediate |= 0xFF000000;
        }
        immediate <<= 2;
    }

    public static boolean canHandle(int op) {
        if( op ==  0x0A000000) {
            //if((op & 0x01000000) ==0){
                // B
                return true;
           // } else {
                // link ?
                // BL
            //}
        }
        return false;
    }

    @Override
    public boolean hasOpcode(int[] queue, int startIndex) {
        return canHandle(queue[startIndex]);
    }

}

package cc.emulator.arch.arm.instruction;

public class Branch extends ArmInstructionBase{
    private final int link;

    public Branch(int[] queue) {
        super(queue);
        // Immediate
        extractImmediate();
        // Link flag
        link =  instruction & 0x01000000;

        setWritesPC (true);
        setFixedJump(true);
    }

    private void extractImmediate() {
        immediate = instruction & 0x00FFFFFF;
        if ((immediate & 0x00800000) != 0) {
            // Extends sign
            immediate |= 0xFF000000;
        }
        immediate <<= 2;
    }

    public static boolean canHandle(int op) {
        if( op ==  0x0A000000)
            return true;
        return false;
    }

}

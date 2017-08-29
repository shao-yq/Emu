package cc.emulator.arch.arm.instruction;

import cc.emulator.arch.arm.ArmInstruction;
import cc.emulator.core.cpu.Instruction;

public class ArmInstructionBase implements ArmInstruction{

    protected final int instruction;
    protected final int cond;

    protected  int opCode;
    protected  int immediate;
    protected  int length;
    protected  int address;

    protected  boolean writesPC;
    protected  boolean fixedJump;

    public int getInstruction() {
        return instruction;
    }

    protected void setWritesPC(boolean writesPC) {
        this.writesPC = writesPC;
    }

    protected void setFixedJump(boolean fixedJump) {
        this.fixedJump = fixedJump;
    }

    public boolean isWritesPC() {
        return writesPC;
    }

    public boolean isFixedJump(){
        return fixedJump;
    }

    public ArmInstructionBase(int[] queue) {
        // Save the instruction raw data
        instruction = queue[0];
        // Condition
        cond = instruction >>> 28;
    }

    @Override
    public int getOpCode() {
        return opCode;
    }

    @Override
    public int getOperand(int index) {
        return 0;
    }

    @Override
    public int getClocks() {
        return 0;
    }

    @Override
    public int getImmediate() {
        return immediate;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public int getAddress() {
        return address;
    }

    public static Instruction createInstruction(int[] queue) {
        return new ArmInstructionBase(queue);
    }
}

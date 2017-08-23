package cc.emulator.arch.arm;

/**
 * @author Shao Yongqing
 * Date: 2017/8/18.
 */
public abstract class DataProcessMiscInstruction implements ArmInstruction{
    @Override
    public int getOpCode() {
        return 0;
    }

    @Override
    public int getOperand(int index) {
        return 0;
    }
}

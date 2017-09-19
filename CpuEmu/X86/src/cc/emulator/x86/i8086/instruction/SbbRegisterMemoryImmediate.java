package cc.emulator.x86.i8086.instruction;

/**
 * @author Shao Yongqing
 * Date: 2017/9/1.
 */
public class SbbRegisterMemoryImmediate extends SbbRegisterMemory {
    public SbbRegisterMemoryImmediate(){}
    public SbbRegisterMemoryImmediate(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    public void decode(int[] raw, int startIndex) {
        super.decode(raw, startIndex);
        decodeDataExt(raw);
    }
}

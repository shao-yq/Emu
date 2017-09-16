package cc.emulator.x86.i8086.instruction;

/**
 * @author Shao Yongqing
 * Date: 2017/9/1.
 */
public class SbbRegisterMemoryImmediate extends SbbRegisterMemory {
    public SbbRegisterMemoryImmediate(int[] raw, int startIndex) {
        super(raw, startIndex);
        decodeDataExt(raw);
    }
}

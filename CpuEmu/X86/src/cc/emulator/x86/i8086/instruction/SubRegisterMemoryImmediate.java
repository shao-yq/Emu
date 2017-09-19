package cc.emulator.x86.i8086.instruction;

import cc.emulator.core.cpu.Instruction;

/**
 * @author Shao Yongqing
 * Date: 2017/9/1.
 */
public class SubRegisterMemoryImmediate extends SubRegisterMemory {
    public SubRegisterMemoryImmediate(){}
    public SubRegisterMemoryImmediate(int[] raw, int startIndex) {
        super(raw, startIndex);
    }
    public void decode(int[] raw, int startIndex) {
        super.decode(raw, startIndex);
        decodeDataExt(raw);
    }
}

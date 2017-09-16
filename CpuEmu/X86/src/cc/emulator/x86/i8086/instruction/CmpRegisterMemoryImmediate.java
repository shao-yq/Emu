package cc.emulator.x86.i8086.instruction;

import cc.emulator.core.cpu.Instruction;

/**
 * @author Shao Yongqing
 * Date: 2017/9/1.
 */
public class CmpRegisterMemoryImmediate extends CmpRegisterMemory {
    public CmpRegisterMemoryImmediate(int[] raw, int startIndex) {
        super(raw, startIndex);
        decodeDataExt(raw);
    }
}

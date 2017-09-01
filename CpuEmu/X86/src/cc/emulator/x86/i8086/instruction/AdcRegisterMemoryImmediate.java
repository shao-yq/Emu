package cc.emulator.x86.i8086.instruction;

import cc.emulator.core.cpu.Instruction;

/**
 * @author Shao Yongqing
 * Date: 2017/9/1.
 */
public class AdcRegisterMemoryImmediate extends AddRegisterMemory {
    public AdcRegisterMemoryImmediate(int[] raw) {
        super(raw);
        decodeDataExt(raw);
    }
}

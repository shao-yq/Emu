package cc.emulator.x86.i8086.instruction;

import cc.emulator.core.cpu.Instruction;

/**
 * @author Shao Yongqing
 * Date: 2017/9/1.
 */
public class SubRegisterMemoryImmediate extends SubRegisterMemory {
    public SubRegisterMemoryImmediate(int[] raw) {
        super(raw);
        decodeDataExt(raw);
    }
}
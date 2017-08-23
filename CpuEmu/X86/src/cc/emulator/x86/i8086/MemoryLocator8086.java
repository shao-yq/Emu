package cc.emulator.x86.i8086;

import cc.emulator.core.cpu.RegisteredMemoryLocator;
import cc.emulator.core.cpu.register.PointerIndexer;
import cc.emulator.core.cpu.register.SegmentRegister;

/**
 * @author Shao Yongqing
 * Date: 2017/7/28.
 */
public class MemoryLocator8086 extends RegisteredMemoryLocator {
    public MemoryLocator8086(SegmentRegister segmentRegister, PointerIndexer pointerIndexer) {
        super(segmentRegister, pointerIndexer);
    }
}

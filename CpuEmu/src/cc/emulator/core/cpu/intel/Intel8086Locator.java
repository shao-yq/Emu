package cc.emulator.core.cpu.intel;

import cc.emulator.core.cpu.RegisteredMemoryLocator;
import cc.emulator.core.cpu.register.PointerIndexer;
import cc.emulator.core.cpu.register.SegmentRegister;

/**
 * @author Shao Yongqing
 * Date: 2017/7/28.
 */
public class Intel8086Locator extends RegisteredMemoryLocator {
    public Intel8086Locator(SegmentRegister segmentRegister, PointerIndexer pointerIndexer) {
        super(segmentRegister, pointerIndexer);
    }
}

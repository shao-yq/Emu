package cc.emulator.core.cpu;

import cc.emulator.core.cpu.register.PointerIndexer;
import cc.emulator.core.cpu.register.SegmentRegister;

/**
 * @author Shao Yongqing
 * Date: 2017/7/28.
 */
public class RegisteredMemoryLocator implements MemoryLocator{
    SegmentRegister segmentRegister;
    PointerIndexer  pointerIndexer;

    public RegisteredMemoryLocator(SegmentRegister segmentRegister, PointerIndexer  pointerIndexer){
        this.segmentRegister = segmentRegister;
        this.pointerIndexer = pointerIndexer;
    }

    @Override
    public int getBase() {
        return segmentRegister.getData();
    }

    @Override
    public void setBase(int base) {
        segmentRegister.setData(base);
    }

    @Override
    public int getOffset() {
        return pointerIndexer.getData();
    }

    @Override
    public void setOffset(int offset) {
        pointerIndexer.setData(offset);
    }

    @Override
    public void incOffset() {
        pointerIndexer.increase();
    }
    @Override
    public void decOffset() {
        pointerIndexer.decrease();
    }

    @Override
    public void incOffset(int delta) {
        pointerIndexer.increase(delta);
    }

    @Override
    public void decOffset(int delta) {
        pointerIndexer.decrease(delta);
    }

}

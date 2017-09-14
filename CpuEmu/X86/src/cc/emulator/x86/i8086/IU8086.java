package cc.emulator.x86.i8086;

import cc.emulator.core.cpu.*;
import cc.emulator.x86.intel.IntelInstructionUnit;

/**
 * @author Shao Yongqing
 * Date: 2017/9/14.
 */
public class IU8086 extends IntelInstructionUnit {

    @Override
    protected DecodedInstructionQueue createDecodedInstructionQueue() {
        return new DecodedInstructionQueueImpl(4);
    }

    @Override
    protected InstructionDecoder createDecoder() {
        return new Decoder8086();
    }

}

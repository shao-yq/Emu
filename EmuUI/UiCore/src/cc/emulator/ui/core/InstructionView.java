package cc.emulator.ui.core;

import cc.emulator.core.cpu.Instruction;


/**
 * @author Shao Yongqing
 * Date: 2017/9/20.
 */
public interface InstructionView {
    /**
     * Switch insrtuction view mode
     * @param mode the view mode value: BINARY, ASM
     */
    void switchMode(int mode);

    Instruction getCurrentInstruction();
}

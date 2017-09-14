package cc.emulator.core.cpu;

import cc.emulator.core.cpu.register.GeneralRegister;
import cc.emulator.core.cpu.register.PointerIndexer;
import cc.emulator.core.cpu.register.StatusRegister;

/**
 * @author Shao Yongqing
 * Date: 2017/7/27.
 */
public interface ExecutionUnit {
    ArithmeticLogicUnit getALU();
    GeneralRegister[] getGeneralRegisters();
    StatusRegister getStatusRegister();

    PointerIndexer[] getPointerIndexers();
    PointerIndexer getPointerIndexer(String name);

    void reset();

    void execute(Instruction instr);
}

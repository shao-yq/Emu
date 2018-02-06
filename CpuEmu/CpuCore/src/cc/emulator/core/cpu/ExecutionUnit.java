package cc.emulator.core.cpu;

import cc.emulator.core.Peripheral;
import cc.emulator.core.ProgrammableInterruptController;
import cc.emulator.core.ProgrammableIntervalTimer;
import cc.emulator.core.cpu.register.GeneralRegister;
import cc.emulator.core.cpu.register.PointerIndexer;
import cc.emulator.core.cpu.register.StatusRegister;

/**
 * @author Shao Bofeng
 * Date: 2017/7/27.
 */
public interface ExecutionUnit {
    ArithmeticLogicUnit getALU();
    GeneralRegister[] getGeneralRegisters();
    StatusRegister getStatusRegister();

    PointerIndexer[] getPointerIndexers();
    PointerIndexer getPointerIndexer(String name);

    void reset();

    boolean execute(Instruction instr);

    void trySingleStepMode();

    void tryExternalMaskabkeInterrupts(ProgrammableInterruptController pic);

    void setPic(ProgrammableInterruptController pic);

    void setPit(ProgrammableIntervalTimer pit);

    void setPeripherals(Peripheral[] peripherals);

    void toggleStep();

    boolean isStepMode();
}

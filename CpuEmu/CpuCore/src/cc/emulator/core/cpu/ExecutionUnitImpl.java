package cc.emulator.core.cpu;

import cc.emulator.core.cpu.register.GeneralRegister;
import cc.emulator.core.cpu.register.StatusRegister;

public abstract class ExecutionUnitImpl implements ExecutionUnit{
    ArithmeticLogicUnit alu;

    protected GeneralRegister[] generalRegisters;
    StatusRegister statusRegister;

    public ExecutionUnitImpl(){
        generalRegisters=createGeneralRegisters();
        statusRegister=createStatusRegister();
        alu=createALU(statusRegister);
    }

    @Override
    public void reset(){
        alu.reset();

        for(GeneralRegister register:generalRegisters){
            register.reset();
        }
        statusRegister.reset();
    }

    protected abstract StatusRegister createStatusRegister();

    protected abstract GeneralRegister[] createGeneralRegisters();

    protected abstract InstructionDecoder createDecoder();

    protected abstract ArithmeticLogicUnit createALU(StatusRegister flags) ;

    @Override
    public ArithmeticLogicUnit getALU() {
        return alu;
    }

    @Override
    public GeneralRegister[] getGeneralRegisters() {
        return generalRegisters;
    }

    @Override
    public StatusRegister getStatusRegister() {
        return statusRegister;
    }
}

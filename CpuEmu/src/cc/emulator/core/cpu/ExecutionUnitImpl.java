package cc.emulator.core.cpu;

import cc.emulator.core.cpu.register.GeneralRegister;
import cc.emulator.core.cpu.register.StatusRegister;

public abstract class ExecutionUnitImpl implements ExecutionUnit{
    ArithmeticLogicUnit alu;
    InstructionDecoder decoder;
    GeneralRegister[] generalRegisters;
    StatusRegister statusRegister;

    public ExecutionUnitImpl(){
        alu=createALU();
        decoder=createDecoder();
        generalRegisters=createGeneralRegisters();
        statusRegister=createStatusRegister();
    }

    @Override
    public void reset(){
        alu.reset();
        decoder.reset();
        for(GeneralRegister register:generalRegisters){
            register.reset();
        }
        statusRegister.reset();
    }

    protected abstract StatusRegister createStatusRegister();

    protected abstract GeneralRegister[] createGeneralRegisters();

    protected abstract InstructionDecoder createDecoder();

    protected abstract ArithmeticLogicUnit createALU() ;

    @Override
    public ArithmeticLogicUnit getALU() {
        return alu;
    }

    @Override
    public InstructionDecoder getDecoder() {
        return decoder;
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

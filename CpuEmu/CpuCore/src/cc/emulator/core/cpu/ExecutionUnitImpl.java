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
public abstract class ExecutionUnitImpl implements ExecutionUnit{
    protected ArithmeticLogicUnit alu;
    protected PointerIndexer pointerIndexers[];

    protected GeneralRegister[] generalRegisters;
    StatusRegister statusRegister;
    private ProgrammableInterruptController pic;
    protected ProgrammableIntervalTimer pit;
    protected Peripheral[] peripherals;
    protected Stack stack;

    public ExecutionUnitImpl(){
        generalRegisters=createGeneralRegisters();
        statusRegister=createStatusRegister();
        alu=createALU(statusRegister);
        pointerIndexers=createPointerIndexers();
    }

    @Override
    public PointerIndexer[] getPointerIndexers() {
        return pointerIndexers;
    }

    @Override
    public void reset(){
        alu.reset();

        for(GeneralRegister register:generalRegisters){
            register.reset();
        }
        statusRegister.reset();
        for(PointerIndexer pointerIndexer:pointerIndexers)
            pointerIndexer.reset();
    }

    public abstract PointerIndexer[] createPointerIndexers();

    protected abstract StatusRegister createStatusRegister();

    protected abstract GeneralRegister[] createGeneralRegisters();

    protected abstract ArithmeticLogicUnit createALU(StatusRegister flags) ;

    @Override
    public ArithmeticLogicUnit getALU() {
        if(alu==null){
            alu = createALU(getStatusRegister());
        }
        return alu;
    }

    @Override
    public GeneralRegister[] getGeneralRegisters() {
        if(generalRegisters==null)
            generalRegisters = createGeneralRegisters();

        return generalRegisters;
    }

    @Override
    public StatusRegister getStatusRegister() {
        if(statusRegister==null)
            statusRegister = createStatusRegister();

        return statusRegister;
    }

    protected  abstract  Stack createStack();

    @Override
    public void setPic(ProgrammableInterruptController pic) {
        this.pic = pic;
    }

    @Override
    public void setPit(ProgrammableIntervalTimer pit) {
        this.pit = pit;
    }


    @Override
    public void setPeripherals(Peripheral[] peripherals) {
        this.peripherals = peripherals;
    }
}

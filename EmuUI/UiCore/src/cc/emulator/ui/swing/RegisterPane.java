package cc.emulator.ui.swing;

import cc.emulator.core.cpu.Register;
import cc.emulator.core.cpu.register.*;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * @author Shao Yongqing
 * Date: 2017/10/17.
 */
public class RegisterPane extends JPanel{
    AccumulatorZone accumulatorZone;
    PointerIndexerZone indexerPointerZone;
    SegmentRegisterZone segmentRegisterZone;
    ProgramCounterZone programerCounterZone ;
    StatusRegisterZone statusRegisterZone;

    public RegisterPane(){
        initUi();
    }

    private void initUi(){
        this.setLayout(new BorderLayout());
        accumulatorZone =  new AccumulatorZone();
        accumulatorZone.initUi();
        indexerPointerZone =  new PointerIndexerZone();
        indexerPointerZone.initUi();
        segmentRegisterZone =  new SegmentRegisterZone();
        segmentRegisterZone.initUi();
        programerCounterZone =  new ProgramCounterZone();
        programerCounterZone.initUi();

        statusRegisterZone = new StatusRegisterZone();
        statusRegisterZone.initUi();

        // Default Layout: FlowLayout
        this.add(accumulatorZone,BorderLayout.CENTER);
        this.add(indexerPointerZone, BorderLayout.WEST);
        this.add(segmentRegisterZone,BorderLayout.EAST);
        this.add(programerCounterZone,BorderLayout.NORTH);
        this.add(statusRegisterZone, BorderLayout.SOUTH);

//        this.setLayout(new BorderLayout());
//        this.add(accumulatorZone, BorderLayout.EAST);
//        this.add(indexerPointerZone, BorderLayout.WEST);
    }

     public void setAccumulators(Vector<DividableRegister> registers) {
        accumulatorZone.setDividableRegisters(registers);
    }
    public void setPointerIndexers(PointerIndexer[] generalRegister) {
        if(generalRegister==null)
            return;

        Vector<Register> registers = new Vector<Register>();
        for(int i=0; i<generalRegister.length; i++){
            if(generalRegister[i] instanceof PointerIndexer)
                registers.add(generalRegister[i]);
        }
        indexerPointerZone.setGeneralRegisters(registers);
    }

    public void setSegmentRegisters(SegmentRegister[] segmentRegister) {
        if(segmentRegister==null)
            return;

        Vector<Register> registers = new Vector<Register>();
        for(int i=0; i<segmentRegister.length; i++){
            if(segmentRegister[i] instanceof SegmentRegister)
                registers.add(segmentRegister[i]);
        }

        segmentRegisterZone.setGeneralRegisters(registers);
    }

    public void setProgramCounter(ProgramCounter programCounter) {
        Vector<Register> registers = new Vector<Register>();
        registers.add(programCounter);
        programerCounterZone.setGeneralRegisters(registers);
    }
    public void setStatusRegister(StatusRegister statusRegister) {
        statusRegisterZone.setStatusRegister(statusRegister);
    }
}

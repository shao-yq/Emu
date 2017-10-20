package cc.emulator.ui.swing;

import cc.emulator.core.cpu.Register;
import cc.emulator.core.cpu.register.DividableRegister;
import cc.emulator.core.cpu.register.GeneralRegister;
import cc.emulator.core.cpu.register.PointerIndexer;
import cc.emulator.core.cpu.register.SegmentRegister;

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

    public RegisterPane(){
        initUi();
    }

    private void initUi(){
        accumulatorZone =  new AccumulatorZone();
        accumulatorZone.initUi();
        indexerPointerZone =  new PointerIndexerZone();
        indexerPointerZone.initUi();
        segmentRegisterZone =  new SegmentRegisterZone();
        segmentRegisterZone.initUi();

        // Default Layout: FlowLayout
        this.add(accumulatorZone);
        this.add(indexerPointerZone);
        this.add(segmentRegisterZone);
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

}

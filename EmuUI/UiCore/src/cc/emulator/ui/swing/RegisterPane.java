package cc.emulator.ui.swing;

import cc.emulator.core.cpu.register.DividableRegister;
import cc.emulator.core.cpu.register.GeneralRegister;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * @author Shao Yongqing
 * Date: 2017/10/17.
 */
public class RegisterPane extends JPanel{
    AccumulatorZone accumulatorZone;
    IndexerPointerZone indexerPointerZone;

    public RegisterPane(){
        initUi();
    }

    private void initUi(){
        accumulatorZone =  new AccumulatorZone();
        accumulatorZone.initUi();
        this.setLayout(new BorderLayout());
        this.add(accumulatorZone, BorderLayout.EAST);
        indexerPointerZone =  new IndexerPointerZone();
        indexerPointerZone.initUi();
        this.add(indexerPointerZone, BorderLayout.WEST);
    }

     public void setAccumulators(Vector<DividableRegister> registers) {
        accumulatorZone.setDividableRegisters(registers);
    }
    public void setIndexerPointers(GeneralRegister[] generalRegister) {
        if(generalRegister==null)
            return;

        Vector<GeneralRegister> registers = new Vector<GeneralRegister>();
        for(int i=0; i<generalRegister.length; i++){
            registers.add(generalRegister[i]);
        }
        indexerPointerZone.setGeneralRegisters(registers);
    }

}

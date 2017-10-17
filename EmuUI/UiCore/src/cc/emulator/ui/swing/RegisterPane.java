package cc.emulator.ui.swing;

import cc.emulator.core.cpu.register.DividableRegister;

import javax.swing.*;
import java.util.Vector;

/**
 * @author Shao Yongqing
 * Date: 2017/10/17.
 */
public class RegisterPane extends JPanel{
    AccumulatorZone accumulatorZone;
    public RegisterPane(){
        initUi();
    }

    private void initUi(){
        accumulatorZone =  new AccumulatorZone();
        accumulatorZone.initUi();
    }

    public void setAccumulators(DividableRegister[] dividableRegisters) {
        if(dividableRegisters==null)
            return;

        Vector<DividableRegister> registers = new Vector<DividableRegister>();
        for(int i=0; i<dividableRegisters.length; i++){
            registers.add(dividableRegisters[i]);
        }
        accumulatorZone.setDividableRegisters(registers);
    }

}

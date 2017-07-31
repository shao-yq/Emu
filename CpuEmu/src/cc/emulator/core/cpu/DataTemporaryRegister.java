package cc.emulator.core.cpu;

import cc.emulator.core.cpu.bus.DataBus;
import cc.emulator.core.cpu.register.GeneralRegister;

/**
 * @author Shao Yongqing
 * Date: 2017/7/26.
 */
public class DataTemporaryRegister extends GeneralRegister implements  DataListener {
    public DataTemporaryRegister(String name, int dataWidth) {
        super(name, dataWidth);
    }

    @Override
    public void dataReady(DataBus dataBus) {
        int w = dataBus.getDataWidth();
        data = dataBus.readData();
        if(w==MemoryAccessor.BYTE8){
            int data2 = dataBus.readData2();
        }

    }

}

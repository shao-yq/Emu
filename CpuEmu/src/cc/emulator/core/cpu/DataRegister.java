package cc.emulator.core.cpu;

/**
 * @author Shao Yongqing
 * Date: 2017/7/26.
 */
public class DataRegister implements  DataListener {
    @Override
    public void dataReady(DataBus dataBus) {
        int w = dataBus.getDataWidth();
        int data = dataBus.readData();
        if(w==MemoryAccessor.BYTE8){
            int data2 = dataBus.readData2();
        }

    }

}

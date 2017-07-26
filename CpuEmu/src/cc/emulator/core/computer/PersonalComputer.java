package cc.emulator.core.computer;

import cc.emulator.core.cpu.Cpu;
import cc.emulator.core.cpu.intel.Intel8086;

/**
 * @author Shao Yongqing
 * Date: 2017/7/25.
 */
public class PersonalComputer  extends BaseComputer {


    public PersonalComputer(String configFile) {
        super(configFile);
    }


    protected Cpu createCpu(MemoryManager mm){
        return new Intel8086(mm);
    }

    protected MemoryManager createMemoryManager(){
        return new  MemoryManager();
    }

}

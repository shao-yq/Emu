package cc.emulator.core.computer;

import cc.emulator.core.*;
import cc.emulator.core.cpu.Cpu;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Shao Yongqing
 * Date: 2017/7/25.
 */
public abstract class PersonalComputer implements Computer {
    public static final String BIOS = "bios";
    public static final String BOOTLOADER = "bootloader";
    public static final String RESOURCE = "resource";
    public static final String BASE = "base";

    protected String configFile;
    protected Properties properties;

    protected Cpu cpu;
    protected MemoryManager memory;

    public PersonalComputer(String configFile) {
        this.configFile = configFile;
    }

    protected void loadConfigProperties(String configFile) throws IOException {
        InputStream is = getClass().getResourceAsStream(configFile);
        properties.load(is);
        is.close();
    }
    protected abstract Cpu createCpu(MemoryManager mm);
    protected abstract MemoryManager createMemoryManager();

    @Override
    public void reset() {
        properties =  new Properties();
        try {
            loadConfigProperties(configFile);
        } catch ( Exception e) {
            e.printStackTrace();
        }

        memory =  createMemoryManager();
        memory.reset();
        cpu =  createCpu(memory);
        cpu.reset();
        //cpu.setMemory(memory.getMemoryBase());

        memory.addDataListener(cpu.getMemoryAccessor().getDataRegister());

        createPeripherals();
    }
    protected DirectMemoryAccess dma ;
    protected ProgrammableInterrupt pic;
    protected ProgrammableIntervalTimer pit;
    protected ProgrammablePeripheralInterface ppi;
    protected DisplayController crtc;
    protected   Display display;//         = new IBMCGA(this, ppi, crtc);

    protected void createPeripherals() {
        dma = createDmaController();
        pic = createProgrammableInterruptController();
        pit = createProgrammableIntervalTimer(pic);
        ppi = createProgrammablePeripheralInterface(pic);
        crtc = createDisplayController();
        display =  createDisplay();
    }

    protected abstract Display createDisplay();

    protected abstract DisplayController createDisplayController();

    protected abstract ProgrammablePeripheralInterface createProgrammablePeripheralInterface(ProgrammableInterrupt pic);

    protected abstract ProgrammableIntervalTimer createProgrammableIntervalTimer(ProgrammableInterrupt pic);

    protected abstract ProgrammableInterrupt createProgrammableInterruptController();

    protected abstract DirectMemoryAccess createDmaController();

    private void start() {
        cpu.run();
    }


    protected void loadBios(int base, String biosResource) throws Exception{
        memory.load(base, biosResource);
    }

    private void loadBootloader(int base, String res) throws Exception{
        memory.load(base, res);
    }

    @Override
    public void powerDown() {

    }

    @Override
    public void powerUp() {
        reset();
        try {
            loadBios(getBiosBase(), getBiosResource());
            loadBootloader(getBootloaderBase(), getBootloaderResource());
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getBiosResource() {
        return (String)properties.get(BIOS+"."+RESOURCE);
    }

    public String getBootloaderResource() {
        return (String)properties.get(BOOTLOADER+"."+RESOURCE);
    }

    public int getBiosBase() {
        int base = 0;
        String baseStr = (String)properties.get(BIOS+"."+BASE);
        if(baseStr != null) {
            int ii = baseStr.indexOf("0x");
            if(ii>=0)
                baseStr = baseStr.substring(2);
            base = Integer.parseInt(baseStr,16);
        }

        return base;
    }

    public int getBootloaderBase() {
        int base = 0;
        String baseStr = (String)properties.get(BOOTLOADER+"."+BASE);
        if(baseStr != null) {
            int ii = baseStr.indexOf("0x");
            if(ii>=0)
                baseStr = baseStr.substring(2);
            base = Integer.parseInt(baseStr,16);
        }

        return base;
    }
}

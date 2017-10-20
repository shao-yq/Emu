package cc.emulator.core.computer;

import cc.emulator.core.MemoryManager;
import cc.emulator.core.cpu.Cpu;

import java.util.HashMap;

public abstract class AbstractMainBoard implements MainBoard{
    private MemoryManager memoryManager;
    public final static String BIOS = "bios";
    public final static String BOOTLOADER = "bootloader";

    HashMap<String, ProgramMemoryInfo> programMemoryInfoMap = new HashMap<String, ProgramMemoryInfo>();


    ProgramMemoryInfo biasInfo;
    ProgramMemoryInfo bootloaderInfo;
    public AbstractMainBoard(){
        initComponents();
    }
    void initComponents(){
        memoryManager =  createMemoryManager();
        cpu =  createCpu(memoryManager);
        memoryManager.addDataListener(cpu.getMemoryAccessor().getDataTemporaryRegister());
    }

    @Override
    public MemoryManager getMemoryManager(){
        return memoryManager;
    }
    @Override
    public int load(int base, String resource) throws Exception{
        return memoryManager.load(base, resource);
    }
    public int load(ProgramMemoryInfo programMemoryInfo , int base, String resource) throws Exception{
        programMemoryInfo.base = base;
        programMemoryInfo.resource = resource;
        programMemoryInfo.size = load(base, resource);

        return programMemoryInfo.size;
    }

    @Override
    public int loadBios(int base, String resource) throws Exception{
        ProgramMemoryInfo programMemoryInfo =  programMemoryInfoMap.get(BIOS);
        if(programMemoryInfo==null){
            programMemoryInfo =  new ProgramMemoryInfo();
            programMemoryInfoMap.put(BIOS, programMemoryInfo);
        }

        return load(programMemoryInfo, base, resource);
    }

    public int loadBootloader(int base, String resource) throws Exception{
        ProgramMemoryInfo programMemoryInfo =  programMemoryInfoMap.get(BOOTLOADER);
        if(programMemoryInfo==null){
            programMemoryInfo =  new ProgramMemoryInfo();
            programMemoryInfoMap.put(BOOTLOADER, programMemoryInfo);
        }

        return load(programMemoryInfo, base, resource);
    }

    public ProgramMemoryInfo getProgramMemoryInfo(String programName){
        return programMemoryInfoMap.get(programName);
    }

    @Override
    public Cpu getCpu() {
        return cpu;
    }

    @Override
    public void run() {
        cpu.run();
    }

    private Cpu cpu;

    @Override
    public void reset() {
        if(memoryManager==null)
            memoryManager = createMemoryManager();
        memoryManager.reset();

        if(cpu==null)
            cpu = createCpu(memoryManager);
        cpu.reset();
        //cpu.setMemory(memory.getMemoryBase());

    }
    protected abstract Cpu createCpu(MemoryManager mm);
    protected abstract MemoryManager createMemoryManager();

}


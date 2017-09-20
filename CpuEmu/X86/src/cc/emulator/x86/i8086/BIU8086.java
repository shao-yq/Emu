package cc.emulator.x86.i8086;

import cc.emulator.core.MemoryManager;
import cc.emulator.core.cpu.*;
import cc.emulator.core.cpu.bus.DataBus;
import cc.emulator.x86.intel.IntelAddressUnit;
import cc.emulator.core.cpu.register.ProgramCounter;
import cc.emulator.core.cpu.register.SegmentRegister;
import cc.emulator.x86.intel.IntelDataBus;
import cc.emulator.x86.intel.IntelMemoryAccessor;

public class BIU8086 extends BusInterfaceUnitImpl {

    public BIU8086(MemoryManager mm, AddressUnit addressUnit) {
        super(mm, addressUnit);
    }

    @Override
    protected ProgramCounter createProgramCounter() {
        return new ProgramCounter("IP",1);
    }

    @Override
    public SegmentRegister[] createSegmentRegisters() {
        SegmentRegister segRegisters[] = new SegmentRegister[4];
        SegmentRegister cs = new SegmentRegister("CS",2);
        SegmentRegister ds = new SegmentRegister("DS",2);
        SegmentRegister es = new SegmentRegister("ES",2);
        SegmentRegister ss = new SegmentRegister("SS",2);

        int i=0;
        segRegisters[i++]=cs;
        segRegisters[i++]=ds;
        segRegisters[i++]=es;
        segRegisters[i++]=ss;

        return segRegisters;
    }

    @Override
    public InstructionQueue createInstructionQueue() {
        return new InstructionQueue8086();
    }

    @Override
    public SegmentRegister getSegmentRegister(String name) {
        for(SegmentRegister register:getSegmentRegisters()){
            if(register.getName().equalsIgnoreCase(name)){
                return register;
            }
        }
        return null;
    }


    @Override
    public void fetchInstructions(MemoryAccessor memoryAccessor, MemoryLocator instructionLocator) {
        // Fetch instruction from memory.
        int seg = instructionLocator.getBase();
        int ip = instructionLocator.getOffset();
        //int address = getAddressUnit().getAddr(seg, ip);
        instructionQueue.reset();
        AddressUnit au= getAddressUnit();
        for (int i = 0; i < 6; ++i) {
            int addr = au.getAddr(seg, ip+i);
            int val = memoryAccessor.getMem(Intel8086InstructionSet.B,addr);
            instructionQueue.fillInstructionQueue(val);
            //queue[i] = memoryAccessor.getMem(B, addr);    // getMem(B, getAddr(cs, ip + i));
        }
    }

    @Override
    protected MemoryAccessor createMemoryAccessor(MemoryManager mm) {
        return new IntelMemoryAccessor(mm, createDataBus());
    }
    protected DataBus createDataBus() {
        return new IntelDataBus();
    }

}

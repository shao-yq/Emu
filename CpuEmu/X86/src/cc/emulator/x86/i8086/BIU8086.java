package cc.emulator.x86.i8086;

import cc.emulator.core.cpu.*;
import cc.emulator.x86.intel.IntelAddressGenerator;
import cc.emulator.core.cpu.register.PointerIndexer;
import cc.emulator.core.cpu.register.ProgramCounter;
import cc.emulator.core.cpu.register.SegmentRegister;

public class BIU8086 extends BusInterfaceUnitImpl {

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
    public PointerIndexer[] createPointerIndexers() {
        PointerIndexer pniRegisters[] = new PointerIndexer[4];
        PointerIndexer sp = new PointerIndexer("SP",2);
        PointerIndexer bp = new PointerIndexer("BP",2);
        PointerIndexer si = new PointerIndexer("SI",2);
        PointerIndexer di = new PointerIndexer("DI",2);

        int i=0;
        pniRegisters[i++]=sp;
        pniRegisters[i++]=bp;
        pniRegisters[i++]=si;
        pniRegisters[i++]=di;

        return pniRegisters;
    }

    @Override
    public AddressGenerator createAddressGenerator() {
        return new IntelAddressGenerator();
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
    public PointerIndexer getPointerIndexer(String name) {
        for(PointerIndexer register:getPointerIndexers()){
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
        int address = getAddressGenerator().getAddr(seg, ip);
        instructionQueue.reset();
        for (int i = 0; i < 6; ++i) {
            int addr = getAddressGenerator().getAddr(seg, ip+i);
            int val = memoryAccessor.getMem(Intel8086InstructionSet.B,addr);
            instructionQueue.fillInstructionQueue(val);
            //queue[i] = memoryAccessor.getMem(B, addr);    // getMem(B, getAddr(cs, ip + i));
        }
    }

//    private void fetchInstructions() {
//        //Calculate the Instruction's Address.
//        getAddressGenerator().getAddr(ins)
//        //Call Instruction Queue to fetch instruction at the Location.
//
//    }

}

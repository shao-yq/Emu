package cc.emulator.x86.i8086;

import cc.emulator.x86.i8086.instruction.*;
import cc.emulator.x86.intel.IntelDecoder;
import cc.emulator.x86.intel.IntelInstruction;
import cc.emulator.core.cpu.Instruction;

/**
 * @author Shao Yongqing
 * Date: 2017/8/9.
 */
public class Decoder8086 extends IntelDecoder {
    @Override
    protected IntelInstruction newInstruction(int raw[]) {
        IntelInstruction intelInstruction=null;
        if(ImmediateToRegister.hasOpcode(raw[0])){
            intelInstruction =  new ImmediateToRegister(raw);
        }
        return  intelInstruction;
    }


    public Instruction decode(int[] queue) {
        instr = null;
        if(ImmediateToRegister.hasOpcode(queue[0])){
            instr =  new ImmediateToRegister(queue);
        } else if(MemoryToFromAccumulator.hasOpcode(queue[0])){
            instr = new MemoryToFromAccumulator(queue);
        } else if(RegMemFromToReg.hasOpcode(queue[0])){
            instr = new RegMemFromToReg(queue);
        } else if(ImmediateToRegMem.hasOpcode(queue[0])){
            instr = new ImmediateToRegMem(queue);
        }  else if(RegMemFromToSegReg.hasOpcode(queue[0])){
            instr = new RegMemFromToSegReg(queue);
        }  else if(PushGeneralRegister.hasOpcode(queue[0])){
            instr = new PushGeneralRegister(queue);
        }  else if(PushSegmentRegister.hasOpcode(queue[0])){
            instr = new PushSegmentRegister(queue);
        }  else if(PopGeneralRegister.hasOpcode(queue[0])){
            instr = new PopGeneralRegister(queue);
        }  else if(PopSegmentRegister.hasOpcode(queue[0])){
            instr = new PopSegmentRegister(queue);
        }  else if(XchgRegMemWithReg.hasOpcode(queue[0])){
            instr = new XchgRegMemWithReg(queue);
        }  else if(XchgRegWithAcc.hasOpcode(queue[0])){
            instr = new XchgRegWithAcc(queue);
        }else if(TranslateSourceTable.hasOpcode(queue[0])){
            instr = new TranslateSourceTable(queue);
        }else if(InVariablePort.hasOpcode(queue[0])){
            instr = new InVariablePort(queue);
        }else if(InFixedPort.hasOpcode(queue[0])){
            instr = new InFixedPort(queue);
        }else if(OutVariablePort.hasOpcode(queue[0])){
            instr = new OutVariablePort(queue);
        }else if(LoadEffectiveAddress.hasOpcode(queue[0])){
            instr = new LoadEffectiveAddress(queue);
        }else if(LoadPointerUsingDSES.hasOpcode(queue[0])){
            instr = new LoadPointerUsingDSES(queue);
        }else if(LoadStoreAHFromFlag.hasOpcode(queue[0])){
            instr = new LoadStoreAHFromFlag(queue);
        }else if(PushPopFlags.hasOpcode(queue[0])){
            instr = new PushPopFlags(queue);
        }else if(AddRegisterMemory.hasOpcode(queue[0])){
            instr = new AddRegisterMemory(queue);
        }else if(AddAccumulatorImmediate.hasOpcode(queue[0])){
            instr = new AddAccumulatorImmediate(queue);
        }else if(AddcRegisterMemory.hasOpcode(queue[0])){
            instr = new AddcRegisterMemory(queue);
        }else if(AddcAccumulatorImmediate.hasOpcode(queue[0])){
            instr = new AddcAccumulatorImmediate(queue);
        }else if(IncRegister.hasOpcode(queue[0])){
            instr = new IncRegister(queue);
        }else if(DecRegister.hasOpcode(queue[0])){
            instr = new DecRegister(queue);
        }

        if(instr==null){
            // if none of above instruction applied, try the universal instruction
            instr =  new Instruction8086(queue);
//            instr.op = queue[0];
//            instr.d = instr.op >>> 1 & 0b1;
//            instr.w = instr.op & 0b1;
        }

        //ip = ip + 1 & 0xffff; // Increment IP.

        return instr;
    }

    public Instruction decode2(int[] queue) {
        instr.mod = queue[1] >>> 6 & 0b11;
        instr.reg = queue[1] >>> 3 & 0b111;
        instr.rm  = queue[1]       & 0b111;

//        if (instr.mod == 0b01)
//            // 8-bit displacement follows
//            ip = ip + 2 & 0xffff;
//        else if (instr.mod == 0b00 && instr.rm == 0b110 || instr.mod == 0b10)
//            // 16-bit displacement follows
//            ip = ip + 3 & 0xffff;
//        else
//            // No displacement
//            ip = ip + 1 & 0xffff;

        /**
         * Register mode/Memory mode with displacement length
         *  CODE        Explanation
         *   00         Memory Mode, no displacement follows (except whe R/M=110, 16-bit displacement follows)
         *   01         Memory Mode,  8-bit displacement follows
         *   10         Memory Mode, 16-bit displacement follows
         *   11         Register Mode (no displacement)
         */

        switch(instr.mod){
            case Intel8086InstructionSet.MOD_MEMORY_DISP0:      //  0b00:
                if(instr.rm == 0b110){
                    instr.disp = queue[3] << 8 | queue[2];
                }
                break;
            case Intel8086InstructionSet.MOD_MEMORY_DISP8:  //  0b01:
                // 8-bit displacement follows
                instr.disp = queue[2];
                break;
            case Intel8086InstructionSet.MOD_MEMORY_DISP16:  //  0b10:
                // 16-bit displacement follows
                instr.disp = queue[3] << 8 | queue[2];
                break;
        }

        return instr;
    }

}

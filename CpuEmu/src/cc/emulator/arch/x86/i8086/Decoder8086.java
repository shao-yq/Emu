package cc.emulator.arch.x86.i8086;

import cc.emulator.arch.x86.i8086.instruction.ImmediateToRegMem;
import cc.emulator.arch.x86.i8086.instruction.ImmediateToRegister;
import cc.emulator.arch.x86.i8086.instruction.MemoryToFromAccumulator;
import cc.emulator.arch.x86.i8086.instruction.RegMemFromToReg;
import cc.emulator.arch.x86.intel.IntelDecoder;
import cc.emulator.arch.x86.intel.IntelInstruction;
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

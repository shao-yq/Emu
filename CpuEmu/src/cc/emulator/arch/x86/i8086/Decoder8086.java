package cc.emulator.arch.x86.i8086;

import cc.emulator.arch.x86.intel.IntelDecoder;
import cc.emulator.arch.x86.intel.IntelInstruction;
import cc.emulator.core.cpu.Instruction;

/**
 * @author Shao Yongqing
 * Date: 2017/8/9.
 */
public class Decoder8086 extends IntelDecoder {
    @Override
    protected IntelInstruction newInstruction() {
        return new Instruction8086();
    }


    public Instruction decode(int[] queue) {
        instr = newInstruction();    // new IntelInstruction();
        instr.op = queue[0];
        instr.d  = instr.op >>> 1 & 0b1;
        instr.w  = instr.op       & 0b1;

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
            case 0b00:
                if(instr.rm == 0b110){
                    instr.data = queue[3] << 8 | queue[2];
                }
                break;
            case 0b01:
                // 8-bit displacement follows
                instr.disp = queue[2];
                break;
            case 0b10:
                // 16-bit displacement follows
                instr.disp = queue[3] << 8 | queue[2];
                break;
        }

        return instr;
    }

}

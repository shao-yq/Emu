package cc.emulator.core.cpu.intel;

import cc.emulator.core.cpu.Decodable;
import cc.emulator.core.cpu.Instruction;

/**
 * @author Shao Yongqing
 * Date: 2017/7/27.
 */
public class IntelDecoder implements Decodable {
    IntelInstruction instr;
    @Override
    public Instruction decode(int[] queue) {
        instr = new IntelInstruction();
        instr.op = queue[0];
        instr.d  = instr.op >>> 1 & 0b1;
        instr.w  = instr.op       & 0b1;

        //ip = ip + 1 & 0xffff; // Increment IP.

        return instr;
    }
    @Override
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

        return instr;
    }
}

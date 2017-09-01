package cc.emulator.x86.i8086.instruction;

import cc.emulator.core.cpu.Instruction;
import cc.emulator.x86.i8086.Intel8086InstructionSet;
import cc.emulator.x86.intel.IntelInstruction;

/**
 * @author Shao Yongqing
 * Date: 2017/9/1.
 */
public class ExtDecoder implements Intel8086InstructionSet{
    public static boolean hasOpcode(int raw) {
        switch (raw) {
            /*
             * GROUP 1
             */
            case EXT_0X80:  // 0x80:
                // ADD REG8/MEM8,IMMED8
                // OR REG8/MEM8,IMMED8
                // ADC REG8/MEM8,IMMED8
                // SBB REG8/MEM8,IMMED8
                // AND REG8/MEM8,IMMED8
                // SUB REG8/MEM8,IMMED8
                // XOR REG8/MEM8,IMMED8
                // CMP REG8/MEM8,IMMED8
            case EXT_0X81:  // 0x81:
                // ADD REG16/MEM16,IMMED16
                // OR REG16/MEM16,IMMED16
                // ADC REG16/MEM16,IMMED16
                // SBB REG16/MEM16,IMMED16
                // AND REG16/MEM16,IMMED16
                // SUB REG16/MEM16,IMMED16
                // XOR REG16/MEM16,IMMED16
                // CMP REG16/MEM16,IMMED16
            case EXT_0X82:  // 0x82:
                // ADD REG8/MEM8,IMMED8
                // ADC REG8/MEM8,IMMED8
                // SBB REG8/MEM8,IMMED8
                // SUB REG8/MEM8,IMMED8
                // CMP REG8/MEM8,IMMED8
            case EXT_0X83:  // 0x83:
                // ADD REG16/MEM16,IMMED8
                // ADC REG16/MEM16,IMMED8
                // SBB REG16/MEM16,IMMED8
                // SUB REG16/MEM16,IMMED8
                // CMP REG16/MEM16,IMMED8
                return true;

        }
        return false;
    }

    public static IntelInstruction decode(int[] raw) {
        IntelInstruction instr = null;
        int reg = raw[1] >>> 3 & 0b111;
        switch(reg) {
            case MOD_ADD: //   0b000: // ADD
                instr =  new AddRegisterMemoryImmediate(raw);
                break;
            case MOD_OR : //   0b001: // OR
                instr =  new OrRegisterMemoryImmediate(raw);
                break;
            case MOD_ADC: //   0b010: // ADC
                instr =  new AddcRegisterMemoryImmediate(raw);
                break;
            case MOD_SBB: //   0b011: // SBB
                instr =  new SbbRegisterMemoryImmediate(raw);
                break;
            case MOD_AND: //   0b100: // AND
                instr =  new AndRegisterMemoryImmediate(raw);
                break;
            case MOD_SUB: //   0b101: // SUB
                instr =  new SubRegisterMemoryImmediate(raw);
                break;
            case MOD_XOR: //   0b110: // XOR
                instr =  new XorRegisterMemoryImmediate(raw);
                break;
            case MOD_CMP: //   0b111: // CMP
                instr =  new CmpRegisterMemoryImmediate(raw);
                break;
        }
        return instr;
    }
}

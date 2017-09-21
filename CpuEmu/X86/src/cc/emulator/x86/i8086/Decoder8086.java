package cc.emulator.x86.i8086;

import cc.emulator.x86.i8086.instruction.AdcAccumulatorImmediate;
import cc.emulator.x86.intel.IntelDecoder;
import cc.emulator.x86.intel.IntelInstruction;
import cc.emulator.core.cpu.Instruction;

/**
 * @author Shao Yongqing
 * Date: 2017/8/9.
 */
public class Decoder8086 extends IntelDecoder {
    public Decoder8086(){
        super();
    }

    protected String getPackagePrefix() {
        return  "cc.emulator.x86.i8086.instruction.";
    }

    protected  void initInstructionNames(){
        instructionNames = new String[] {
                "AdcAccumulatorImmediate",
                "AdcRegisterMemory",
                //"AdcRegisterMemoryImmediate",
                "AddAccumulatorImmediate",
                "AddRegisterMemory",
                //"AddRegisterMemoryImmediate",
                "AndAccumulatorImmediate",
                "AndRegisterMemory",
                //"AndRegisterMemoryImmediate",
                "AsciiAdjustForAddition",
                "AsciiAdjustForDivision",
                "AsciiAdjustForMultiply",
                "AsciiAdjustForSubstraction",
                "CallFarProc",
                "CallIndirect",
                "CallNearProc",
                "CmpAccumulatorImmediate",
                "CmpRegisterMemory",
                //"CmpRegisterMemoryImmediate",
                "CompareString",
                "ControlOp",
                "ConvertByteWord",
                "DecimalAdjustForAddition",
                "DecimalAdjustForSubstraction",
                "DecRegister",
                "DecRegisterMemory",
                "DivRegisterMemory",
                "Escape",
                //"ExtDecoder",
                "FlagOp",
                //"FlagsFromToAH",
                //"FlagsFromToStack",
                "IncRegister",
                "IncRegisterMemory",
                "InFixedPort",
                "Interrupt",
                "InterruptReturn",
                "InVariablePort",
                "JmpIndirect",
                "Jump",
                "JumpIf",
                "LoadEffectiveAddress",
                "LoadPointerUsingDSES",
                "LoadStoreAHFromFlag",
                "LoadString",
                "Loop",
                //"MOV",
                "MoveString",
                "MovImmediateToRegister",
                "MovImmediateToRegMem",
                "MovMemoryToFromAccumulator",
                "MovRegMemFromToReg",
                "MovRegMemFromToSegReg",
                "MulRegisterMemory",
                "NegRegisterMemory",
                "NotRegisterMemory",
                "OrAccumulatorImmediate",
                "OrRegisterMemory",
                //"OrRegisterMemoryImmediate",
                "OutFixedPort",
                "OutVariablePort",
                "PopGeneralRegister",
                "PopRegisterMemory",
                "PopSegmentRegister",
                //"Prefix",
                "PushGeneralRegister",
                "PushPopFlags",
                "PushRegisterMemory",
                "PushSegmentRegister",
                "ReturnImmediate",
                "ReturnNoImmed",
                "SbbAccumulatorImmediate",
                "SbbRegisterMemory",
                //"SbbRegisterMemoryImmediate",
                "ScanString",
                "ShiftRotate",
                "StoreString",
                "SubAccumulatorImmediate",
                "SubRegisterMemory",
                //"SubRegisterMemoryImmediate",
                "TestAccumulatorImmediate",
                "TestRegisterMemory",
                "TestRegisterMemoryImmediate",
                "TranslateSourceTable",
                "XchgRegMemWithReg",
                "XchgRegWithAcc",
                "XorAccumulatorImmediate",
                "XorRegisterMemory"
                //"XorRegisterMemoryImmediate"
        };
    }

    int checkPrefix(int[] queue){
        int opcodeIndex=0;
        boolean prefix = false;
        do {
            switch (queue[opcodeIndex]){
                //
                case cc.emulator.x86.i8086.Intel8086InstructionSet.PREFIX_ES: //  0x26: // ES: (segment override prefix)
                case cc.emulator.x86.i8086.Intel8086InstructionSet.PREFIX_CS: //  0x2e: // CS: (segment override prefix)
                case cc.emulator.x86.i8086.Intel8086InstructionSet.PREFIX_SS: //  0x36: // SS: (segment override prefix)
                case cc.emulator.x86.i8086.Intel8086InstructionSet.PREFIX_DS: //  0x3e: // DS: (segment override prefix)

                //
                case cc.emulator.x86.i8086.Intel8086InstructionSet.PREFIX_REPNEZ: //  0xf2: // REPNE/REPNZ
                case cc.emulator.x86.i8086.Intel8086InstructionSet.PREFIX_REPEZ: //  0xf3: // REP/REPE/REPZ
                    prefix = true;
                    opcodeIndex++;
                    break;
                default:
                    prefix = false;
            }

        } while(opcodeIndex<4 && prefix==true);

        return opcodeIndex;
    }
    public Instruction decode(int[] queue) {
        IntelInstruction instr = null;
        // 1. Check Prifx(es)
        int startIndex= checkPrefix(queue);

        // 2. Decode opcode
        // Check if there is any Instruction has such opcode
        for(int i=0; i<instructions.length; i++){
            if(instructions[i].hasOpcode(queue, startIndex)){
                instr = (IntelInstruction) instructions[i].clone();
                instr.decodeMe(queue, startIndex);
                break;
            }
        }

        // Check if the instruction already in hand
        if(instr ==null){
            // Try the extend decoder
            if(ExtDecoder.hasOpcode(queue, startIndex)){
                instr = ExtDecoder.decode(queue, startIndex);
            }
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

    // Test code, would be removed later
    public static void main(final String[] args) {
        int queue[] = {0x14,1,2,3,4,5};
        int start = 0;
        AdcAccumulatorImmediate adc = new AdcAccumulatorImmediate(queue, start);

    }

}

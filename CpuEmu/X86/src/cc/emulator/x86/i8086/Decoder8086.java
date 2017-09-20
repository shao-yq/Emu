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

    static Class instructionClasses[]=null;
    static Instruction instructions[]=null;

    static String instructionNames[]={
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

    static String packagePrefix = "cc.emulator.x86.i8086.instruction.";
    // Initialize the instruction classes
    static void initInstructions(){
        instructions = new IntelInstruction[instructionNames.length];

        instructionClasses = new Class[instructionNames.length];
        for(int i=0; i<instructionNames.length; i++){
            try {
                instructionClasses[i] = Class.forName(packagePrefix+instructionNames[i]);
                try {
                    instructions[i] = (IntelInstruction) instructionClasses[i].newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
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
        instr = null;
        // 1. Check Prifx(es)
        int startIndex= checkPrefix(queue);

        // 2. Decode opcode
        if(instructionClasses==null){
            initInstructions();
        }
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

    // Test code, would be removed later
    public static void main(final String[] args) {
        int queue[] = {0x14,1,2,3,4,5};
        int start = 0;
        AdcAccumulatorImmediate adc = new AdcAccumulatorImmediate(queue, start);

    }

}

package cc.emulator.x86.i8086;

import cc.emulator.x86.i8086.instruction.*;
import cc.emulator.x86.i8086.instruction.MovImmediateToRegister;
import cc.emulator.x86.i8086.instruction.MovRegMemFromToReg;
import cc.emulator.x86.intel.IntelDecoder;
import cc.emulator.x86.intel.IntelInstruction;
import cc.emulator.core.cpu.Instruction;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Shao Yongqing
 * Date: 2017/8/9.
 */
public class Decoder8086 extends IntelDecoder {

    static Class instructionClasses[]=null;
    static Method instructionMethods[]=null;
    static Constructor instructionConstructors[]=null;

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
        instructionClasses = new Class[instructionNames.length];
        instructionMethods = new Method[instructionNames.length];
        instructionConstructors = new Constructor [instructionNames.length];

        for(int i=0; i<instructionNames.length; i++){
            try {
                instructionClasses[i] = Class.forName(packagePrefix+instructionNames[i]);
                try {
                    instructionMethods[i] =  instructionClasses[i].getDeclaredMethod("hasOpcode", new Class[] {int[].class, int.class});
                    instructionMethods[i].setAccessible(true);
                    instructionConstructors[i] = instructionClasses[i].getConstructor(new Class[]{int[].class, int.class});
                    instructionConstructors[i].setAccessible(true);

                } catch (NoSuchMethodException e) {
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
//        if(instructionClasses==null){
//            initInstructions();
//        }
//        // Check if there is any Instruction has such opcode
//        for(int i=0; i<instructionClasses.length; i++){
//            Class aClass = instructionClasses[i];
//
//            Method method = instructionMethods[i];       //  aClass.getDeclaredMethod("hasOpcode", new Class[] {int[].class, int.class});
//            try {
//                // If method valid
//                if(method!=null) {
//                    Object ret = method.invoke(null, new Object[]{queue, startIndex});
//                    // Check if the opcode in the instruction
//                    if ((boolean) ret == true) {
//                        Constructor constructor = instructionConstructors[i];
//                        try {
//                            // Construct the instruction instance
//                            instr = (IntelInstruction) constructor.newInstance(queue, startIndex);
//                            break;
//                        } catch (InstantiationException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//
//
//        }
//        // Check if the instruction already in hand
//        if(instr ==null){
//            // Try the extend decoder
//            if(ExtDecoder.hasOpcode(queue, startIndex)){
//                instr = ExtDecoder.decode(queue, startIndex);
//            }
//        }

        if(MovImmediateToRegister.hasOpcode(queue, startIndex)){
            instr =  new MovImmediateToRegister(queue, startIndex);
        } else if(MovMemoryToFromAccumulator.hasOpcode(queue, startIndex)){
            instr = new MovMemoryToFromAccumulator(queue, startIndex);
        } else if(MovRegMemFromToReg.hasOpcode(queue, startIndex)){
            instr = new MovRegMemFromToReg(queue, startIndex);
        } else if(MovImmediateToRegMem.hasOpcode(queue, startIndex)){
            instr = new MovImmediateToRegMem(queue, startIndex);
        }  else if(MovRegMemFromToSegReg.hasOpcode(queue, startIndex)){
            instr = new MovRegMemFromToSegReg(queue, startIndex);
        }  else if(PushGeneralRegister.hasOpcode(queue, startIndex)){
            instr = new PushGeneralRegister(queue, startIndex);
        }  else if(PushSegmentRegister.hasOpcode(queue, startIndex)){
            instr = new PushSegmentRegister(queue, startIndex);
        }  else if(PopGeneralRegister.hasOpcode(queue, startIndex)){
            instr = new PopGeneralRegister(queue, startIndex);
        }  else if(PopSegmentRegister.hasOpcode(queue, startIndex)){
            instr = new PopSegmentRegister(queue, startIndex);
        }  else if(XchgRegMemWithReg.hasOpcode(queue, startIndex)){
            instr = new XchgRegMemWithReg(queue, startIndex);
        }  else if(XchgRegWithAcc.hasOpcode(queue, startIndex)){
            instr = new XchgRegWithAcc(queue, startIndex);
        }else if(TranslateSourceTable.hasOpcode(queue, startIndex)){
            instr = new TranslateSourceTable(queue, startIndex);
        }else if(InVariablePort.hasOpcode(queue, startIndex)){
            instr = new InVariablePort(queue, startIndex);
        }else if(InFixedPort.hasOpcode(queue, startIndex)){
            instr = new InFixedPort(queue, startIndex);
        }else if(OutVariablePort.hasOpcode(queue, startIndex)){
            instr = new OutVariablePort(queue, startIndex);
        }else if(LoadEffectiveAddress.hasOpcode(queue, startIndex)){
            instr = new LoadEffectiveAddress(queue, startIndex);
        }else if(LoadPointerUsingDSES.hasOpcode(queue, startIndex)){
            instr = new LoadPointerUsingDSES(queue, startIndex);
        }else if(LoadStoreAHFromFlag.hasOpcode(queue, startIndex)){
            instr = new LoadStoreAHFromFlag(queue, startIndex);
        }else if(PushPopFlags.hasOpcode(queue, startIndex)){
            instr = new PushPopFlags(queue, startIndex);
        }else if(AddRegisterMemory.hasOpcode(queue, startIndex)){
            instr = new AddRegisterMemory(queue, startIndex);
        }else if(AddAccumulatorImmediate.hasOpcode(queue, startIndex)){
            instr = new AddAccumulatorImmediate(queue, startIndex);
        }else if(AdcRegisterMemory.hasOpcode(queue, startIndex)){
            instr = new AdcRegisterMemory(queue, startIndex);
        }else if(AdcAccumulatorImmediate.hasOpcode(queue, startIndex)){
            instr = new AdcAccumulatorImmediate(queue, startIndex);
        }else if(AndRegisterMemory.hasOpcode(queue, startIndex)){
            instr = new AndRegisterMemory(queue, startIndex);
        }else if(AndAccumulatorImmediate.hasOpcode(queue, startIndex)){
            instr = new AndAccumulatorImmediate(queue, startIndex);
        }else if(OrRegisterMemory.hasOpcode(queue, startIndex)){
            instr = new OrRegisterMemory(queue, startIndex);
        }else if(OrAccumulatorImmediate.hasOpcode(queue, startIndex)){
            instr = new OrAccumulatorImmediate(queue, startIndex);
        }else if(XorRegisterMemory.hasOpcode(queue, startIndex)){
            instr = new XorRegisterMemory(queue, startIndex);
        }else if(XorAccumulatorImmediate.hasOpcode(queue, startIndex)){
            instr = new XorAccumulatorImmediate(queue, startIndex);
        }else if(IncRegister.hasOpcode(queue, startIndex)){
            instr = new IncRegister(queue, startIndex);
        }else if(DecRegister.hasOpcode(queue, startIndex)){
            instr = new DecRegister(queue, startIndex);
        }else if(AsciiAdjustForAddition.hasOpcode(queue, startIndex)){
            instr = new AsciiAdjustForAddition(queue, startIndex);
        }else if(DecimalAdjustForAddition.hasOpcode(queue, startIndex)){
            instr = new DecimalAdjustForAddition(queue, startIndex);
        }else if(AsciiAdjustForSubstraction.hasOpcode(queue, startIndex)){
            instr = new AsciiAdjustForSubstraction(queue, startIndex);
        }else if(DecimalAdjustForSubstraction.hasOpcode(queue, startIndex)){
            instr = new DecimalAdjustForSubstraction(queue, startIndex);
        }else if(AsciiAdjustForMultiply.hasOpcode(queue, startIndex)){
            instr = new AsciiAdjustForMultiply(queue, startIndex);
        }else if(AsciiAdjustForDivision.hasOpcode(queue, startIndex)){
            instr = new AsciiAdjustForDivision(queue, startIndex);
        }else if(SubRegisterMemory.hasOpcode(queue, startIndex)){
            instr = new SubRegisterMemory(queue, startIndex);
        }else if(SubAccumulatorImmediate.hasOpcode(queue, startIndex)){
            instr = new SubAccumulatorImmediate(queue, startIndex);
        }else if(SbbRegisterMemory.hasOpcode(queue, startIndex)){
            instr = new SbbRegisterMemory(queue, startIndex);
        }else if(SbbAccumulatorImmediate.hasOpcode(queue, startIndex)){
            instr = new SbbAccumulatorImmediate(queue, startIndex);
        }else if(CmpRegisterMemory.hasOpcode(queue, startIndex)){
            instr = new CmpRegisterMemory(queue, startIndex);
        }else if(CmpAccumulatorImmediate.hasOpcode(queue, startIndex)){
            instr = new CmpAccumulatorImmediate(queue, startIndex);
        }else if(TestRegisterMemory.hasOpcode(queue, startIndex)){
            instr = new TestRegisterMemory(queue, startIndex);
        }else if(TestAccumulatorImmediate.hasOpcode(queue, startIndex)){
            instr = new TestAccumulatorImmediate(queue, startIndex);
        }else if(ConvertByteWord.hasOpcode(queue, startIndex)){
            instr = new ConvertByteWord(queue, startIndex);
        }else if(MoveString.hasOpcode(queue, startIndex)){
            instr = new MoveString(queue, startIndex);
        }else if(CompareString.hasOpcode(queue, startIndex)){
            instr = new CompareString(queue, startIndex);
        }else if(ScanString.hasOpcode(queue, startIndex)){
            instr = new ScanString(queue, startIndex);
        }else if(LoadString.hasOpcode(queue, startIndex)){
            instr = new LoadString(queue, startIndex);
        }else if(StoreString.hasOpcode(queue, startIndex)){
            instr = new StoreString(queue, startIndex);
        }else if(CallNearProc.hasOpcode(queue, startIndex)){
            instr = new CallNearProc(queue, startIndex);
        }else if(CallFarProc.hasOpcode(queue, startIndex)){
            instr = new CallFarProc(queue, startIndex);
        }else if(ReturnNoImmed.hasOpcode(queue, startIndex)){
            instr = new ReturnNoImmed(queue, startIndex);
        }else if(ReturnImmediate.hasOpcode(queue, startIndex)){
            instr = new ReturnImmediate(queue, startIndex);
        }else if(Jump.hasOpcode(queue, startIndex)){
            instr = new Jump(queue, startIndex);
        }else if(JumpIf.hasOpcode(queue, startIndex)){
            instr = new JumpIf(queue, startIndex);
        }else if(Loop.hasOpcode(queue, startIndex)){
            instr = new Loop(queue, startIndex);
        }else if(Interrupt.hasOpcode(queue, startIndex)){
            instr = new Interrupt(queue, startIndex);
        }else if(InterruptReturn.hasOpcode(queue, startIndex)){
            instr = new InterruptReturn(queue, startIndex);
        }else if(Escape.hasOpcode(queue, startIndex)){
            instr = new Escape(queue, startIndex);
        }else if(FlagOp.hasOpcode(queue, startIndex)){
            instr = new FlagOp(queue, startIndex);
        }else if(ControlOp.hasOpcode(queue, startIndex)){
            instr = new ControlOp(queue, startIndex);
        }else if(ShiftRotate.hasOpcode(queue, startIndex)){
            instr = new ShiftRotate(queue, startIndex);
        }else if(MulRegisterMemory.hasOpcode(queue, startIndex)){
            instr = new MulRegisterMemory(queue, startIndex);
        }else if(DivRegisterMemory.hasOpcode(queue, startIndex)){
            instr = new DivRegisterMemory(queue, startIndex);
        }else if(NotRegisterMemory.hasOpcode(queue, startIndex)){
            instr = new NotRegisterMemory(queue, startIndex);
        }else if(NegRegisterMemory.hasOpcode(queue, startIndex)){
            instr = new NegRegisterMemory(queue, startIndex);
        }else if(TestRegisterMemoryImmediate.hasOpcode(queue, startIndex)){
            instr = new TestRegisterMemoryImmediate(queue, startIndex);
        }else if(IncRegisterMemory.hasOpcode(queue, startIndex)){
            instr = new IncRegisterMemory(queue, startIndex);
        }else if(DecRegisterMemory.hasOpcode(queue, startIndex)){
            instr = new DecRegisterMemory(queue, startIndex);
        }else if(CallIndirect.hasOpcode(queue, startIndex)){
            instr = new CallIndirect(queue, startIndex);
        }else if(JmpIndirect.hasOpcode(queue, startIndex)){
            instr = new JmpIndirect(queue, startIndex);
        }else if(PushRegisterMemory.hasOpcode(queue, startIndex)){
            instr = new PushRegisterMemory(queue, startIndex);
        }else if(PopRegisterMemory.hasOpcode(queue, startIndex)){
            instr = new PopRegisterMemory(queue, startIndex);
        }else if(ExtDecoder.hasOpcode(queue, startIndex)){
            instr = ExtDecoder.decode(queue, startIndex);
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

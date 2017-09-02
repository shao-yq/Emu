package cc.emulator.x86.i8086;

import cc.emulator.x86.i8086.instruction.*;
import cc.emulator.x86.i8086.instruction.MovImmediateToRegister;
import cc.emulator.x86.i8086.instruction.MovRegMemFromToReg;
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
        if(MovImmediateToRegister.hasOpcode(raw[0])){
            intelInstruction =  new MovImmediateToRegister(raw);
        }
        return  intelInstruction;
    }


    public Instruction decode(int[] queue) {
        instr = null;
        if(MovImmediateToRegister.hasOpcode(queue[0])){
            instr =  new MovImmediateToRegister(queue);
        } else if(MovMemoryToFromAccumulator.hasOpcode(queue[0])){
            instr = new MovMemoryToFromAccumulator(queue);
        } else if(MovRegMemFromToReg.hasOpcode(queue[0])){
            instr = new MovRegMemFromToReg(queue);
        } else if(MovImmediateToRegMem.hasOpcode(queue[0])){
            instr = new MovImmediateToRegMem(queue);
        }  else if(MovRegMemFromToSegReg.hasOpcode(queue[0])){
            instr = new MovRegMemFromToSegReg(queue);
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
        }else if(AdcRegisterMemory.hasOpcode(queue[0])){
            instr = new AdcRegisterMemory(queue);
        }else if(AdcAccumulatorImmediate.hasOpcode(queue[0])){
            instr = new AdcAccumulatorImmediate(queue);
        }else if(AndRegisterMemory.hasOpcode(queue[0])){
            instr = new AndRegisterMemory(queue);
        }else if(AndAccumulatorImmediate.hasOpcode(queue[0])){
            instr = new AndAccumulatorImmediate(queue);
        }else if(OrRegisterMemory.hasOpcode(queue[0])){
            instr = new OrRegisterMemory(queue);
        }else if(OrAccumulatorImmediate.hasOpcode(queue[0])){
            instr = new OrAccumulatorImmediate(queue);
        }else if(XorRegisterMemory.hasOpcode(queue[0])){
            instr = new XorRegisterMemory(queue);
        }else if(XorAccumulatorImmediate.hasOpcode(queue[0])){
            instr = new XorAccumulatorImmediate(queue);
        }else if(IncRegister.hasOpcode(queue[0])){
            instr = new IncRegister(queue);
        }else if(DecRegister.hasOpcode(queue[0])){
            instr = new DecRegister(queue);
        }else if(AsciiAdjustForAddition.hasOpcode(queue[0])){
            instr = new AsciiAdjustForAddition(queue);
        }else if(DecimalAdjustForAddition.hasOpcode(queue[0])){
            instr = new DecimalAdjustForAddition(queue);
        }else if(AsciiAdjustForSubstraction.hasOpcode(queue[0])){
            instr = new AsciiAdjustForSubstraction(queue);
        }else if(DecimalAdjustForSubstraction.hasOpcode(queue[0])){
            instr = new DecimalAdjustForSubstraction(queue);
        }else if(AsciiAdjustForMultiply.hasOpcode(queue[0])){
            instr = new AsciiAdjustForMultiply(queue);
        }else if(AsciiAdjustForDivision.hasOpcode(queue[0])){
            instr = new AsciiAdjustForDivision(queue);
        }else if(SubRegisterMemory.hasOpcode(queue[0])){
            instr = new SubRegisterMemory(queue);
        }else if(SubAccumulatorImmediate.hasOpcode(queue[0])){
            instr = new SubAccumulatorImmediate(queue);
        }else if(SbbRegisterMemory.hasOpcode(queue[0])){
            instr = new SbbRegisterMemory(queue);
        }else if(SbbAccumulatorImmediate.hasOpcode(queue[0])){
            instr = new SbbAccumulatorImmediate(queue);
        }else if(CmpRegisterMemory.hasOpcode(queue[0])){
            instr = new CmpRegisterMemory(queue);
        }else if(CmpAccumulatorImmediate.hasOpcode(queue[0])){
            instr = new CmpAccumulatorImmediate(queue);
        }else if(TestRegisterMemory.hasOpcode(queue[0])){
            instr = new TestRegisterMemory(queue);
        }else if(TestAccumulatorImmediate.hasOpcode(queue[0])){
            instr = new TestAccumulatorImmediate(queue);
        }else if(ConvertByteWord.hasOpcode(queue[0])){
            instr = new ConvertByteWord(queue);
        }else if(MoveString.hasOpcode(queue[0])){
            instr = new MoveString(queue);
        }else if(CompareString.hasOpcode(queue[0])){
            instr = new CompareString(queue);
        }else if(ScanString.hasOpcode(queue[0])){
            instr = new ScanString(queue);
        }else if(LoadString.hasOpcode(queue[0])){
            instr = new LoadString(queue);
        }else if(StoreString.hasOpcode(queue[0])){
            instr = new StoreString(queue);
        }else if(CallNearProc.hasOpcode(queue[0])){
            instr = new CallNearProc(queue);
        }else if(CallFarProc.hasOpcode(queue[0])){
            instr = new CallFarProc(queue);
        }else if(ReturnNoImmed.hasOpcode(queue[0])){
            instr = new ReturnNoImmed(queue);
        }else if(ReturnImmediate.hasOpcode(queue[0])){
            instr = new ReturnImmediate(queue);
        }else if(Jump.hasOpcode(queue[0])){
            instr = new Jump(queue);
        }else if(JumpIf.hasOpcode(queue[0])){
            instr = new JumpIf(queue);
        }else if(Loop.hasOpcode(queue[0])){
            instr = new Loop(queue);
        }else if(Interrupt.hasOpcode(queue[0])){
            instr = new Interrupt(queue);
        }else if(InterruptReturn.hasOpcode(queue[0])){
            instr = new InterruptReturn(queue);
        }else if(Escape.hasOpcode(queue[0])){
            instr = new Escape(queue);
        }else if(FlagOp.hasOpcode(queue[0])){
            instr = new FlagOp(queue);
        }else if(ControlOp.hasOpcode(queue[0])){
            instr = new ControlOp(queue);
        }else if(ShiftRotate.hasOpcode(queue[0])){
            instr = new ShiftRotate(queue);
        }else if(MulRegisterMemory.hasOpcode(queue)){
            instr = new MulRegisterMemory(queue);
        }else if(DivRegisterMemory.hasOpcode(queue)){
            instr = new DivRegisterMemory(queue);
        }else if(NotRegisterMemory.hasOpcode(queue)){
            instr = new NotRegisterMemory(queue);
        }else if(NegRegisterMemory.hasOpcode(queue)){
            instr = new NegRegisterMemory(queue);
        }else if(TestRegisterMemoryImmediate.hasOpcode(queue)){
            instr = new TestRegisterMemoryImmediate(queue);
        }else if(IncRegisterMemory.hasOpcode(queue)){
            instr = new IncRegisterMemory(queue);
        }else if(DecRegisterMemory.hasOpcode(queue)){
            instr = new DecRegisterMemory(queue);
        }else if(CallIndirect.hasOpcode(queue)){
            instr = new CallIndirect(queue);
        }else if(JmpIndirect.hasOpcode(queue)){
            instr = new JmpIndirect(queue);
        }else if(PushRegisterMemory.hasOpcode(queue)){
            instr = new PushRegisterMemory(queue);
        }else if(PopRegisterMemory.hasOpcode(queue)){
            instr = new PopRegisterMemory(queue);
        }else if(ExtDecoder.hasOpcode(queue[0])){
            instr = ExtDecoder.decode(queue);
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

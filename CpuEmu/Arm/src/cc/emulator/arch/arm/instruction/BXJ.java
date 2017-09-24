package cc.emulator.arch.arm.instruction;

/**
 * BXJ
 *
 * Branch and Exchange Jazelle attempts to change to Jazelle state. If the attempt fails, it branches to an address and
 * instruction set specified by a register as though it were a BX instruction.
 * 
 * In an implementation that includes the Virtualization Extensions, if HSTR.TJDBX is set to 1, execution of a BXJ
 * instruction in a Non-secure mode other than Hyp mode generates a Hyp Trap exception. For more information see  
 * Trapping accesses to Jazelle functionality on page B1-1256.                                                    
 * 
 * Encoding T1 ARMv6T2, ARMv7
 *  BXJ<c> <Rm> Outside or last in IT block 
 * -------------------------------------------------------------------------------------------------
 * |15 14 13 12|11|10| 9| 8  7| 6| 5| 4| 3  2  1  0|15 14|13|12|11|10  9  8| 7| 6| 5| 4| 3  2  1  0|
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * | 1  1  1  1  0| 0| 1  1  1  1| 0  0| Rm        | 1  0|(0| 0|(1)(1)(1)(1|(0)(0)(0)(0)(0)(0)(0)(0|
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * 
 * m = UInt(Rm);                      
 * if m IN {13,15} then UNPREDICTABLE;
 * if InITBlock() && !LastInITBlock() then UNPREDICTABLE;
 * 
 * Encoding A1 ARMv5TEJ, ARMv6*, ARMv7
 * BXJ<c> <Rm>                        
 *  |-----------------------------------------------------------------------------------------------|
 *  |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7  6  5  4| 3  2  1  0|
 *  |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 *  | Condition | 0| 0| 0| 1| 0| 0| 1| 0|(1)(1)(1)(1)(1)(1)(1)(1)(1)(1)(1)(1| 0| 0| 1| 0|    Rm     |
 *  |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|
 * 
 * m = UInt(Rm);
 * if m == 15 then UNPREDICTABLE;
 * 
 * 
 * Assembler syntax                                                                                              
 *      BXJ{<c>}{<q>} <Rm>                                                                                            
 * where:                                                                                                        
 * <c>, <q>     See Standard assembler syntax fields on page A8-287.                                                 
 * <Rm>         The register that specifies the branch target address and instruction set selection bit to be used if the
 *              attempt to switch to Jazelle state fails.                                                                     
 * Operation                                                                                                     
 *  if ConditionPassed() then                                                                                     
 *      EncodingSpecificOperations();                                                                                 
 *      if HaveVirtExt() && !IsSecure() && !CurrentModeIsHyp() && HSTR.TJDBX == '1' then                            
 *          HSRString = Zeros(25);                                                                                        
 *          HSRString<3:0> = m;                                                                                           
 *          WriteHSR('001010', HSRString);                                                                              
 *          TakeHypTrapException();                                                                                       
 *      elsif JMCR.JE == '0' || CurrentInstrSet() == InstrSet_ThumbEE then                                          
 *          BXWritePC(R[m]);                                                                                              
 *      else                                                                                                          
 *          if JazelleAcceptsExecution() then                                                                             
 *              SwitchToJazelleExecution();                                                                                   
 *          else                                                                                                          
 *              SUBARCHITECTURE_DEFINED handler call;   
 *                                                                      
 * Exceptions                                                                                                    
 *  Hyp Trap.                                                                                                     
 *                                           
 * @author Shao Yongqing
 * Date: 2017/9/24.
 */
public class BXJ extends BranchExchange{
    public BXJ(int[] queue) {
        super(queue);
    }
    
    @Override
    public boolean hasOpcode(int[] queue, int startIndex) {
        int raw = queue[startIndex];
        int op = raw;
        if ((op & 0x0FFFFFF0) == 0x012FFF20) {
            return true;
        }
        return false;
    }
}

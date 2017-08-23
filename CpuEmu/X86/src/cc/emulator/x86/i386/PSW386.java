package cc.emulator.x86.i386;

import cc.emulator.x86.intel.ProgramStatusWord;

/**
 * @author  Shao Bofeng
 * Date: 2017/8/13
 */
public class PSW386 extends ProgramStatusWord {
    /**
     *
     *       17 16 15 14 13 12 11 10  9  8  7  6  5  4  3  2  1  0
     *       VM RF 0  NT IO_PL OF DF IF TF SF ZF x  AF x  PF x  CF
     *
     */

    /**
     * NT (Nested Task, bit 14)
     * The processor uses the nested task flag to control chaining of
     * interrupted and called tasks. NT influences the operation of the IRET
     * instruction. Refer to Chapter 7 and Chapter 9 for more information on
     * nested tasks.
     */
    public static final int   NT     = 1 << 14;

    /**
     * RF (Resume Flag, bit 16)
     * The RF flag temporarily disables debug exceptions so that an instruction
     * can be restarted after a debug exception without immediately causing
     * another debug exception. Refer to Chapter 12 for details.
     */
    public static final int   RF     = 1 << 16;

    /**
     * VM (Virtual 8086 Mode, bit 17)
     * When set, the VM flag indicates that the task is executing an 8086
     * program. Refer to Chapter 14 for a detailed discussion of how the 80386
     * executes 8086 tasks in a protected, multitasking environment.
     */
    public static final int   VM     = 1 << 17;


    public int getIOPL(){
        return (flags>>12)&0b11;
    }

    public void setIOPL(int iopl){
        flags = flags&(~(0b11<<12))|(iopl<<12);
    }
}

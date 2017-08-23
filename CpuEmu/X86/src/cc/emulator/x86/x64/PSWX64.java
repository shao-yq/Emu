package cc.emulator.x86.x64;

import cc.emulator.x86.i386.PSW386;

/**
 * @author  Shao Bofeng
 * Date: 2017/8/13
 */
public class PSWX64 extends PSW386 {
    /**
     *
     *       21 20  19  18 17 16 15 14 13 12 11 10  9  8  7  6  5  4  3  2  1  0
     *       ID VIP VIF AC VM RF 0  NT IO_PL OF DF IF TF SF ZF x  AF x  PF x  CF
     *
     */

    /**
     * AC (bit 18) Alignment check (or access control) flag
     * If the AM bit is set in the CR0 register, alignment
     * checking of user-mode data accesses is enabled if and only if this flag is 1.
     * If the SMAP bit is set in the CR4 register, explicit supervisor-mode data accesses to user-mode
     * pages are allowed if and only if this bit is 1. See Section 4.6, “Access Rights,” in the Intel® 64
     * and IA-32 Architectures Software Developer’s Manual, Volume 3A.
     */
    public static final int   AC     = 1 << 18;

    /**
     * VIF (bit 19) Virtual interrupt flag — Virtual image of the IF flag. Used in conjunction with the VIP flag.
     * (To use this flag and the VIP flag the virtual mode extensions are enabled by setting the VME
     * flag in control register CR4.)
     */
    public static final int   VIF     = 1 << 19;

    /**
     * VIP (bit 20) Virtual interrupt pending flag
     * Set to indicate that an interrupt is pending; clear when no
     * interrupt is pending. (Software sets and clears this flag; the processor only reads it.) Used in
     * conjunction with the VIF flag.
     */
    public static final int   VIP     = 1 << 20;

    /**
     * ID (bit 21) Identification flag
     * The ability of a program to set or clear this flag indicates support for
     * the CPUID instruction.
     */
    public static final int   ID     = 1 << 21;

}

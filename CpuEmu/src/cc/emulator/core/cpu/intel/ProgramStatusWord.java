package cc.emulator.core.cpu.intel;

import cc.emulator.core.cpu.register.StatusRegister;

/**
 * @author Shao Yongqing
 * Date: 2017/7/27.
 */
public class ProgramStatusWord extends StatusRegister{
    /**
     *
     *       15 14 13 12 11 10  9  8  7  6  5  4  3  2  1  0
     *       x  x  x  x  OF DF IF TF SF ZF x  AF x  PF x  CF
     *
     */

    /**
     * CF (carry flag)
     *
     * If an addition results in a carry out of the high-order bit of the
     * result, then CF is set; otherwise CF is cleared. If a subtraction
     * results in a borrow into the high-order bit of the result, then CF is
     * set; otherwise CF is cleared. Note that a signed carry is indicated by
     * CF ≠ OF. CF can be used to detect an unsigned overflow. Two
     * instructions, ADC (add with carry) and SBB (subtract with borrow),
     * incorporate the carry flag in their operations and can be used to
     * perform multibyte (e.g., 32-bit, 64-bit) addition and subtraction.
     */
    public static final int   CF     = 1 << 0;

    /**
     * PF (parity flag)
     *
     * If the low-order eight bits of an arithmetic or logical operation is
     * zero contain an even number of 1-bits, then the parity flag is set,
     * otherwise it is cleared. PF is provided for 8080/8085 compatibility; it
     * can also be used to check ASCII characters for correct parity.
     */
    public static final int   PF     = 1 << 2;

    /**
     * AF (auxiliary carry flag)
     *
     * If an addition results in a carry out of the low-order half-byte of the
     * result, then AF is set; otherwise AF is cleared. If a subtraction
     * results in a borrow into the low-order half-byte of the result, then AF
     * is set; otherwise AF is cleared. The auxiliary carry flag is provided
     * for the decimal adjust instructions and ordinarily is not used for any
     * other purpose.
     */
    public static final int   AF     = 1 << 4;

    /**
     * ZF (zero flag)
     *
     * If the result of an arithmetic or logical operation is zero, then ZF is
     * set; otherwise ZF is cleared. A conditional jump instruction can be used
     * to alter the flow of the program if the result is or is not zero.
     */
    public static final int   ZF     = 1 << 6;

    /**
     * SF (sign flag)
     *
     * Arithmetic and logical instructions set the sign flag equal to the
     * high-order bit (bit 7 or 15) of the result. For signed binary numbers,
     * the sign flag will be 0 for positive results and 1 for negative results
     * (so long as overflow does not occur). A conditional jump instruction can
     * be used following addition or subtraction to alter the flow of the
     * program depending on the sign of the result. Programs performing
     * unsigned operations typically ignore SF since the high-order bit of the
     * result is interpreted as a digit rather than a sign.
     */
    public static final int   SF     = 1 << 7;

    /**
     * TF (trap flag)
     *
     * Settings TF puts the processor into single-step mode for debugging. In
     * this mode, the CPU automatically generates an internal interrupt after
     * each instruction, allowing a program to be inspected as it executes
     * instruction by instruction.
     */
    public static final int   TF     = 1 << 8;

    /**
     * IF (interrupt-enable flag)
     *
     * Setting IF allows the CPU to recognize external (maskable) interrupt
     * requests. Clearing IF disables these interrupts. IF has no affect on
     * either non-maskable external or internally generated interrupts.
     */
    public static final int   IF     = 1 << 9;

    /**
     * DF (direction flag)
     *
     * Setting DF causes string instructions to auto-decrement; that is, to
     * process strings from the high addresses to low addresses, or from "right
     * to left". Clearing DF causes string instructions to auto-increment, or
     * to process strings from "left to right."
     */
    public static final int   DF     = 1 << 10;

    /**
     * OF (overflow flag)
     *
     * If the result of an operation is too large a positive number, or too
     * small a negative number to fit in the destination operand (excluding the
     * sign bit), then OF is set; otherwise OF is cleared. OF thus indicates
     * signed arithmetic overflow; it can be tested with a conditional jump or
     * the INFO (interrupt on overflow) instruction. OF may be ignored when
     * performing unsigned arithmetic.
     */
    public static final int   OF     = 1 << 11;

    /** Lookup table used for clipping results. */
    public static final int[] MASK   = new int[] { 0xff, 0xffff };
    /** Lookup table used for setting the parity flag. */
    private static final int[] PARITY = {
            1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1,
            0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0,
            0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0,
            1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1,
            0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0,
            1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1,
            1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1,
            0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0,
            0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0,
            1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1,
            1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1,
            0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0,
            1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1,
            0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0,
            0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0,
            1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1
    };
    /** Lookup table used for setting the sign and overflow flags. */
    private static final int[] BITS   = new int[] { 8, 16 };
    /** Lookup table used for setting the overflow flag. */
    private static final int[] SIGN   = new int[] { 0x80, 0x8000 };



    /**
     * Sets the parity, zero and sign flags.
     *
     * @param w
     *            word/byte operation
     * @param res
     *            the result
     */
    public void setFlags(final int w, final int res) {
        setFlag(PF, PARITY[res & 0xff] > 0);
        setFlag(ZF, res == 0);
        setFlag(SF, (shift(res, 8 - BITS[w]) & SF) > 0);
    }


    /**
     * Returns if the most significant byte of a value is set.
     *
     * @param w
     *            word/byte operation
     * @param x
     *            the value
     * @return true if MSB is set, false if it is cleared
     */
    private static boolean msb(final int w, final int x) {
        return (x & SIGN[w]) == SIGN[w];
    }

    /**
     * Shifts left a value by a given number of positions, or right if that
     * number is negative.
     *
     * @param x
     *            the value
     * @param n
     *            the number of positions
     * @return the new value
     */
    private static int shift(final int x, final int n) {
        return n >= 0 ? x << n : x >>> -n;
    }

    /**
     * Converts an unsigned value to a signed value.
     *
     * @param w
     *            word/byte operation
     * @param x
     *            the value
     * @return the new value
     */
    private static int signconv(final int w, final int x) {
        return x << 32 - BITS[w] >> 32 - BITS[w];
    }

    @Override
    public String getName() {
        return "PSW";
    }



    @Override
    public int getDataWidth() {
        return 2;
    }


//    public int hasCF() {
//        return (flags & CF) == CF ? 1 : 0;
//    }

}

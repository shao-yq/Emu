package cc.emulator.x86.i8086;

import cc.emulator.x86.intel.ProgramStatusWord;
import cc.emulator.core.cpu.ArithmeticLogicUnit;

import static cc.emulator.x86.intel.ProgramStatusWord.AF;
import static cc.emulator.x86.intel.ProgramStatusWord.CF;
import static cc.emulator.x86.intel.ProgramStatusWord.OF;


public class ALU8086 implements ArithmeticLogicUnit {

    /** Lookup table used for clipping results. */
    private static final int[] MASK   = new int[] { 0xff, 0xffff };

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


    ProgramStatusWord flags;
    public ALU8086(ProgramStatusWord flags){
        this.flags = flags;
    }
    @Override
    public void reset() {

    }
    /**
     * Performs addition with carry and sets flags accordingly.
     *
     * @param w
     *            word/byte operation
     * @param dst
     *            the first operand
     * @param src
     *            the second operand
     * @return the result
     */
    public int adc(final int w, final int dst, final int src) {
        final int carry = (flags.hasFlag(CF)) ? 1 : 0;
        final int result = dst + src + carry & MASK[w];

        flags.setFlag(CF, carry == 1 ? result <= dst : result < dst);
        flags.setFlag(AF, ((result ^ dst ^ src) & AF) > 0);
        flags.setFlag(OF, (shift((dst ^ src ^ -1) & (dst ^ result), 12 - BITS[w]) & OF) > 0);
        flags.setFlags(w, result);

        return result;
    }
    /**
     * Performs addition and sets flags accordingly.
     *
     * @param w
     *            word/byte operation
     * @param dst
     *            the first operand
     * @param src
     *            the second operand
     * @return the result
     */
    public int add(final int w, final int dst, final int src) {
        final int res = dst + src & MASK[w];

        flags.setFlag(CF, res < dst);
        flags.setFlag(AF, ((res ^ dst ^ src) & AF) > 0);
        flags.setFlag(OF, (shift((dst ^ src ^ -1) & (dst ^ res), 12 - BITS[w]) & OF) > 0);
        flags.setFlags(w, res);

        return res;
    }
    /**
     * Performs subtraction and sets flags accordingly.
     *
     * @param w
     *            word/byte operation
     * @param dst
     *            the first operand
     * @param src
     *            the second operand
     * @return the result
     */
    public int sub(final int w, final int dst, final int src) {
        final int res = dst - src & MASK[w];

        flags.setFlag(CF, dst < src);
        flags.setFlag(AF, ((res ^ dst ^ src) & AF) > 0);
        flags.setFlag(OF, (shift((dst ^ src) & (dst ^ res), 12 - BITS[w]) & OF) > 0);
        flags.setFlags(w, res);

        return res;
    }
    /**
     * Performs subtraction with borrow and sets flags accordingly.
     *
     * @param w
     *            word/byte operation
     * @param dst
     *            the first operand
     * @param src
     *            the second operand
     * @return the result
     */
    public int sbb(final int w, final int dst, final int src) {
        final int carry = flags.hasFlag(CF )? 1 : 0;
        final int res = dst - src - carry & MASK[w];

        flags.setFlag(CF, carry > 0 ? dst <= src : dst < src);
        flags.setFlag(AF, ((res ^ dst ^ src) & AF) > 0);
        flags.setFlag(OF, (shift((dst ^ src) & (dst ^ res), 12 - BITS[w]) & OF) > 0);
        flags.setFlags(w, res);

        return res;
    }
    /**
     * Decrements an operand and sets flags accordingly.
     *
     * @param w
     *            word/byte operation
     * @param dst
     *            the operand
     * @return the result
     */
    public int dec(final int w, final int dst) {
        final int res = dst - 1 & MASK[w];

        flags.setFlag(AF, ((res ^ dst ^ 1) & AF) > 0);
        flags.setFlag(OF, res == SIGN[w] - 1);
        flags.setFlags(w, res);

        return res;
    }
    /**
     * Increments an operand and sets flags accordingly.
     *
     * @param w
     *            word/byte operation
     * @param dst
     *            the operand
     * @return the result
     */
    public int inc(final int w, final int dst) {
        final int res = dst + 1 & MASK[w];

        flags.setFlag(AF, ((res ^ dst ^ 1) & AF) > 0);
        flags.setFlag(OF, res == SIGN[w]);
        flags.setFlags(w, res);

        return res;
    }
    /**
     * Sets flags according to the result of a logical operation.
     *
     * @param w
     *            word/byte operation
     * @param res
     *            the result
     */
    public void logic(final int w, final int res) {
        flags.setFlag(CF, false);
        flags.setFlag(OF, false);
        flags.setFlags(w, res);
    }
}

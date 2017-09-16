package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
/*
 * LEA destination,source
 *
 * LEA (load effective address) transfers the offset of the source
 * operand (rather than its value) to the destination operand. The
 * source must be a memory operand, and the destination operand must
 * be a 16-bit general register. LEA does not affect any flags. The
 * XLAT and string instructions assume that certain registers point
 * to operands; LEA can be used to load these register (e.g.,
 * loading BX with the address of the translate table used by the
 * XLAT instruction).
 */
public class LoadEffectiveAddress extends Instruction8086 {
    public LoadEffectiveAddress(int[] raw, int startIndex) {
        super(raw, 2, startIndex);
        decodeDisplacement(raw);
    }

    public int getClocks(int c) {
        return 2;
    }

    public static boolean hasOpcode(int raw) {
        switch (raw) {
            case LEA_REG16_MEM16: //  0x8d: // LEA REG16,MEM16
                return true;
        }
        return false;
    }
}

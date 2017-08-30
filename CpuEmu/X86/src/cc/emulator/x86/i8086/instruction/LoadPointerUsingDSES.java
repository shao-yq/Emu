package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class LoadPointerUsingDSES extends Instruction8086 {
    public LoadPointerUsingDSES(int[] raw) {
        super(raw, 2);
        decodeDisplacement(raw);
    }

    public int getClocks(int c) {
        return 16;
    }

    public static boolean hasOpcode(int raw) {
        switch (raw) {
            /*
             * LDS destination,source
             *
             * LDS (load pointer using DS) transfers a 32-bit pointer variable
             * from the source operand, which must be a memory operand, to the
             * destination operand and register DS. The offset word of the
             * pointer is transferred to the destination operand, which may be
             * any 16-bit general register. The segment word of the pointer is
             * transferred to register DS. Specifying SI as the destination
             * operand is a convenient way to prepare to process a source string
             * that is not in the current data segment (string instructions
             * assume that the source string is located in the current data
             * segment and that SI contains the offset of the string).
             */
            case LDS_REG16_MEM32: //  0xc5: // LDS REG16,MEM32
            /*
             * LES destination,source
             *
             * LES (load pointer using ES) transfers a 32-bit pointer variable
             * from the source operand, which must be a memory operand, to the
             * destination operand and register ES. The offset word of the
             * pointer is transferred to the destination operand, which may be
             * any 16-bit general register. The segment word of the pointer is
             * transferred to register ES. Specifying DI as the destination
             * operand is a convenient way to prepare to process a destination
             * string that is not in the current extra segment. (The destination
             * string must be located in the extra segment, and DI must contain
             * the offset of the string).
             */
            case LES_REG16_MEM32: //  0xc4: // LES REG16,MEM32
                return true;
        }
        return false;
    }
}

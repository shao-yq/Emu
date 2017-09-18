package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/31.
 */
/*
 * ESC external-opcode,source
 *
 * ESC (Escape) provides a means for an external processor to obtain
 * an opcode and possibly a memory operand from the 8086. The
 * external opcode is a 6-bit immediate constant that the assembler
 * encodes in the machine instruction it builds. An external
 * processor may monitor the system bus and capture this opcode when
 * the ESC is fetched. If the source operand is a register, the
 * processor does nothing. If the source operand is a memory
 * variable, the processor obtains the operand from memory and
 * discards it. An external processor may capture the memory operand
 * when the processor reads it from memory.
 */
public class Escape extends Instruction8086{
    public Escape(int[] raw, int startIndex) {
        super(raw,2, startIndex);
        decodeDisplacement(raw);
    }
    public static boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch (raw) {
            case ESC_0_SOURCE: //   0xd8: // ESC 0,SOURCE
            case ESC_1_SOURCE: //   0xd9: // ESC 1,SOURCE
            case ESC_2_SOURCE: //   0xda: // ESC 2,SOURCE
            case ESC_3_SOURCE: //   0xdb: // ESC 3,SOURCE
            case ESC_4_SOURCE: //   0xdc: // ESC 4,SOURCE
            case ESC_5_SOURCE: //   0xdd: // ESC 5,SOURCE
            case ESC_6_SOURCE: //   0xde: // ESC 6,SOURCE
            case ESC_7_SOURCE: //   0xdf: // ESC 7,SOURCE
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return mod == 0b11 ? 2 : 8;
    }
}

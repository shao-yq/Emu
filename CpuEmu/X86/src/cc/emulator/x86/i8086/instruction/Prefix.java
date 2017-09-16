package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/9/5.
 */

/**
 * Instruction Prefixes
 * Instruction prefixes are divided into four groups, each with a set of allowable prefix codes. For each instruction, it
 * is only useful to include up to one prefix code from each of the four groups (Groups 1, 2, 3, 4). Groups 1 through 4
 * may be placed in any order relative to each other.
 *
 * • Group 1
 * — Lock and repeat prefixes:
 *   • LOCK prefix is encoded using F0H
 *   • REPNE/REPNZ prefix is encoded using F2H. Repeat-Not-Zero prefix applies only to string and input/output instructions.
 *   (F2H is also used as a mandatory prefix for some instructions)
 * REP or REPE/REPZ is encoded using F3H. The repeat prefix applies only to string and input/output instructions. F3H
 * is also used as a mandatory prefix for POPCNT, LZCNT and ADOX instructions.
 * • Group 2
 * — Segment override prefixes:
 *   • 2EH—CS segment override (use with any branch instruction is reserved)
 *   • 36H—SS segment override prefix (use with any branch instruction is reserved)
 *   • 3EH—DS segment override prefix (use with any branch instruction is reserved)
 *   • 26H—ES segment override prefix (use with any branch instruction is reserved)
 *   • 64H—FS segment override prefix (use with any branch instruction is reserved)
 *   • 65H—GS segment override prefix (use with any branch instruction is reserved)
 * — Branch hints:
 *   • 2EH—Branch not taken (used only with Jcc instructions)
 *   • 3EH—Branch taken (used only with Jcc instructions)
 * • Group 3
 *   • Operand-size override prefix is encoded using 66H (66H is also used as a mandatory prefix for some instructions).
 * • Group 4
 *   • 67H—Address-size override prefix
 */
public class Prefix extends Instruction8086{
    public Prefix(int[] raw) {
        super(raw);
    }

    public static boolean hasOpcode(int raw) {
        switch(raw) {

        }
        return false;
    }

}

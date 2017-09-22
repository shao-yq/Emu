package cc.emulator.arch.arm.decoder;

import cc.emulator.core.cpu.Instruction;

/**
 * @author Shao Yongqing
 * Date: 2017/9/22.
 */
public interface ArmDecodable {
    boolean hasInstruction(int rawInstruction);
    boolean hasOpcode(int rawInstruction);

    Instruction decode(int rawInstruction[]);
}

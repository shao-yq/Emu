package cc.emulator.arch.arm.decoder;

/**
 * @author Shao Yongqing
 * Date: 2017/9/22.
 */
public abstract class AbstractArmDecoder implements ArmDecodable{

    public static boolean isUncoditional(int rawInstruction) {
        return (rawInstruction >>> 28) == 0b1111;
    }

}

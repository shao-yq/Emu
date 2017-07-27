package cc.emulator.core.cpu;

/**
 * @author Shao Yongqing
 * Date: 2017/7/27.
 */
public interface Decodable {
    Instruction decode(int[] ibuf);
    Instruction decode2(int[] ibuf);

}

package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/9/2.
 */
public class MOV extends Instruction8086 {
    public MOV(int[] raw) {
        super(raw);
    }

    public MOV(int[] raw, int cnt) {
        super(raw, cnt);
    }
    public void execute(){

    }
}

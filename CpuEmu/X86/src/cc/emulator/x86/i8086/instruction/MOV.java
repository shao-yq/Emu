package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/9/2.
 */
public class MOV extends Instruction8086 {
    public MOV(){}
    public MOV(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    public void execute(){

    }

    @Override
    public String getMnemonic() {
        return "MOV";
    }
}

package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class XchgRegWithAcc extends Instruction8086 {
    public XchgRegWithAcc(){}
    public XchgRegWithAcc(int[] raw, int startIndex) {
        super(raw, startIndex);
    }
    public void decode(int[] raw, int startIndex) {
        super.decode(raw, startIndex);
        reg = op & 0b111;
    }
    public  boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch (raw) {
            // Register with Accumulator
            case XCHG_AX_CX: // 0x91: // XCHG AX,CX
            case XCHG_AX_DX: // 0x92: // XCHG AX,DX
            case XCHG_AX_BX: // 0x93: // XCHG AX,BX
            case XCHG_AX_SP: // 0x94: // XCHG AX,SP
            case XCHG_AX_BP: // 0x95: // XCHG AX,BP
            case XCHG_AX_SI: // 0x96: // XCHG AX,SI
            case XCHG_AX_DI: // 0x97: // XCHG AX,DI
                return true;
        }
        return false;
    }
    public int getClocks() {
        return 3;
    }
}

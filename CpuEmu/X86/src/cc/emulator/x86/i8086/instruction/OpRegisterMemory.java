package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2018/2/7.
 */
public abstract class OpRegisterMemory  extends Instruction8086 {
    public OpRegisterMemory(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    public OpRegisterMemory() {

    }

    public void decode(int[] raw, int startIndex) {
        decode(raw, 2, startIndex);
        decodeDisplacement(raw);
    }

    @Override
    public int getClocks() {
        if (d == 0b0)
            return mod == 0b11 ? 3 : 16;
        else
            return mod == 0b11 ? 3 : 9;
    }

    abstract int oprandMode(int op);

    final static int OP_REG8__MEM8_REG8     = 0x00;     // OP REG8/MEM8,REG8
    final static int OP_REG16__MEM16_REG16  = 0x01;     // OP REG16/MEM16,REG16
    final static int OP_REG8_REG8__MEM8     = 0x02;     // OP REG8,REG8/MEM8
    final static int OP_REG16_REG16__MEM16  = 0x03;     // OP REG16,REG16/MEM16

    @Override
    protected String getOperandPart() {
        StringBuffer asm = new StringBuffer();
        switch (oprandMode(op)) {
            case OP_REG8__MEM8_REG8   : //  0x00: // ADD REG8/MEM8,REG8
            case OP_REG16__MEM16_REG16: //  0x01: // ADD REG16/MEM16,REG16

                //dest
                asm.append(" "+getRMFieldString(w, mod, reg, rm));

                // src
                asm.append(", "+getRegMnemonic(reg,w));

                break;
            case OP_REG8_REG8__MEM8   : //  0x02: // ADD REG8,REG8/MEM8
            case OP_REG16_REG16__MEM16: //  0x03: // ADD REG16,REG16/MEM16
                // dest
                asm.append(" "+getRegMnemonic(reg,w));

                //src
                asm.append(", "+getRMFieldString(w, mod, reg, rm));
                break;
        }

        return asm.toString();
    }
}

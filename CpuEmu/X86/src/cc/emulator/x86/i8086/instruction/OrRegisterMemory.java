package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
/*
 * OR destination,source
 *
 * OR performs the logical "inclusive or" of the two operands (byte
 * or word) and returns the result to the destination operand. A bit
 * in the result is set if either or both corresponding bits of the
 * original operands are set; otherwise the result bit is cleared.
 */
public class OrRegisterMemory extends Instruction8086 {
    public OrRegisterMemory(){}
    public OrRegisterMemory(int[] raw, int startIndex) {
        super(raw, startIndex);
    }
    public void decode(int[] raw, int startIndex) {
        decode(raw, 2, startIndex);
        decodeDisplacement(raw);
    }
    public  boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch(raw) {

            // Register/Memory and Register
            case OR_REG8__MEM8_REG8   : //  0x08: // OR REG8/MEM8,REG8
            case OR_REG16__MEM16_REG16: //  0x09: // OR REG16/MEM16,REG16
            case OR_REG8_REG8__MEM8   : //  0x0a: // OR REG8,REG8/MEM8
            case OR_REG16_REG16__MEM16: //  0x0b: // OR REG16,REG16/MEM16
                return true;
        }
        return false;
    }
    @Override
    public int getClocks() {
        if (d == 0b0)
            return mod == 0b11 ? 3 : 16;
        else
            return mod == 0b11 ? 3 : 9;
    }


    @Override
    protected String getOperandPart() {
        StringBuffer asm = new StringBuffer();
        switch (op) {
            case OR_REG8__MEM8_REG8   : //  0x08: // OR REG8/MEM8,REG8
            case OR_REG16__MEM16_REG16: //  0x09: // OR REG16/MEM16,REG16

                //dest
                asm.append(" "+getRMFieldString(w, mod, reg, rm));

                // src
                asm.append(", "+getRegMnemonic(reg,w));

                break;
            case OR_REG8_REG8__MEM8   : //  0x0a: // OR REG8,REG8/MEM8
            case OR_REG16_REG16__MEM16: //  0x0b: // OR REG16,REG16/MEM16
                // dest
                asm.append(" "+getRegMnemonic(reg,w));

                //src
                asm.append(", "+getRMFieldString(w, mod, reg, rm));
                break;
        }

        return asm.toString();
    }


    @Override
    public String getMnemonic() {
        return "OR";
    }

}

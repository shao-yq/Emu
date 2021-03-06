package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/9/1.
 */
public class JmpIndirect extends Instruction8086 {
    public JmpIndirect(){}
    public JmpIndirect(int[] raw, int startIndex) {
        super(raw, startIndex);
    }
    public void decode(int[] raw, int startIndex) {
        decode(raw,2, startIndex);
        decodeDisplacement(raw);
    }

    public  boolean hasOpcode(int raw[], int startIndex) {
        switch (raw[startIndex]) {
            case EXT_0XFF:  // 0xff:
                // INC REG16/MEM16
                // DEC REG16/MEM16
                // CALL REG16/MEM16 (intra)
                // CALL MEM16 (intersegment)
                // JMP REG16/MEM16 (intra)
                // JMP MEM16 (intersegment)
                // PUSH REG16/MEM16
                int reg = (raw[1+startIndex]>>3) & 0b111;
                switch (reg) {
                    case JMP_REG16__MEM16_INTRA: //  0b100: // JMP REG16/MEM16(intra)
                    case JMP_MEM16_INTERSEGMENT: //  0b101: // JMP MEM16(intersegment)
                        return true;
                }
        }
        return false;
    }

    @Override
    public String getMnemonic() {
        return "JMP";
    }
    @Override
    protected String getOperandPart() {
        StringBuffer asm = new StringBuffer();
        switch (op) {
            case EXT_0XFF:  // 0xff:
                switch(reg){
                    case JMP_REG16__MEM16_INTRA: //  0b100: // JMP REG16/MEM16(intra)
                        asm.append("REG16/MEM16(intra)");
                        break;
                    case JMP_MEM16_INTERSEGMENT: //  0b101: // JMP MEM16(intersegment)
                        asm.append("MEM16(intersegment)");
                        break;
                }
        }
        return asm.toString();
    }
}

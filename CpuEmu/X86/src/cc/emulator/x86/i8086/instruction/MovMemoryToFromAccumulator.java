package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Bofeng
 * Date: 2017/8/20.
 */
public class MovMemoryToFromAccumulator extends MOV {
    public MovMemoryToFromAccumulator(int raw[]){
        super(raw);
        setAddress(raw[2]<<8|raw[1]);
        setLength(3);
    }

    public static boolean hasOpcode(int raw) {
        int opcode = raw;
        switch (opcode){
            // Memory to/from Accumulator
            case MOV_AL_MEM8 : //   0xa0: // MOV AL,MEM8
            case MOV_AX_MEM16: //   0xa1: // MOV AX,MEM16
            case MOV_MEM8_AL : //   0xa2: // MOV MEM8,AL
            case MOV_MEM16_AX: //   0xa3: // MOV MEM16,AX
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 10;
    }
}
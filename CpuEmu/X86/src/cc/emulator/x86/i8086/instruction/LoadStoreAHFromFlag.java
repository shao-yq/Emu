package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/30.
 */
public class LoadStoreAHFromFlag extends Instruction8086 {
    public LoadStoreAHFromFlag(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    public static boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        int opcode = raw;
        switch (opcode){
            /*
             * LAHF
             *
             * LAHF (load register AH from flags) copies SF, ZF, AF, PF and CF
             * into the bits 7, 6, 4, 2 and 0, respectively, of register AH. The
             * content of bits 5, 3 and 1 is undefined; the flags themselves are
             * not affected. LAHF is provided primarily for converting 8080/8085
             * assembly language programs to run on an 8086.
             */
            case LAHF: //  0x9f: // LAHF
            /*
             * SAHF
             *
             * SAHF (store register AH into flags) transfers bits 7, 6, 4, 2 and
             * 0 from register AH into SF, ZF, AF, PF and CF, respectively,
             * replacing whatever values these flags previously had. OF, DF, IF
             * and TF are not affected. This instruction is provided from
             * 8080/8085 compatibility.
             */
            case SAHF: //  0x9e: // SAHF
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 4;
    }
}

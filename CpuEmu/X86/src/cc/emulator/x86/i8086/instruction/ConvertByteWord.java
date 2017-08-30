package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/30.
 */
public class ConvertByteWord extends Instruction8086 {
    public ConvertByteWord(int[] raw) {
        super(raw);
    }

    public static boolean hasOpcode(int raw) {
        switch(raw) {

            /*
             * CBW
             *
             * CBW (Convert Byte to Word) extends the sign of the byte in
             * register AL throughout register AH. CBW does not affect any
             * flags. CBW can be used to produce a double-length (word) dividend
             * from a byte prior to performing byte division.
             */
            case CBW: //  0x98: // CBW
            /*
             * CWD
             *
             * CWD (Convert Word to Doubleword) extends the sign of the word in
             * register AX throughout register DX. CWD does not affect any
             * flags. CWD can be used to produce a double-length (doubleword)
             * dividend from a word prior to performing word division.
             */
            case CWD: //  0x99: // CWD
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        switch(op) {
            case CBW: //  0x98: // CBW
                return 2;
            case CWD: //  0x99: // CWD
                return 5;
        }

        return 2;
    }
}

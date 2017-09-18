package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/9/1.
 */
public class ShiftRotate extends Instruction8086{
    public ShiftRotate(int[] raw, int startIndex) {
        super(raw,2, startIndex);
        decodeDisplacement(raw);
    }

    public static boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch (raw) {
            /*
             * GROUP 2
             */
            case EXT_0XD0:  // 0xd0:
                // ROL REG8/MEM8,1
                // ROR REG8/MEM8,1
                // RCL REG8/MEM8,1
                // RCR REG8/MEM8,1
                // SAL/SHL REG8/MEM8,1
                // SHR REG8/MEM8,1
                // SAR REG8/MEM8,1
            case EXT_0XD1:  // 0xd1:
                // ROL REG16/MEM16,1
                // ROR REG16/MEM16,1
                // RCL REG16/MEM16,1
                // RCR REG16/MEM16,1
                // SAL/SHL REG16/MEM16,1
                // SHR REG16/MEM16,1
                // SAR REG16/MEM16,1
            case EXT_0XD2:  // 0xd2:
                // ROL REG8/MEM8,CL
                // ROR REG8/MEM8,CL
                // RCL REG8/MEM8,CL
                // RCR REG8/MEM8,CL
                // SAL/SHL REG8/MEM8,CL
                // SHR REG8/MEM8,CL
                // SAR REG8/MEM8,CL
            case EXT_0XD3:  // 0xd3:
                // ROL REG16/MEM16,CL
                // ROR REG16/MEM16,CL
                // RCL REG16/MEM16,CL
                // RCR REG16/MEM16,CL
                // SAL/SHL REG16/MEM16,CL
                // SHR REG16/MEM16,CL
                // SAR REG16/MEM16,CL

                return true;
        }
        return false;
    }
}

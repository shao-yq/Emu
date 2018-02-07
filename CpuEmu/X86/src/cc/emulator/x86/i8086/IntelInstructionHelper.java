package cc.emulator.x86.i8086;

/**
 * @author Shao Yongqing
 * Date: 2018/2/7.
 */
public class IntelInstructionHelper {

    // format regNames[reg][w]
    // format regNames[rm][w]
    static String regNames[][]=new String[][]{

            {"AL", "AX"},
            {"CL", "CX"},
            {"DL", "DX"},
            {"BL", "BX"},
            {"AH", "SP"},
            {"CH", "BP"},
            {"DH", "SI"},
            {"BH", "DI"},
    };

    public static String getRegMnemonic(int reg, int w){
        return regNames[reg][w];
    }

//    MOV REG, memory
//    MOV memory, REG
//    MOV REG, REG
//    MOV memory, immediate
//    MOV REG, immediate
//
//    REG: AX, BX, CX, DX, AH, AL, BL, BH, CH, CL, DH, DL, DI, SI, BP, SP.
//
//    memory: [BX], [BX+SI+7],变量, 等等
//
//    immediate: 5, -24, 3Fh, 10001101b, 等等.

    static String rmMods[]=new String[]{
            "BX+SI",
            "BX+DI",
            "BP+SI",
            "BP+DI",
            "SI",
            "DI",
            "BP",
            "BX"
    };

    public static String getRMFieldString(int w, int mod, int reg, int rm, int disp){
        switch(mod){
            case 0b00:
                if(rm==0b110)
                    return ""+disp;
                else {
                    // (BX)+(SI)
                    return "[" + rmMods[rm] + "]";
                }

            case 0b01:
                // (BX)+(SI) + D8
                return "["+rmMods[rm]+"+"+disp+"]";
            case 0b10:
                // (BX)+(SI) + D16
                return "["+rmMods[rm]+"+"+disp+"]";

            case 0b11:
                // Register mode
                return regNames[rm][w];
        }
        return "N/A";
    }

}

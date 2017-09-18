package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/30.
 */
/*
 * Conditional Transfers
 *
 * The conditional transfer instructions are jumps that may or may
 * not transfer control depending on the state of the CPU flags at
 * the time the instruction is executed. The 18 instructions each
 * test a different combination of flags for a conditional. If the
 * condition is "true," then control is transferred to the target
 * specified in the instruction. If the condition is "false," then
 * control passes to the instruction that follows the conditional
 * jump. All conditional jumps are SHORT, that is, the target must
 * be in the current code segment and within -128 to +127 bytes of
 * the first byte of the next instruction (JMP 00H jumps to the
 * first byte of the next instruction). Since the jump is made by
 * adding the relative displacement of the target to the instruction
 * pointer, all conditional jumps are self-relative and are
 * appropriate for position-independent routines.
 */
public class JumpIf extends Instruction8086{
    public JumpIf(int[] raw, int startIndex) {
        super(raw, startIndex);

        // IP-INC-8
        disp = raw[1+startIndex];
        incLength(1);
    }
    public static boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch (raw){
            /*
             * JO
             *
             * Jump if overflow - OF=1.
             */
            case JO_SHORT: //   0x70: // JO SHORT-LABEL
            /*
             * JNO
             *
             * Jump if not overflow - OF=0.
             */
            case JNO_SHORT: //  0x71: // JNO SHORT-LABEL
            /*
             * JB/JNAE/JC
             *
             * Jump if below/not above nor equal/carry - CF=1.
             */
            case JB__JNAE__JC_SHORT: //   0x72: // JB/JNAE/JC SHORT-LABEL
            /*
             * JNE/JAE/JNC
             *
             * Jump if not below/above or equal/not carry - CF=0.
             */
            case JNB__JAE__JNC_SHORT: //  0x73: // JNB/JAE/JNC SHORT-LABEL
            /*
             * JE/JZ
             *
             * Jump if equal/zero - ZF=1.
             */
            case JE__JZ_SHORT: //  0x74: // JE/JZ SHORT-LABEL
            /*
             * JNE/JNZ
             *
             * Jump if not equal/not zero - ZF=0.
             */
            case JNE__JNZ_SHORT: //  0x75: // JNE/JNZ SHORT-LABEL
            /*
             * JBE/JNA
             *
             * Jump if below or equal/not above - (CF or ZF)=1.
             */
            case JBE__JNA_SHORT: //   0x76: // JBE/JNA SHORT-LABEL
            /*
             * JNBE/JA
             *
             * Jump if not below nor equal/above - (CF or ZF)=0.
             */
            case JNBE__JA_SHORT: //   0x77: // JNBE/JA SHORT-LABEL
            /*
             * JS
             *
             * Jump if sign - SF=1.
             */
            case JS_SHORT: //   0x78: // JS SHORT-LABEL
            /*
             * JNS
             *
             * Jump if not sign - SF=0.
             */
            case JNS_SHORT: //  0x79: // JNS SHORT-LABEL
            /*
             * JP/JPE
             *
             * Jump if parity/parity equal - PF=1.
             */
            case JP__JPE_SHORT: //  0x7a: // JP/JPE SHORT-LABEL
            /*
             * JNP/JPO
             *
             * Jump if not parity/parity odd - PF=0.
             */
            case JNP__JPO_SHORT: //  0x7b: // JNP/JPO SHORT-LABEL
            /*
             * JL/JNGE
             *
             * Jump if less/not greater nor equal - (SF xor OF)=1.
             */
            case JL__JNGE_SHORT: //  0x7c: // JL/JNGE SHORT-LABEL
            /*
             * JNL/JGE
             *
             * Jump if not less/greater or equal - (SF xor OF)=0.
             */
            case JNL__JGE_SHORT: //  0x7d: // JNL/JGE SHORT-LABEL
            /*
             * JLE/JNG
             *
             * Jump if less or equal/not greater - ((SF xor OF) or ZF)=1.
             */
            case JLE__JNG_SHORT: //  0x7e: // JLE/JNG SHORT-LABEL
            /*
             * JNLE/JG
             *
             * Jump if not less nor equal/greater - ((SF xor OF) or ZF)=0.
             */
            case JNLE__JG_SHORT: //  0x7f: // JNLE/JG SHORT-LABEL
            /*
             * JCXZ short-label
             *
             * JCXZ (Jump If CX Zero) transfers control to the target operand if
             * CX is O. This instruction is useful at the beginning of a loop;
             * to bypass the loop if CX has a zero value, i.e., to execute the
             * loop zero times.
             */
            case JCXZ_SHORT: //  0xe3: // JCXZ SHORT-LABEL
                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 15;

    }

    public int getIpInc() {
        return disp;
    }
}

package cc.emulator.x86.i8086.instruction;

import cc.emulator.x86.i8086.Instruction8086;

/**
 * @author Shao Yongqing
 * Date: 2017/8/31.
 */
/*
 * Flag Operations
 */
public class FlagOp extends Instruction8086 {
    public FlagOp(){}
    public FlagOp(int[] raw, int startIndex) {
        super(raw, startIndex);
    }

    public  boolean hasOpcode(int raw[], int startIndex) {
        return hasOpcode(raw[startIndex]);
    }

    public static boolean hasOpcode(int raw) {
        switch (raw) {
            /*
             * CLC
             *
             * CLC (Clear Carry flag) zeroes the carry flag (CF) and affects no
             * other flags. It (and CMC and STC) is useful in conjunction with
             * the RCL and RCR instructions.
             */
            case CLC: //  0xf8: // CLC
            /*
             * CMC
             *
             * CMC (Complement Carry flag) "toggles" CF to its opposite state
             * and affects no other flags.
             */
            case CMC: //  0xf5: // CMC
            /*
             * STC
             *
             * STC (Set Carry flag) sets CF to 1 and affects no other flags.
             */
            case STC: //   0xf9: // STC
            /*
             * CLD
             *
             * CLD (Clear Direction flag) zeroes DF causing the string
             * instructions to auto-increment the SI and/or DI index registers.
             * CLD does not affect any other flags.
             */
            case CLD: //   0xfc: // CLD
            /*
             * STD
             *
             * STD (Set Direction flag) sets DF to 1 causing the string
             * instructions to auto-decrement the SI and/or DI index registers.
             * STD does not affect any other flags.
             */
            case STD : //  0xfd: // STD
            /*
             * CLI
             *
             * CLI (Clear Interrupt-enable flag) zeroes IF. When the interrupt-
             * enable flag is cleared, the 8086 and 8088 do not recognize an
             * external interrupt request that appears on the INTR line; in
             * other words maskable interrupts are disabled. A non-maskable
             * interrupt appearing on the NMI line, however, is honored, as is a
             * software interrupt. CLI does not affect any other flags.
             */
            case CLI : //  0xfa: // CLI
            /*
             * STI
             *
             * STI (Set Interrupt-enable flag) sets IF to 1, enabling processor
             * recognition of maskable interrupt requests appearing on the INTR
             * line. Note however, that a pending interrupt will not actually be
             * recognized until the instruction following STI has executed. STI
             * does not affect any other flags.
             */
            case STI : //  0xfb: // STI

                return true;
        }
        return false;
    }

    @Override
    public int getClocks() {
        return 2;
    }

    @Override
    public String getMnemonic() {
        switch (op){
                        /*
             * CLC
             *
             * CLC (Clear Carry flag) zeroes the carry flag (CF) and affects no
             * other flags. It (and CMC and STC) is useful in conjunction with
             * the RCL and RCR instructions.
             */
            case CLC: //  0xf8: // CLC
                return "CLC";
            /*
             * CMC
             *
             * CMC (Complement Carry flag) "toggles" CF to its opposite state
             * and affects no other flags.
             */
            case CMC: //  0xf5: // CMC
                return "CMC";
            /*
             * STC
             *
             * STC (Set Carry flag) sets CF to 1 and affects no other flags.
             */
            case STC: //   0xf9: // STC
                return "STC";
            /*
             * CLD
             *
             * CLD (Clear Direction flag) zeroes DF causing the string
             * instructions to auto-increment the SI and/or DI index registers.
             * CLD does not affect any other flags.
             */
            case CLD: //   0xfc: // CLD
                return "CLD";
            /*
             * STD
             *
             * STD (Set Direction flag) sets DF to 1 causing the string
             * instructions to auto-decrement the SI and/or DI index registers.
             * STD does not affect any other flags.
             */
            case STD : //  0xfd: // STD
                return "STD";
            /*
             * CLI
             *
             * CLI (Clear Interrupt-enable flag) zeroes IF. When the interrupt-
             * enable flag is cleared, the 8086 and 8088 do not recognize an
             * external interrupt request that appears on the INTR line; in
             * other words maskable interrupts are disabled. A non-maskable
             * interrupt appearing on the NMI line, however, is honored, as is a
             * software interrupt. CLI does not affect any other flags.
             */
            case CLI : //  0xfa: // CLI
                return "CLI";
            /*
             * STI
             *
             * STI (Set Interrupt-enable flag) sets IF to 1, enabling processor
             * recognition of maskable interrupt requests appearing on the INTR
             * line. Note however, that a pending interrupt will not actually be
             * recognized until the instruction following STI has executed. STI
             * does not affect any other flags.
             */
            case STI : //  0xfb: // STI
                return "STI";

        }
        return super.getMnemonic();
    }
}

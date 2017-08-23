package cc.emulator.x86.i8086;

import cc.emulator.core.cpu.ArithmeticLogicUnit;
import cc.emulator.core.cpu.ExecutionUnitImpl;
import cc.emulator.core.cpu.InstructionDecoder;
import cc.emulator.x86.intel.ProgramStatusWord;
import cc.emulator.core.cpu.register.DividableRegister;
import cc.emulator.core.cpu.register.GeneralRegister;
import cc.emulator.core.cpu.register.StatusRegister;

public class EU8086 extends ExecutionUnitImpl {
        /*
     * General Registers
     *
     * CPU has a complement of eight 16-bit general registers. The general
     * registers are subdivided into two sets of four registers each; the data
     * registers (sometimes called the H & L group for "high" and "low"), and
     * the pointer and index registers (sometimes called the P & I group).
     *
     * The data registers are unique in that their upper (high) and lower
     * halves are separately addressable. This means that each data register
     * can be used interchangeably as a 16-bit register, or as two 8-bit
     * registers. The other CPU registers always are accessed as 16-bit units
     * only. The data registers can be used without constraint in most
     * arithmetic and logic operations. In addition, some instructions use
     * certain registers implicitly thus allowing compact yet powerful
     * encoding.
     *
     * The pointer and index registers can also participate in most arithmetic
     * and logical operations. In fact, all eight registers fit the definition
     * of "accumulator" as used in first and second generation microprocessors.
     * The P & I registers (exception BP) also are used implicitly in some
     * instructions.
     */
    /**
     * AX (accumulator)
     *
     * Implicit Use:
     * AX - Word Multiply, Word Divide, Word I/O
     * AL - Byte Multiply, Byte Divide, Byte I/O, Translate, Decimal Arithmetic
     * AH - Byte Multiply, Byte Divide
     */
    private int                ah, al;
    DividableRegister ax;
    /**
     * CX (count)
     *
     * Implicit Use:
     * CX - String Operations, Loops
     * CL - Variable Shift and Rotate
     */
    private int                ch, cl;
    DividableRegister cx;
    /**
     * DX (data)
     *
     * Implicit Use:
     * DX - Word Multiply, Word Divide, Indirect I/O
     */
    private int                dh, dl;
    DividableRegister dx;
    /**
     * BX (base)
     *
     * Implicit Use:
     * BX - Translate
     */
    private int                bh, bl;
    DividableRegister bx;

    public EU8086() {
        super();
    }

    @Override
    protected StatusRegister createStatusRegister() {
        return new ProgramStatusWord();
    }

    @Override
    protected GeneralRegister[] createGeneralRegisters() {
        GeneralRegister regs[] = new GeneralRegister[4];
        // Create general registers
        //ax = new DividableRegister8086("AX", 2);
        ax = new DividableRegister8086("AX",2);
        bx = new DividableRegister8086("BX",2);
        cx = new DividableRegister8086("CX",2);
        dx = new DividableRegister8086("DX",2);

        int i=0;
        regs[i++] = ax;
        regs[i++] = bx;
        regs[i++] = cx;
        regs[i++] = dx;

        return regs;
    }

    @Override
    protected InstructionDecoder createDecoder() {
        return new Decoder8086();
    }

    @Override
    protected ArithmeticLogicUnit createALU(StatusRegister flags) {
        return new ALU8086((ProgramStatusWord) flags);
    }
}

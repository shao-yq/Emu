package cc.emulator.x86.i8086;

import cc.emulator.core.cpu.ArithmeticLogicUnit;
import cc.emulator.core.cpu.ExecutionUnitImpl;
import cc.emulator.core.cpu.InstructionDecoder;
import cc.emulator.core.cpu.register.PointerIndexer;
import cc.emulator.x86.intel.ProgramStatusWord;
import cc.emulator.core.cpu.register.DividableRegister;
import cc.emulator.core.cpu.register.GeneralRegister;
import cc.emulator.core.cpu.register.StatusRegister;

public class EU8086 extends ExecutionUnitImpl implements Intel8086InstructionSet {
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
    DividableRegister8086 ax;
    /**
     * CX (count)
     *
     * Implicit Use:
     * CX - String Operations, Loops
     * CL - Variable Shift and Rotate
     */
    private int                ch, cl;
    DividableRegister8086 cx;
    /**
     * DX (data)
     *
     * Implicit Use:
     * DX - Word Multiply, Word Divide, Indirect I/O
     */
    private int                dh, dl;
    DividableRegister8086 dx;
    /**
     * BX (base)
     *
     * Implicit Use:
     * BX - Translate
     */
    private int                bh, bl;
    DividableRegister8086 bx;

    /**
     * SP(Stack Pointer)
     */
    PointerIndexer sp;
    /**
     * BP(Base Pointer)
     */
    PointerIndexer bp;
    /**
     * SI(Source Index)
     */
    PointerIndexer si;
    /**
     * DI(Destination Index)
     */
    PointerIndexer di;

    public EU8086() {
        super();
    }

    @Override
    protected StatusRegister createStatusRegister() {
        return new ProgramStatusWord();
    }

    @Override
    protected GeneralRegister[] createGeneralRegisters() {
        GeneralRegister regs[] = new GeneralRegister[8];
        // Create general registers
        //ax = new DividableRegister8086("AX", 2);
        ax = new DividableRegister8086("AX",2);
        bx = new DividableRegister8086("BX",2);
        cx = new DividableRegister8086("CX",2);
        dx = new DividableRegister8086("DX",2);
        sp = new PointerIndexer("SP",2);
        bp = new PointerIndexer("BP",2);
        si = new PointerIndexer("SI",2);
        di = new PointerIndexer("DI",2);

        int i=0;
        regs[i++] = ax;
        regs[i++] = bx;
        regs[i++] = cx;
        regs[i++] = dx;
        regs[i++] = sp;
        regs[i++] = bp;
        regs[i++] = si;
        regs[i++] = di;

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
    /**
     * Gets the value of the register.
     *
     * The REG (register) field identifies a register that is one of the
     * instruction operands.
     *
     * @param w
     *            word/byte operation
     * @param reg
     *            the register field
     * @return the value
     */
    private int getReg(final int w, final int reg) {
        if (w == Intel8086InstructionSet.B)
            // Byte data
            switch (reg) {
                case 0b000: // AL
                    return ax.getL();
                case 0b001: // CL
                    return cx.getL();
                case 0b010: // DL
                    return dx.getL();
                case 0b011: // BL
                    return bx.getL();
                case 0b100: // AH
                    return ax.getH();
                case 0b101: // CH
                    return cx.getH();
                case 0b110: // DH
                    return dx.getH();
                case 0b111: // BH
                    return bx.getH();
            }
        else
            // Word data
            switch (reg) {
                case AX: //  0b000: // AX
                    return ax.getX();
                case CX: //  0b001: // CX
                    return cx.getX();
                case DX: //  0b010: // DX
                    return dx.getX();
                case BX: //  0b011: // BX
                    return bx.getX();
                case 0b100: // SP
                    return sp.getData();   //  sp;
                case 0b101: // BP
                    return bp.getData();
                case 0b110: // SI
                    return si.getData();
                case 0b111: // DI
                    return di.getData();
            }
        return 0;
    }
    /**
     * Sets the value of the register.
     *
     * The REG (register) field identifies a register that is one of the
     * instruction operands.
     *
     * @param w
     *            word/byte operation
     * @param reg
     *            the register field
     * @param val
     *            the new value
     */
    private void setReg(final int w, final int reg, final int val) {
        if (w == B)
            // Byte data
            switch (reg) {
                case AL: //  0b000: // AL
//                    al = val & 0xff;
                    ax.setL(val & 0xff);
                    break;
                case CL: //  0b001: // CL
                    //cl = val & 0xff;
                    cx.setL(val & 0xff);
                    break;
                case DL: //  0b010: // DL
                    //dl = val & 0xff;
                    dx.setL(val & 0xff);
                    break;
                case BL: //  0b011: // BL
                    //bl = val & 0xff;
                    bx.setL(val & 0xff);
                    break;
                case AH: //  0b100: // AH
                    //ah = val & 0xff;
                    ax.setH(val & 0xff);
                    break;
                case CH: //  0b101: // CH
                    //ch = val & 0xff;
                    cx.setH(val & 0xff);
                    break;
                case DH: //  0b110: // DH
                    //dh = val & 0xff;
                    dx.setH(val & 0xff);
                    break;
                case BH: //  0b111: // BH
                    //bh = val & 0xff;
                    bx.setH(val & 0xff);
                    break;
            }
        else
            // Word data
            switch (reg) {
                case AX: //  0b000: // AX
//                    al = val & 0xff;
//                    ah = val >>> 8 & 0xff;
                    ax.setX(val & 0xffff);
                    break;
                case CX: //  0b001: // CX
//                    cl = val & 0xff;
//                    ch = val >>> 8 & 0xff;
                    cx.setX(val & 0xffff);
                    break;
                case DX: //  0b010: // DX
//                    dl = val & 0xff;
//                    dh = val >>> 8 & 0xff;
                    dx.setX(val & 0xffff);
                    break;
                case BX: //  0b011: // BX
//                    bl = val & 0xff;
//                    bh = val >>> 8 & 0xff;
                    bx.setX(val & 0xffff);
                    break;
                case SP: //  0b100: // SP
//                    stack.setSp(val & 0xffff);      //  sp = val & 0xffff;
                    sp.setData(val & 0xffff);
                    break;
                case BP: //  0b101: // BP
//                    bp = val & 0xffff;
                    bp.setData(val & 0xffff);
                    break;
                case SI: //  0b110: // SI
//                    si = val & 0xffff;
                    si.setData(val & 0xffff);
                    break;
                case DI: //  0b111: // DI
                    di.setData(val & 0xffff);
                    break;
            }
    }

}

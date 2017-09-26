package cc.emulator.arch.arm;

import cc.emulator.core.cpu.AbstractInstruction;
import cc.emulator.core.cpu.Instruction;

/**
 * ARM Instruction base class
 *
 * @author Shao Yongqing
 * Date: 2017/8/17.
 */

/**
 * ARM Instruction format (ARM7TDMI-S)
 *
 * 4.1 Instruction Set Summary
 *
 * ARM Instruction Set Format
 * ARM instruction set encoding
 * The ARM instruction stream is a sequence of word-aligned words. Each ARM instruction is a single 32-bit word in
 * that stream. The encoding of an ARM instruction is:
 * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7  6  5 | 4| 3  2  1  0|Instruction Type
 * ------------+--------+------------------------------------------------------------+--+-----------------------------
 * |cond       | op1    |                                                            |op|           | Instruction classes(指令分类)
 * ------------+--------+------------------------------------------------------------+--+-----------+---------
 * Table shows the major subdivisions of the ARM instruction set, determined by bits[31:25, 4].
 * Most ARM instructions can be conditional, with a condition determined by bits[31:28] of the instruction, the cond
 * field. For more information see The condition code field. This applies to all instructions except those with the cond
 * field equal to 0b1111.
 * 
 * 
 * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7  6  5 | 4| 3  2  1  0|Instruction Type
 * |-----------+--------+------------------------------------------------------------+--+-----------------------------
 * | cond      | op1    |                                                            |op|           | Instruction classes(指令分类)
 * |-----------+--------+------------------------------------------------------------+--+-----------+---------
 * |           | 0 0 x  | -	 | 数据处理和杂项指令）
 * |NOT        |------------------------------------------------------------------
 * |1111       | 010   | -  | 加载/存储字或无符号的字节
 * |           |------------------------------------------------------------------
 * |           | 011   | 0  | 加载/存储字或无符号的字节
 * |           |       | 1  | 媒体指令
 * |           |------------------------------------------------------------------
 * |           | 10x   | -  | 分支、带链接分支、块数据传输
 * |           | 11x   | -  | 协处理器指令或软中断，包括浮点指令和先进SIMD数据传输
 * |-----------+------------------------------------------------------------------
 * | 1111     |  -    | -  | 如果cond字段为0b1111，只能无条件地执行指令
 * ---------------------------------------------------------------------------
 * 表格中的op1、op字段中的x、-表示可以是0，也可以是1
 * 
 * 4.1.1 Format summary
 * The ARM instruction set formats are shown below.
 * 
 * -------------------------------------------------------------------------------------------------------------------------------
 * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7  6  5  4| 3  2  1  0|Instruction Type
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
 * | Condition | 0| 0| I|  OPCODE   | S|     Rn    |   Rd      |           OPERAND‐2              | Data processing/PSR Transfer
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
 * | Condition | 0| 0| 0| 0| 0| 0| A| S|    Rd     |   Rn      |     Rs    | 1| 0| 0| 1|    Rm     |  Multiply
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------|--+--+--+--+-----------|------------------------------
 * | Condition | 0| 0| 0| 0| 1| U| A| S| Rd HIGH   | Rd LOW    |     Rn    | 1| 0| 0| 1|    Rm     |  Long Multiply
 * |-----------|--+--+--+--+--+--+--+--|-----------|-----------|-----------|--+--+--+--+-----------|------------------------------
 * | Condition | 0| 0| 0| 1| 0| B| 0| 0|     Rn    |    Rd     | 0| 0| 0| 0| 1| 0| 0| 1|    Rm     |  Single Data SWAP
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
 * | Condition | 0| 0| 0| 1| 0| 0| 1| 0| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 0| 0| 0| 1|    Rn     |  Branch and Exchange
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
 * | Condition | 0| 0| 0| P| U| 0| W| L|     Rn    |    Rd     | 0| 0| 0| 0| 1| S| H| 1|    Rm     |  Halfword Data Transfer: register offset
 * |-----------|--+--+--+-----------+--|-----------|-----------|-----------------------------------|------------------------------
 * | Condition | 0| 0| 0| P| U| 1| W| L|    Rn     |   Rd      |   Offset  | 1| S| H| 1|  Offset   |  Halfword Data Transfer: immediate offset
 * |-----------|--+--+--+--------------|-----------|-----------|-----------------------------------|------------------------------
 * | Condition | 0| 1| 1| P| U| B| W| L|    Rn     |   Rd      |      Offset                       |  Single Data Transfer
 * |-----------|--+--+--+--------------------------------------------------------------------------|------------------------------
 * | Condition | 0| 1| 1|                                                           | 1|           |  Undefined
 * |-----------|--+--+--+--------------------------------------------------------------------------|------------------------------
 * | Condition | 1| 0| 0| P| U| S| W| L|    Rn     |   Register List                               |  Data Block Transfer
 * |-----------|--+--+--+--+-----------------------------------------------------------------------|------------------------------
 * | Condition | 1| 0| 1| L|                      Offset                                           |  Branch
 * |-----------|--+--+--+--------------------------------------------------------------------------|------------------------------
 * | Condition | 1| 1| 0| P| U| N| W| L|    Rn     |   CRd     |   CP#     |     Offset            |  Coprocessor Data Transfer
 * |-----------|--+--+--+--------------|-----------|-----------|-----------------------------------|------------------------------
 * | Condition | 1| 1| 1| 0| CP Opc    |   CRn     |   CRd     |   CP#     |    CP  | 0|   CRm     |  Coprocessor Data Transfer
 * |-----------|--+--+--+--------------|-----------|-----------|-----------------------------------|------------------------------
 * | Condition | 1| 1| 1| 0| CP Opc | L|   CRn     |   CRd     |   CP#     |    CP  | 1|   CRm     |  Coprocessor Data Transfer
 * |-----------|--+--+--+--------------------------------------------------------------------------|------------------------------
 * | Condition | 1| 1| 1|                Ignored By Processor                                      |  Software Interrupt
 * |-----------|--+--+--+--------------------------------------------------------------------------|------------------------------
 * |31 30 29 28|27|26|25|24 23 22 21|20|19 18 17 16|15 14 13 12|11 10  9  8| 7  6  5  4| 3  2  1  0|Instruction Type
 * -------------------------------------------------------------------------------------------------------------------------------
 * 
 *
 */

/**
 * The ARM Instruction set
 *  4.1.2 Instruction summary
 * Table 4-1: The ARM Instruction set
 * Mnemonic| Instruction                           |   Action                  | See Section:
 * --------+---------------------------------------+---------------------------+--------------
 * ADC     | Add with carry                        | Rd := Rn + Op2 + Carry    | 4.5
 * ADD     | Add                                   | Rd := Rn + Op2            | 4.5
 * AND     | AND                                   | Rd := Rn AND Op2          | 4.5
 * B       | Branch                                | R15 := address            | 4.4
 * BIC     | Bit Clear                             | Rd := Rn AND NOT Op2      | 4.5
 * BL      | Branch with Link                      | R14 := R15, R15 := address| 4.4
 * BX      | Branch and Exchange                   | R15 := Rn, T bit := Rn[0] | 4.3
 * CDP     | Coprocesor Data Processing            | (Coprocessor-specific)    | 4.14
 * CMN     | Compare Negative                      | CPSR flags := Rn + Op2    | 4.5
 * CMP     | Compare                               | CPSR flags := Rn - Op2    | 4.5
 * EOR     | Exclusive OR                          | Rd := (Rn AND NOT Op2)    | 4.5
 *         |                                       |   OR (op2 AND NOT Rn)     |
 * LDC     |Load coprocessor frommemory            | Coprocessor load          | 4.15
 * LDM     | Load multiple registers               | Stack manipulation (Pop)  | 4.11
 * LDR     | Load register from memory             | Rd := (address)           | 4.9, 4.10
 * MCR     | Move CPU register to                  | cRn := rRn {<op>cRm}      | 4.16
 *         | coprocessor register                  |                           |
 * MLA     | Multiply Accumulate                   | Rd := (Rm * Rs) + Rn      | 4.7, 4.8
 * MOV     | Move register or constant             | Rd : = Op2                | 4.5
 * MRC     | Move from coprocessor                 | Rn := cRn {<op>cRm}       | 4.16
 *         | register to CPU register              |
 * MRS     | Move PSR status/flags to register     | Rn := PSR                 | 4.6
 * MSR     | Move register to PSR status/flags     | PSR := Rm                 | 4.6
 * MUL     | Multiply                              | Rd := Rm * Rs             | 4.7, 4.8
 * MVN     | Move negative register                | Rd := 0xFFFFFFFF EOR Op2  | 4.5
 * ORR     | OR                                    | Rd := Rn OR Op2           | 4.5
 * RSB     | Reverse Subtract                      | Rd := Op2 - Rn            | 4.5
 * RSC     | Reverse Subtract with Carry           | Rd := Op2 - Rn - 1 + Carry| 4.5
 * SBC     | Subtract with Carry                   | Rd := Rn - Op2 - 1 + Carry| 4.5
 * STC     | Store coprocessor register to memory  | address := CRn            | 4.15
 * STM     | Store Multiple                        | Stack manipulation (Push) | 4.11
 * STR     | Store register to memory              | <address> := Rd           | 4.9, 4.10
 * SUB     | Subtract                              | Rd := Rn - Op2            | 4.5
 * SWI     | Software Interrupt                    | OS call                   | 4.13
 * SWP     | Swap register with memory             | Rd := [Rn], [Rn] := Rm    | 4.12
 * TEQ     | Test bitwise equality                 | CPSR flags := Rn EOR Op2  | 4.5
 * TST     | Test bits                             | CPSR flags := Rn AND Op2  | 4.5
 * ----------------------------------------------------------------------------------------
 * 
 */

/**
 * Condition code summary
 *
 *
 * 4.2 The Condition Field
 * In ARM state, all instructions are conditionally executed according to the state of the
 * CPSR condition codes and the instruction’s condition field. This field (bits 31:28)
 * determines the circumstances under which an instruction is to be executed. If the state
 * of the C, N, Z and V flags fulfils the conditions encoded by the field, the instruction is
 * executed, otherwise it is ignored.
 * There are sixteen possible conditions, each represented by a two-character suffix that
 * can be appended to the instruction’s mnemonic. For example, a Branch (B in assembly
 * language) becomes BEQ for "Branch if Equal", which means the Branch will only be
 * taken if the Z flag is set.
 * In practice, fifteen different conditions may be used: these are listed in Table 4-2:
 * Condition code summary. The sixteenth (1111) is reserved, and must not be used.
 * In the absence of a suffix, the condition field of most instructions is set to "Always" (sufix
 * AL). This means the instruction will always be executed regardless of the CPSR
 * condition codes.
 * 
 * Code | Suffix   | Flags                     | Meaning
 * ------------------------------------------------------------------------
 * 0000 | EQ       | Z set                     | equal
 * 0001 | NE       | Z clear                   | not equal
 * 0010 | CS       | C set                     | unsigned higher or same
 * 0011 | CC       | C clear                   | unsigned lower
 * 0100 | MI       | N set                     | negative
 * 0101 | PL       | N clear                   | positive or zero
 * 0110 | VS       | V set                     | overflow
 * 0111 | VC       | V clear                   | no overflow
 * 1000 | HI       | C set and Z clear         | unsigned higher
 * 1001 | LS       | C clear or Z set          | unsigned lower or same
 * 1010 | GE       | N equals V                | greater or equal
 * 1011 | LT       | N not equal to V          | less than
 * 1100 | GT       | Z clear AND (N equals V)  | greater than
 * 1101 | LE       |Z set OR (N not equal to V)| less than or equal
 * 1110 | AL       | (ignored)                 | always
 * =========================================================================
 * 
 *
 */


/**
 *
 *
 * aaaa 汇编器 意思 P-Code
 * 
 * 0000 AND 逻辑与 Rd = Rn AND Op2
 * 0001 EOR 逻辑异或 Rd = Rn EOR Op2
 * 0010 SUB 减法 Rd = Rn - Op2
 * 0011 RSB 反向减法 Rd = Op2 - Rn
 * 0100 ADD 加法 Rd = Rn + Op2
 * 0101 ADC 带进位的加法 Rd = Rn + Op2 + C
 * 0110 SBC 带借位的减法 Rd = Rn - Op2 - (1-C)
 * 0111 RSC 带借位的反向减法 Rd = Op2 - Rn - (1-C)
 * 1000 TST 测试位 Rn AND Op2
 * 1001 TEQ 测试等同 Rn EOR Op2
 * 1010 CMP 比较 Rn - Op2
 * 1011 CMN 比较取负 Rn + Op2
 * 1100 ORR 逻辑或 Rd = Rn OR Op2
 * 1101 MOV 传送值 Rd = Op2
 * 1110 BIC 位清除 Rd = Rn AND NOT Op2
 * 1111 MVN 传送取非 Rd = NOT Op2
 * 
 *
 */


/**
 *
 * SHIFTS
 * 
 * 11
 * 109876543210
 * bbbbbTT0----
 * |   | shit type
 * |  00  logical left
 * |  01  logical right
 * |  10  arithmetic right
 * |  11  rotate right
 * |
 * Shift amount (5 bits)
 * 
 * 11
 * 109876543210
 * rrrr0TT1----
 * |   | shit type
 * |  00  logical left
 * |  01  logical right
 * |  10  arithmetic right
 * |  11  rotate right
 * |
 * Shift Register (4 bits) -  bottom byte
 * 
 *
 */

public  abstract class ArmInstruction  extends AbstractInstruction {
    private  int startIndex;
    protected boolean setFlag;

    public ArmInstruction(){}
    public ArmInstruction(int rawInstruction){
        this(new int[]{rawInstruction});
    }

    public ArmInstruction(int[] rawInstruction) {
        this(rawInstruction, 0);
    }

    protected  int rawInstruction;
    protected  int cond;

    protected  int immediate;

    protected  int address;

    protected  boolean writesPC;
    protected  boolean fixedJump;

    public int getRawInstruction() {
        return rawInstruction;
    }

    protected void setWritesPC(boolean writesPC) {
        this.writesPC = writesPC;
    }

    protected void setFixedJump(boolean fixedJump) {
        this.fixedJump = fixedJump;
    }

    public boolean isWritesPC() {
        return writesPC;
    }

    public boolean isFixedJump(){
        return fixedJump;
    }

    public ArmInstruction(int[] raw, int startIndex) {
        decodeMe(raw, startIndex);
    }


    @Override
    public int getOperand(int index) {
        return 0;
    }

    @Override
    public int getClocks() {
        return 0;
    }

    @Override
    public int getImmediate() {
        return immediate;
    }



    @Override
    public int getAddress() {
        return address;
    }


    protected void copyOthers(Instruction o) {
    }

    /* Registers */
    protected int Rn = 0;
    protected int Rd = 0;
    protected int Rm = 0;
    protected int Rs = 0;
    protected int imm = 0;
    protected int amt = 0;

    boolean I;
    boolean P ;
    boolean U ;
    boolean B ;
    boolean W ;
    boolean S ;
    boolean L ;

    @Override
    public void decode(int[] raw, int startIndex) {
        // Save the rawInstruction raw data
        this.startIndex = startIndex;
        rawInstruction = raw[startIndex];
        // Condition
        cond = rawInstruction >>> 28;

         /* Registers */
        Rn = ((rawInstruction >> 16) & 0xF);
        Rd = ((rawInstruction >> 12) & 0xF);
        Rm = ((rawInstruction >> 0) & 0xF);
        Rs = ((rawInstruction >> 8) & 0xF);
        imm = ((rawInstruction >> 0) & 0xFF);
        amt = Rs << 1;

            /* Flags */
        boolean I = ((rawInstruction >> 25) & 1) != 0;
        boolean P = ((rawInstruction >> 24) & 1) != 0;
        boolean U = ((rawInstruction >> 23) & 1) != 0;
        boolean B = ((rawInstruction >> 22) & 1) != 0;
        boolean W = ((rawInstruction >> 21) & 1) != 0;
        boolean S = ((rawInstruction >> 20) & 1) != 0;
        boolean L = ((rawInstruction >> 20) & 1) != 0;

    }

//    public static Instruction createInstruction(int[] raw) {
//        return new ArmInstruction(raw);
//    }

    @Override
    public String getMnemonic() {
        return "ARM";
    }
}

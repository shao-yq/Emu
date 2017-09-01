package cc.emulator.x86.i8086;

/**
 * @author Shao Yongqing
 * Date: 2017/7/25.
 */
public interface Intel8086InstructionSet {

    public final static int REGISTER_SRC = 0b0;
    public final static int REGISTER_DST = 0b1;


    public final static int MOD_MEMORY_DISP0            = 0b00;
    public final static int MOD_MEMORY_DISP8            = 0b01;
    public final static int MOD_MEMORY_DISP16           = 0b10;
    public final static int MOD_REGISTER_TO_REGISTER   = 0b11;



    public final static int ES = 0b00; // ES
    public final static int CS = 0b01; // CS
    public final static int SS = 0b10; // SS
    public final static int DS = 0b11; // DS
    
    public final static int SP =  0b100; // SP
    public final static int BP =  0b101; // BP
    public final static int SI =  0b110; // SI
    public final static int DI =  0b111; // DI

    /** Instruction operates on byte data. */
    public static final int   B      = 0b0;
    /** Instruction operates on word data. */
    public static final int   W      = 0b1;

    /** Register AX is one of the instruction operands. */
    public static final int   AX     = 0b000;
    public static final int   AL     = 0b000;
    public static final int   AH     = 0b100;
    
    /** Register CX is one of the instruction operands. */
    public static final int   CX     = 0b001;
    public static final int   CL     = 0b001;
    public static final int   CH     = 0b101;
    
    /** Register DX is one of the instruction operands. */
    public static final int   DX     = 0b010;
    public static final int   DL     = 0b010;
    public static final int   DH     = 0b110;
    /** Register BX is one of the instruction operands. */
    public static final int   BX     = 0b011;
    public static final int   BL     = 0b011;
    public static final int   BH     = 0b111;


    
    public final static int POP_ES = 0x07; // POP ES
    public final static int POP_CS = 0x0f; // POP CS
    public final static int POP_SS = 0x17; // POP SS
    public final static int POP_DS = 0x1f; // POP DS
    
    public final static int PUSH_ES =  0x06; // PUSH ES
    public final static int PUSH_CS =  0x0e; // PUSH CS
    public final static int PUSH_SS =  0x16; // PUSH SS
    public final static int PUSH_DS =  0x1e; // PUSH DS
    
    public final static int POP_AX =  0x58; // POP AX
    public final static int POP_CX =  0x59; // POP CX
    public final static int POP_DX =  0x5a; // POP DX
    public final static int POP_BX =  0x5b; // POP BX
    public final static int POP_SP =  0x5c; // POP SP
    public final static int POP_BP =  0x5d; // POP BP
    public final static int POP_SI =  0x5e; // POP SI
    public final static int POP_DI =  0x5f; // POP DI
    
    
    public final static int PUSH_AX =  0x50; // PUSH AX
    public final static int PUSH_CX =  0x51; // PUSH CX
    public final static int PUSH_DX =  0x52; // PUSH DX
    public final static int PUSH_BX =  0x53; // PUSH BX
    public final static int PUSH_SP =  0x54; // PUSH SP
    public final static int PUSH_BP =  0x55; // PUSH BP
    public final static int PUSH_SI =  0x56; // PUSH SI
    public final static int PUSH_DI =  0x57; // PUSH DI

    
    public final static int XCHG_REG8_REG8__MEM8   = 0x86; // XCHG REG8,REG8/MEM8   
    public final static int XCHG_REG16_REG16__MEM16= 0x87; // XCHG REG16,REG16/MEM16
    
    public final static int MOV_REG8__MEM8_REG8    =   0x88; // MOV REG8/MEM8,REG8   
    public final static int MOV_REG16__MEM16_REG16 =   0x89; // MOV REG16/MEM16,REG16
    public final static int MOV_REG8_REG8__MEM8    =   0x8a; // MOV REG8,REG8/MEM8   
    public final static int MOV_REG16_REG16__MEM16 =   0x8b; // MOV REG16,REG16/MEM16
    
    public final static int MOV_REG16_MEM16__SEGREG=  0x8c; // MOV REG16/MEM16,SEGREG
    public final static int MOV_SEGREG_REG16__MEM16=  0x8e; // MOV SEGREG,REG16/MEM16
    
    public final static int XCHG_AX_CX = 0x91;   // XCHG AX,CX
    public final static int XCHG_AX_DX = 0x92;   // XCHG AX,DX
    public final static int XCHG_AX_BX = 0x93;   // XCHG AX,BX
    public final static int XCHG_AX_SP = 0x94;   // XCHG AX,SP
    public final static int XCHG_AX_BP = 0x95;   // XCHG AX,BP
    public final static int XCHG_AX_SI = 0x96;   // XCHG AX,SI
    public final static int XCHG_AX_DI = 0x97;   // XCHG AX,DI
    
    
    public final static int MOV_AL_MEM8  =   0xa0; // MOV AL,MEM8 
    public final static int MOV_AX_MEM16 =   0xa1; // MOV AX,MEM16
    public final static int MOV_MEM8_AL  =   0xa2; // MOV MEM8,AL 
    public final static int MOV_MEM16_AX =   0xa3; // MOV MEM16,AX
    
    public final static int MOVS_STR8_STR8   =  0xa4; // MOVS
    public final static int MOVS_STR16_STR16 =  0xa5;        
    
    public final static int CMPS_STR8_STR8   =  0xa6; // CMPS
    public final static int CMPS_STR16_STR16 =  0xa7;        
    public final static int SCAS_STR8  =  0xae; // SCAS      
    public final static int SCAS_STR16 =  0xaf;                                         
    public final static int LODS_STR8  =  0xac; // LODS      
    public final static int LODS_STR16 =  0xad;              
    
    public final static int STOS_STR8  = 0xaa; // STOS       
    public final static int STOS_STR16 = 0xab;               
    
    public final static int MOV_AL_IMMED8  = 0xb0; // MOV AL,IMMED8 
    public final static int MOV_CL_IMMED8  = 0xb1; // MOV CL,IMMED8 
    public final static int MOV_DL_IMMED8  = 0xb2; // MOV DL,IMMED8 
    public final static int MOV_BL_IMMED8  = 0xb3; // MOV BL,IMMED8 
    public final static int MOV_AH_IMMED8  = 0xb4; // MOV AH,IMMED8 
    public final static int MOV_CH_IMMED8  = 0xb5; // MOV CH,IMMED8 
    public final static int MOV_DH_IMMED8  = 0xb6; // MOV DH,IMMED8 
    public final static int MOV_BH_IMMED8  = 0xb7; // MOV BH,IMMED8 
    public final static int MOV_AX_IMMED16 = 0xb8; // MOV AX,IMMED16
    public final static int MOV_CX_IMMED16 = 0xb9; // MOV CX,IMMED16
    public final static int MOV_DX_IMMED16 = 0xba; // MOV DX,IMMED16
    public final static int MOV_BX_IMMED16 = 0xbb; // MOV BX,IMMED16
    public final static int MOV_SP_IMMED16 = 0xbc; // MOV SP,IMMED16
    public final static int MOV_BP_IMMED16 = 0xbd; // MOV BP,IMMED16
    public final static int MOV_SI_IMMED16 = 0xbe; // MOV SI,IMMED16
    public final static int MOV_DI_IMMED16 = 0xbf; // MOV DI,IMMED16
    
    public final static int MOV_REG8__MEM8_IMMED8    = 0xc6; // MOV REG8/MEM8,IMMED8   
    public final static int MOV_REG16__MEM16_IMMED16 = 0xc7; // MOV REG16/MEM16,IMMED16
    public final static int XLAT_SOURCE_TABLE = 0xd7; // XLAT SOURCE-TABLE
    public final static int IN_AL_IMMED8 =  0xe4; // IN AL,IMMED8
    public final static int IN_AX_IMMED8 =  0xe5; // IN AX,IMMED8
    
    public final static int IN_AL_DX =  0xec; // IN AL,DX
    public final static int IN_AX_DX =  0xed; // IN AX,DX
    
    public final static int OUT_AL_IMMED8 =  0xe6; // OUT AL,IMMED8
    public final static int OUT_AX_IMMED8 =  0xe7; // OUT AX,IMMED8
    
    public final static int OUT_AL_DX =  0xee; // OUT AL,DX
    public final static int OUT_AX_DX =  0xef; // OUT AX,DX
    
    public final static int LEA_REG16_MEM16 =  0x8d; // LEA REG16,MEM16
    
    public final static int LDS_REG16_MEM32 =  0xc5; // LDS REG16,MEM32
    
    public final static int LES_REG16_MEM32 = 0xc4; // LES REG16,MEM32
    
    public final static int LAHF =  0x9f; // LAHF
    public final static int SAHF =  0x9e; // SAHF
    public final static int PUSHF = 0x9c; // PUSHF
    public final static int POPF  = 0x9d; // POPF
    
    public final static int ADD_REG8__MEM8_REG8    =  0x00; // ADD REG8/MEM8,REG8   
    public final static int ADD_REG16__MEM16_REG16 =  0x01; // ADD REG16/MEM16,REG16
    public final static int ADD_REG8_REG8__MEM8    =  0x02; // ADD REG8,REG8/MEM8   
    public final static int ADD_REG16_REG16__MEM16 =  0x03; // ADD REG16,REG16/MEM16
    
    public final static int ADD_AL_IMMED8  =  0x04; // ADD AL,IMMED8 
    public final static int ADD_AX_IMMED16 =  0x05; // ADD AX,IMMED16
    
    public final static int ADC_REG8__MEM8_REG8    =  0x10; // ADC REG8/MEM8,REG8   
    public final static int ADC_REG16__MEM16_REG16 =  0x11; // ADC REG16/MEM16,REG16
    public final static int ADC_REG8_REG8__MEM8    =  0x12; // ADC REG8,REG8/MEM8   
    public final static int ADC_REG16_REG16__MEM16 =  0x13; // ADC REG16,REG16/MEM16
    
    public final static int ADC_AL_IMMED8  =  0x14; // ADC AL,IMMED8 
    public final static int ADC_AX_IMMED16 =  0X15; // ADC AX,IMMED16
    
    public final static int INC_AX =  0x40; // INC AX
    public final static int INC_CX =  0x41; // INC CX
    public final static int INC_DX =  0x42; // INC DX
    public final static int INC_BX =  0x43; // INC BX
    public final static int INC_SP =  0x44; // INC SP
    public final static int INC_BP =  0x45; // INC BP
    public final static int INC_SI =  0x46; // INC SI
    public final static int INC_DI =  0x47; // INC DI
    
    public final static int AAA =  0x37; // AAA
    public final static int DAA =  0x27; // DAA
    
    public final static int SUB_REG8__MEM8_REG8    =  0x28; // SUB REG8/MEM8,REG8   
    public final static int SUB_REG16__MEM16_REG16 =  0x29; // SUB REG16/MEM16,REG16
    public final static int SUB_REG8_REG8__MEM8    =  0x2a; // SUB REG8,REG8/MEM8   
    public final static int SUB_REG16_REG16__MEM16 =  0x2b; // SUB REG16,REG16/MEM16
    
    public final static int SUB_AL_IMMED8  =  0x2c; // SUB AL,IMMED8 
    public final static int SUB_AX_IMMED16 =  0x2d; // SUB AX,IMMED16
    
    public final static int SBB_REG8__MEM8_REG8    =   0x18; // SBB REG8/MEM8,REG8   
    public final static int SBB_REG16__MEM16_REG16 =   0x19; // SBB REG16/MEM16,REG16
    public final static int SBB_REG8_REG8__MEM8    =   0x1a; // SBB REG8,REG8/MEM8    
    public final static int SBB_REG16_REG16__MEM16 =   0x1b; // SBB REG16,REG16/MEM16 
    
    public final static int SBB_AL_IMMED8  =   0x1c; // SBB AL,IMMED8 
    public final static int SBB_AX_IMMED16 =   0X1d; // SBB AX,IMMED16
    
    public final static int DEC_AX =  0x48; // DEC AX
    public final static int DEC_CX =  0x49; // DEC CX
    public final static int DEC_DX =  0x4a; // DEC DX
    public final static int DEC_BX =  0x4b; // DEC BX
    public final static int DEC_SP =  0x4c; // DEC SP
    public final static int DEC_BP =  0x4d; // DEC BP
    public final static int DEC_SI =  0x4e; // DEC SI
    public final static int DEC_DI =  0x4f; // DEC DI
    
    public final static int CMP_REG8__MEM8_REG8    =  0x38; // CMP REG8/MEM8,REG8    
    public final static int CMP_REG16__MEM16_REG16 =  0x39; // CMP REG16/MEM16,REG16 
    public final static int CMP_REG8_REG8__MEM8    =  0x3a; // CMP REG8,REG8/MEM8    
    public final static int CMP_REG16_REG16__MEM16 =  0x3b; // CMP REG16,REG16/MEM16 
   
    public final static int CMP_AL_IMMED8  =  0x3c; // CMP AL,IMMED8 
    public final static int CMP_AX_IMMED16 =  0x3d; // CMP AX,IMMED16
    
    public final static int AAS =  0x3f; // AAS
    public final static int DAS =  0x2f; // DAS
    public final static int AAM =  0xd4; // AAM
    public final static int AAD =  0xd5; // AAD
    public final static int CBW =  0x98; // CBW
    public final static int CWD =  0x99; // CWD
    
    public final static int AND_REG8__MEM8_REG8    =   0x20; // AND REG8/MEM8,REG8   
    public final static int AND_REG16__MEM16_REG16 =   0x21; // AND REG16/MEM16,REG16
    public final static int AND_REG8_REG8__MEM8    =   0x22; // AND REG8,REG8/MEM8   
    public final static int AND_REG16_REG16__MEM16 =   0x23; // AND REG16,REG16/MEM16
    
    public final static int AND_AL_IMMED8  =  0x24; // AND AL,IMMED8 
    public final static int AND_AX_IMMED16 =  0x25; // AND AX,IMMED16
    
    public final static int OR_REG8__MEM8_REG8    =   0x08; // OR REG8/MEM8,REG8   
    public final static int OR_REG16__MEM16_REG16 =   0x09; // OR REG16/MEM16,REG16
    public final static int OR_REG8_REG8__MEM8    =   0x0a; // OR REG8,REG8/MEM8   
    public final static int OR_REG16_REG16__MEM16 =   0x0b; // OR REG16,REG16/MEM16
    
    public final static int OR_AL_IMMED8  = 0x0c; // OR AL,IMMED8  
    public final static int OR_AX_IMMED16 = 0x0d; // OR AX,IMMED16
   
    public final static int XOR_REG8__MEM8_REG8    =   0x30; // XOR REG8/MEM8,REG8   
    public final static int XOR_REG16__MEM16_REG16 =   0x31; // XOR REG16/MEM16,REG16
    public final static int XOR_REG8_REG8__MEM8    =   0x32; // XOR REG8,REG8/MEM8   
    public final static int XOR_REG16_REG16__MEM16 =   0x33; // XOR REG16,REG16/MEM16
    
    public final static int XOR_AL_IMMED8  =  0x34; // XOR AL,IMMED8 
    public final static int XOR_AX_IMMED16 =  0x35; // XOR AX,IMMED16
    
    public final static int TEST_REG8__MEM8_REG8    =  0x84; // TEST REG8/MEM8,REG8   
    public final static int TEST_REG16__MEM16_REG16 =  0x85; // TEST REG16/MEM16,REG16
    public final static int TEST_AL_IMMED8  =  0xa8; // TEST AL,IMMED8 
    public final static int TEST_AX_IMMED16 =  0xa9; // TEST AX,IMMED16
    
    public final static int MOVS_DEST8_SRC8   = 0xa4; // MOVS DEST-STR8,SRC-STR8  
    public final static int MOVS_DEST16_SRC16 = 0xa5; // MOVS DEST-STR16,SRC-STR16
    public final static int CMPS_DEST8_SRC8   = 0xa6; // CMPS DEST-STR8,SRC-STR8  
    public final static int CMPS_DEST16_SRC16 = 0xa7; // CMPS DEST-STR16,SRC-STR16
    public final static int SCAS_DEST8  =  0xae; // SCAS DEST-STR8 
    public final static int SCAS_DEST16 =  0xaf; // SCAS DEST-STR16
    
    public final static int LODS_SRC8   = 0xac; // LODS SRC-STR8 
    public final static int LODS_SRC16  = 0xad; // LODS SRC-STR16
    public final static int STOS_DEST8  = 0xaa; // STOS DEST-STR8 
    public final static int STOS_DEST16 = 0xab; // STOS DEST-STR16
        
    public final static int CALL_NEAR_PROC =  0xe8; // CALL NEAR-PROC
    public final static int CALL_FAR_PROC  =  0x9a; // CALL FAR-PROC
    
    public final static int RET_INTRASEGMENT        = 0xc3; // RET (intrasegment)
    public final static int RET_IMMED16_INTRASEG    = 0xc2; // RET IMMED16 (intraseg)
    public final static int RET_INTERSEGMENT        = 0xcb; // RET (intersegment)
    public final static int RET_IMMED16_INTERSEGMENT= 0xca; // RET IMMED16 (intersegment)
        
    public final static int JMP_NEAR  =  0xe9; // JMP NEAR-LABEL 
    public final static int JMP_SHORT =  0xeb; // JMP SHORT-LABEL
    public final static int JMP_FAR   =  0xea; // JMP FAR-LABEL
    public final static int JO_SHORT  =  0x70; // JO SHORT-LABEL
    public final static int JNO_SHORT =  0x71; // JNO SHORT-LABEL
    
    public final static int JB__JNAE__JC_SHORT  = 0x72; // JB/JNAE/JC SHORT-LABEL
    public final static int JNB__JAE__JNC_SHORT = 0x73; // JNB/JAE/JNC SHORT-LABEL
    public final static int JE__JZ_SHORT   = 0x74; // JE/JZ SHORT-LABEL
    public final static int JNE__JNZ_SHORT = 0x75; // JNE/JNZ SHORT-LABEL
    public final static int JBE__JNA_SHORT = 0x76; // JBE/JNA SHORT-LABEL
    public final static int JNBE__JA_SHORT = 0x77; // JNBE/JA SHORT-LABEL
    public final static int JS_SHORT  = 0x78; // JS SHORT-LABEL
    public final static int JNS_SHORT = 0x79; // JNS SHORT-LABEL
    public final static int JP__JPE_SHORT  =  0x7a; // JP/JPE SHORT-LABEL
    public final static int JNP__JPO_SHORT =  0x7b; // JNP/JPO SHORT-LABEL
    public final static int JL__JNGE_SHORT =  0x7c; // JL/JNGE SHORT-LABEL
    public final static int JNL__JGE_SHORT =  0x7d; // JNL/JGE SHORT-LABEL
    public final static int JLE__JNG_SHORT =  0x7e; // JLE/JNG SHORT-LABEL
    public final static int JNLE__JG_SHORT =  0x7f; // JNLE/JG SHORT-LABEL
    
    public final static int LOOP_SHORT =  0xe2; // LOOP SHORT-LABEL
    public final static int LOOPE__LOOPZ_SHORT  = 0xe1; // LOOPE/LOOPZ SHORT-LABEL
    public final static int LOOPNE__LOOPNZ_SHORT= 0xe0; // LOOPNE/LOOPNZ SHORT-LABEL 
    public final static int JCXZ_SHORT =  0xe3; // JCXZ SHORT-LABEL
    
    public final static int INT_3       =  0xcc; // INT 3
    public final static int INT_IMMED8  =  0xcd; // INT IMMED8 
    public final static int INTO        =  0xce; // INTO
    
    public final static int IRET = 0xcf; // IRET
    public final static int CLC  = 0xf8; // CLC
    public final static int CMC  = 0xf5; // CMC  
    public final static int STC  = 0xf9; // STC
    public final static int CLD  = 0xfc; // CLD
    public final static int STD  = 0xfd; // STD
    public final static int CLI  = 0xfa; // CLI
    public final static int STI  = 0xfb; // STI
    public final static int HLT  = 0xf4; // HLT
    public final static int WAIT = 0x9b; // WAIT
    
    public final static int ESC_0_SOURCE =  0xd8; // ESC 0,SOURCE
    public final static int ESC_1_SOURCE =  0xd9; // ESC 1,SOURCE
    public final static int ESC_2_SOURCE =  0xda; // ESC 2,SOURCE
    public final static int ESC_3_SOURCE =  0xdb; // ESC 3,SOURCE
    public final static int ESC_4_SOURCE =  0xdc; // ESC 4,SOURCE
    public final static int ESC_5_SOURCE =  0xdd; // ESC 5,SOURCE
    public final static int ESC_6_SOURCE =  0xde; // ESC 6,SOURCE
    public final static int ESC_7_SOURCE =  0xdf; // ESC 7,SOURCE
     
    public final static int LOCK =  0xf0; // LOCK
    public final static int NOP  =  0x90; // NOP
    
    public final static int MOD_ADD =   0b000; // ADD
    public final static int MOD_OR  =   0b001; // OR
    public final static int MOD_ADC =   0b010; // ADC
    public final static int MOD_SBB =   0b011; // SBB
    public final static int MOD_AND =   0b100; // AND
    public final static int MOD_SUB =   0b101; // SUB
    public final static int MOD_XOR =   0b110; // XOR
    public final static int MOD_CMP =   0b111; // CMP
    
    public final static int POP_REG16__MEM16 = 0x8f; // POP REG16/MEM16
    public final static int MOD_POP = 0b000; // POP
    
    public final static int MOD_ROL =  0b000; // ROL
    public final static int MOD_ROR =  0b001; // ROR
    public final static int MOD_RCL =  0b010; // RCL
    public final static int MOD_RCR =  0b011; // RCR
    public final static int MOD_SHR =  0b101; // SHR
    public final static int MOD_SAR =  0b111; // SAR
    public final static int MOD_SAL__SHL = 0b100; // SAL/SHL
    
    public final static int MOD_TEST=   0b000; // TEST
    public final static int MOD_NOT =   0b010; // NOT
    public final static int MOD_NEG =   0b011; // NEG
    public final static int MOD_MUL =   0b100; // MUL
    public final static int MOD_IMUL=   0b101; // IMUL
    public final static int MOD_DIV =   0b110; // DIV
    public final static int MOD_IDIV=   0b111; // IDIV
    
    public final static int INC_REG8__MEM8   =   0b000; // INC REG8/MEM8
    public final static int DEC_REG8__MEM8   =   0b001; // DEC REG8/MEM8 
    public final static int INC_REG16__MEM16 =   0b000; // INC REG16/MEM16
    public final static int DEC_REG16__MEM16 =   0b001; // DEC REG16/MEM16 
    
    public final static int CALL_REG16__MEM16_INTRA = 0b010; // CALL REG16/MEM16(intra)
    public final static int CALL_MEM16_INTERSEGMENT = 0b011; // CALL MEM16(intersegment)
    
    public final static int JMP_REG16__MEM16_INTRA = 0b100; // JMP REG16/MEM16(intra)
    public final static int JMP_MEM16_INTERSEGMENT = 0b101; // JMP MEM16(intersegment)
    public final static int PUSH_MEM16 =   0b110; // PUSH MEM16

    public final static int EXT_0X80 = 0x80;  // 0x80
    public final static int EXT_0X81 = 0x81;  // 0x81
    public final static int EXT_0X82 = 0x82;  // 0x82
    public final static int EXT_0X83 = 0x83;  // 0x83
    public final static int EXT_0XD0 = 0xd0;  // 0xd0
    public final static int EXT_0XD1 = 0xd1;  // 0xd1
    public final static int EXT_0XD2 = 0xd2;  // 0xd2
    public final static int EXT_0XD3 = 0xd3;  // 0xd3
    public final static int EXT_0XF6 = 0xf6;  // 0xf6
    public final static int EXT_0XF7 = 0xf7;  // 0xf7
    public final static int EXT_0XFE = 0xfe;  // 0xfe
    public final static int EXT_0XFF = 0xff;  // 0xff

}    
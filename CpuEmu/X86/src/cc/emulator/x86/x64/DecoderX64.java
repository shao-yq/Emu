package cc.emulator.x86.x64;

/**
 * @author Shao Bofeng
 * Date: 2017/8/13.
 */

/**
 *   Instruction Format
 *
 * |-------------------------------------------------------------------------------------------------|
 * |Instruction Prefixes| Opcode         | ModR/M      |  SIB        | Displacement  |   Immediate   |
 * |-------------------------------------------------------------------------------------------------|
 * |Up to four          | 1-,2-,or 3-byte| 1 byte      | 1 byte      | Address       |  Immediate    |
 * |prefixes of         | opcode         |(if required)|(if required)| displacement  |  data of      |
 * |1 byte each         |                |             |             | of 1, 2, or 4 |  1, 2, or 4   |
 * |(optional)          |                |             |             | bytes or none |  bytes or none|
 * |-------------------------------------------------------------------------------------------------|
 *                                            /               \
 *                                 -------------------    --------------------
 *                                 |7 6| 5 4 3| 2 1 0|    |7  6 | 5 4 3| 2 1 0|
 *                                 -------------------    -------------------
 *                                 |Mod| Reg/ | R/M  |    |Scale| index| Base |
 *                                 |   |Opcode|      |    ---------------------
 *                                 -------------------
 *
 */
public class DecoderX64 {

}

package cc.emulator.ui.core;

import cc.emulator.core.cpu.Register;

/**
 * @author Shao Yongqing
 * Date: 2017/9/20.
 */
public interface RegisterVIew {
    Register getRegister(String name);
}

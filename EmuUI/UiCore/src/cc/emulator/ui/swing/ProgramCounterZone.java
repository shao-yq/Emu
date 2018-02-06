package cc.emulator.ui.swing;

import java.awt.*;

/**
 * @author Shao Yongqing
 * Date: 2017/10/17.
 */
public class ProgramCounterZone extends RegisterZone {

    public ProgramCounterZone(){

    }
    public void initUi(){
        super.initUi();
        scrollPane.setPreferredSize(new Dimension(200, 50));
    }
}

package cc.emulator.ui.swing;

import cc.emulator.core.cpu.Instruction;

import javax.swing.*;
import java.awt.*;

/**
 * @author Shao Yongqing
 * Date: 2017/9/24.
 */
public class InstructionPane extends JPanel{
    JTable instructionList;
    Instruction instructions[];
    //创建表头
    String[] columnNames = { "No", "Instruction" };
    //创建显示数据
    Object[][] data = {
            {"0", "aaa"},
            {"1", "bbb"},
            {"2", "ccc"},
            {"3", "ddd"},
    };

    public InstructionPane(){
        super(new BorderLayout());

    }
    public void initUi(){
        instructionList = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(instructionList);

        this.add(scrollPane);
    }

}

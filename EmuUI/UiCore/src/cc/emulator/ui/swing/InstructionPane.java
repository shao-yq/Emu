package cc.emulator.ui.swing;

import cc.emulator.core.cpu.Instruction;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

/**
 * @author Shao Yongqing
 * Date: 2017/9/24.
 */
public class InstructionPane extends JPanel{
    JTable instructionList;
    Instruction instructions[];
    //创建表头
    String[] columnNames = { "No", "Assemble", "binary" };
    //创建显示数据
    Object[][] data = {
            {"0", "aaa", "0000"},
            {"1", "bbb", "1111"},
            {"2", "ccc", "2222"},
            {"3", "ddd", "3333"},
    };

    public InstructionPane(){
        super(new BorderLayout());

    }

    public void setInstructions(Instruction instructions[]){
        this.instructions = instructions;
        // update ui
        updateUi();
    }

    private void updateUi() {
        data = updateData();
    }

    private Object[][] updateData() {

        Object tempData[][] = new Object[instructions.length][];
        for(int i=0; i<instructions.length; i++) {
            tempData[i] = new Object[2];
            tempData[i][0] = ""+i;
            //tempData[i][1] = instructions[i].getAssembleString();
        }

        return tempData;
    }

    public void initUi(){
        instructionList = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(instructionList);
        TableColumn column = null;
        for (int i = 0; i < 3; i++) {
            column = instructionList.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(50);
            } else {
                column.setPreferredWidth(200);
            }
        }
        this.add(scrollPane);
    }

}

package cc.emulator.ui.swing;

import cc.emulator.core.cpu.Instruction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.Vector;

/**
 * @author Shao Yongqing
 * Date: 2017/9/24.
 */
public class InstructionPane extends JPanel{
    JTable instructionList;
    Vector<Instruction> instructions;
    //创建表头
    String[] columnNames = { "No.", "Asm.", "Bin.", "Hex." };
    //Vector<String> columnNames = new Vector<String>();
    //创建显示数据
    Object[][] data = {
            {"0", "Jmp #XXX", "C3A6"},
            {"1", "bbb", "1111"},
            {"2", "ccc", "2222"},
            {"3", "ddd", "3333"},
    };
    //Vector data = new Vector();

    public InstructionPane(){
        super(new BorderLayout());

    }

    public void setInstructions(Vector<Instruction> instructions){
        this.instructions = instructions;
        // update ui
        //instructionList.updateUI();
        updateUI();

    }

//    private void updateUi() {
////        data = updateData();
//    }

//    private Vector updateData() {
//        data.clear();
//        // No.(int), Asm.(String), Bin.(int), Hex.(String)
//        data.add()
//    }

//    private Object[][] updateData() {
//
//        Object tempData[][] = new Object[instructions.length][];
//        for(int i=0; i<instructions.length; i++) {
//            tempData[i] = new Object[2];
//            tempData[i][0] = ""+i;
//            //tempData[i][1] = instructions[i].getAssembleString();
//        }
//
//        return tempData;
//    }

    public void initUi(){
        instructions = new Vector<>();
        instructionList = new JTable(new AbstractTableModel() {
            public String getColumnName(int col) {
                return columnNames[col].toString();
            }
            public int getRowCount() {
                return instructions.size();
                //return data.length;
            }
            public int getColumnCount() { return columnNames.length; }
            public Object getValueAt(int row, int col) {
                //return data[row][col];
                Instruction instruction=instructions.get(row);
                switch (col){
                    case 0:
                        return row;
                    case 1:
                        return instruction.toAsm();
                    case 2:
                        return instruction.toBinary();
                    case 3:
                        return instruction.toHexadecimal();
                    default:
                        return "";
                }
            }
            public boolean isCellEditable(int row, int col)
            { return false; }
            public void setValueAt(Object value, int row, int col) {
                data[row][col] = value;
                fireTableCellUpdated(row, col);
            }
        });//new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(instructionList);
        TableColumn column = null;
        for (int i = 0; i < columnNames.length; i++) {
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

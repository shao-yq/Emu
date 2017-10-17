package cc.emulator.ui.swing;

import cc.emulator.core.cpu.Instruction;
import cc.emulator.core.cpu.register.DividableRegister;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import java.util.Vector;

/**
 * @author Shao Yongqing
 * Date: 2017/10/17.
 */
public class AccumulatorZone extends JPanel {
    JTable regisgerList;

    public AccumulatorZone(){

    }

    public void setDividableRegisters(Vector<DividableRegister> dividableRegisters) {
        this.dividableRegisters = dividableRegisters;
        regisgerList.invalidate();
    }

    Vector<DividableRegister> dividableRegisters;
    //创建表头
    String[] columnNames = { "H.", "L.", "X." };
    public void initUi(){
        dividableRegisters = new Vector<>();
        regisgerList = new JTable(new AbstractTableModel() {
            public String getColumnName(int col) {
                return columnNames[col].toString();
            }
            public int getRowCount() {
                return dividableRegisters.size();
                //return data.length;
            }
            public int getColumnCount() { return columnNames.length; }
            public Object getValueAt(int row, int col) {
                //return data[row][col];
                DividableRegister dividableRegister=dividableRegisters.get(row);
                switch (col){
                    case 0:
                        return dividableRegister.getH();
                    case 1:
                        return dividableRegister.getL();
                    case 2:
                        return dividableRegister.getX();
                    default:
                        return "";
                }
            }
            public boolean isCellEditable(int row, int col)
            { return false; }
            public void setValueAt(Object value, int row, int col) {
                DividableRegister dividableRegister=dividableRegisters.get(row);
                int iv = 0;
                switch (col){
                    case 0:
                        dividableRegister.setH(iv);
                        break;
                    case 1:
                        dividableRegister.setL(iv);
                        break;
                    case 2:
                        dividableRegister.setX(iv);
                        break;
                    default:
                        break;
                }

                fireTableCellUpdated(row, col);
            }
        });//new JTable(data, columnNames);

        TableColumn column = null;
        for (int i = 0; i < columnNames.length; i++) {
            column = regisgerList.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(50);
            } else {
                column.setPreferredWidth(200);
            }
        }
        this.add(regisgerList);
    }
}

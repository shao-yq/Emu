package cc.emulator.ui.swing;

import cc.emulator.core.cpu.Instruction;
import cc.emulator.core.cpu.register.DividableRegister;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import java.awt.*;
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
    String[] columnNames = { "Reg", "H.", "L.", "X." };
    public void initUi(){
        dividableRegisters = new Vector<>();
        regisgerList = new JTable(new AbstractTableModel() {
            public String getColumnName(int col) {
                return columnNames[col].toString();
            }
            public int getRowCount() {
                return dividableRegisters.size();
            }
            public int getColumnCount() { return columnNames.length; }
            public Object getValueAt(int row, int col) {
                DividableRegister dividableRegister=dividableRegisters.get(row);
                String value = "";
                switch (col){
                    case 0:
                        value = dividableRegister.getName();

                        break;
                    case 1:
                        //value = Integer.toHexString(dividableRegister.getH());
                        value = String.format("%02x", dividableRegister.getH());
                        break;
                    case 2:
                        //value =  Integer.toHexString(dividableRegister.getL());
                        value = String.format("%02x", dividableRegister.getL());
                        break;
                    case 3:
                        //value =  Integer.toHexString(dividableRegister.getX());
                        value = String.format("%04x", dividableRegister.getX());
                        break;
                    default:
                        value =  "";
                        break;
                }
                return value;
            }

            public boolean isCellEditable(int row, int col)
            { return false; }
            public void setValueAt(Object value, int row, int col) {
                DividableRegister dividableRegister=dividableRegisters.get(row);
                int iv = 0;
                switch (col){
                    case 0:
                        break;
                    case 1:
                        dividableRegister.setH(iv);
                        break;
                    case 2:
                        dividableRegister.setL(iv);
                        break;
                    case 3:
                        dividableRegister.setX(iv);
                        break;
                    default:
                        break;
                }

                fireTableCellUpdated(row, col);
            }
        });//new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(regisgerList);
        scrollPane.setPreferredSize(new Dimension(200, 100));

        TableColumn column = null;
        for (int i = 0; i < columnNames.length; i++) {
            column = regisgerList.getColumnModel().getColumn(i);
//            if (i == 0) {
//                column.setPreferredWidth(40);
//            } else {
//                column.setPreferredWidth(50);
//            }
        }
        this.add(scrollPane);
    }
}

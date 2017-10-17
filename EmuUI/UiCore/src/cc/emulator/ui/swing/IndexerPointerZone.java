package cc.emulator.ui.swing;

import cc.emulator.core.cpu.register.DividableRegister;
import cc.emulator.core.cpu.register.GeneralRegister;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.util.Vector;

/**
 * @author Shao Yongqing
 * Date: 2017/10/17.
 */
public class IndexerPointerZone extends JPanel {
    JTable regisgerList;

    public IndexerPointerZone(){

    }

    public void setGeneralRegisters(Vector<GeneralRegister> registers) {
        this.registers = registers;
        regisgerList.invalidate();
    }

    Vector<GeneralRegister> registers;
    //创建表头
    String[] columnNames = { "Reg", "Value" , "HEX"};
    public void initUi(){
        registers = new Vector<>();
        regisgerList = new JTable(new AbstractTableModel() {
            public String getColumnName(int col) {
                return columnNames[col].toString();
            }
            public int getRowCount() {
                return registers.size();
            }
            public int getColumnCount() { return columnNames.length; }
            public Object getValueAt(int row, int col) {
                GeneralRegister register=registers.get(row);
                String value = "";
                switch (col){
                    case 0:
                        value = register.getName();
                        break;
                    case 1:
                        //value = Integer.toHexString(dividableRegister.getH());
                        value = String.format("%d", register.getData());
                        break;
                    case 2:
                        //value =  Integer.toHexString(dividableRegister.getL());
                        value = String.format("%04x", register.getData());
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
                GeneralRegister register=registers.get(row);
                int iv = 0;
                switch (col){
                    case 0:
                        break;
                    case 1:
                        register.setData(iv);
                        break;
                    case 2:
                        register.setData(iv);
                        break;
                    default:
                        break;
                }

                fireTableCellUpdated(row, col);
            }
        });//new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(regisgerList);
        TableColumn column = null;
        for (int i = 0; i < columnNames.length; i++) {
            column = regisgerList.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(40);
            } else {
                column.setPreferredWidth(50);
            }
        }
        this.add(scrollPane);
    }
}

package cc.emulator.ui.swing;

import cc.emulator.core.cpu.Instruction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

/**
 * @author Shao Yongqing
 * Date: 2017/10/17.
 */
public class MemoryPane extends JPanel {
    static class MemoryBlock {
        int[]base;
        int offset;
        int size;

        public MemoryBlock(int[] base, int offset, int size) {
            this.base = base;
            this.offset = offset;
            this.size = size;
        }

        int get(int pos){
            return base[offset+pos];
        }
        void set(int pos, int value) {
            base[offset+pos] = value;
        }
    } ;

    MemoryBlock memoryBlock;

    JTable dataTable;
    //创建表头
    String[] columnNames = { "No.", "Dec.", "Hex." };

    public MemoryPane(MemoryBlock memoryBlock){
        super(new BorderLayout());
        this.memoryBlock = memoryBlock;
        initUi();
    }

    public MemoryPane(int[] base, int offset, int size){
        this(new MemoryBlock(base, offset, size));
    }

    void initUi() {
        dataTable = new JTable(new AbstractTableModel() {
            public String getColumnName(int col) {
                return columnNames[col].toString();
            }
            public int getRowCount() {
                return memoryBlock.size;
            }
            public int getColumnCount() { return columnNames.length; }
            public Object getValueAt(int row, int col) {
                int data=memoryBlock.get(row);
                switch (col){
                    case 0:
                        return row;
                    case 1:
                        return data;
                    case 2:
                        return Integer.toHexString(data);
                    default:
                        return "";
                }
            }
            public boolean isCellEditable(int row, int col) {
                return false;
            }
            public void setValueAt(Object value, int row, int col) {
                switch (col){
                    case 0:
                        break ;
                    case 1:
                        memoryBlock.set(row, Integer.parseInt((String) value, 10));
                        break;
                    case 2:
                        memoryBlock.set(row, Integer.parseInt((String) value, 16));
                        break;
                    default:
                        break ;
                }
                fireTableCellUpdated(row, col);
            }
        });
        JScrollPane scrollPane = new JScrollPane(dataTable);
        TableColumn column = null;
        for (int i = 0; i < columnNames.length; i++) {
            column = dataTable.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(50);
            } else {
                column.setPreferredWidth(100);
            }
        }
        this.add(scrollPane);
    }


}

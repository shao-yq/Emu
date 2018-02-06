package cc.emulator.ui.swing;

import cc.emulator.core.cpu.Register;
import cc.emulator.core.cpu.register.StatusRegister;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

/**
 * @author Shao Yongqing
 * Date: 2018/1/31.
 */
public class StatusRegisterZone extends JPanel{
    StatusRegister statusRegister;
    /**
     *
     *       15 14 13 12 11 10  9  8  7  6  5  4  3  2  1  0
     *       x  x  x  x  OF DF IF TF SF ZF x  AF x  PF x  CF
     *
     */

    JTable flagTable;
    String[] columnNames = { "x", "x", "x", "x", "OF", "DF", "IF", "TF", "SF", "ZF", "x", "AF", "x", "PF" ,"x", "CF"};

    public StatusRegisterZone(){
        super(new BorderLayout());
    }
    public StatusRegisterZone(StatusRegister statusRegister){
        super(new BorderLayout());
        this.statusRegister = statusRegister;
    }

    public void initUi(){
        flagTable = new JTable(new AbstractTableModel() {
            public String getColumnName(int col) {
                return columnNames[col].toString();
            }
            public int getRowCount() {
                return 1;
            }
            public int getColumnCount() { return columnNames.length; }
            public Object getValueAt(int row, int col) {
                if(row!=0)
                    return "";

                int value = statusRegister.getData();
                value = value>>>col;
                value &= 1;

                return value;
            }

            public boolean isCellEditable(int row, int col)
            { return false; }
            public void setValueAt(Object value, int row, int col) {

                fireTableCellUpdated(row, col);
            }
        });//new JTable(data, columnNames);

        JScrollPane scrollPane = new JScrollPane(flagTable);
        scrollPane.setPreferredSize(new Dimension(300, 50));

        TableColumn column = null;
        for (int i = 0; i < columnNames.length; i++) {
            column = flagTable.getColumnModel().getColumn(i);
//            if (i == 0) {
//                column.setPreferredWidth(40);
//            } else {
//                column.setPreferredWidth(50);
//            }
        }
        this.add(scrollPane, BorderLayout.CENTER);
        flagValue = new JLabel();
        this.add(flagValue, BorderLayout.SOUTH);
    }

    JLabel flagValue;

    public void setStatusRegister(StatusRegister statusRegister) {
        this.statusRegister = statusRegister;
        updateUi();

    }

    private void updateUi() {
        flagValue.setText(Integer.toHexString(statusRegister.getData()));
        invalidate();
    }

    @Override
    public void print(Graphics g) {
        flagValue.setText(Integer.toHexString(statusRegister.getData()));
        super.print(g);

    }
}

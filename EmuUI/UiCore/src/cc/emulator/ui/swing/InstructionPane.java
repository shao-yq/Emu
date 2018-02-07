package cc.emulator.ui.swing;

import cc.emulator.core.MemoryManager;
import cc.emulator.core.computer.ProgramMemoryInfo;
import cc.emulator.core.cpu.Cpu;
import cc.emulator.core.cpu.Instruction;
import cc.emulator.core.cpu.InstructionDecoder;
import cc.emulator.core.cpu.InstructionQueue;

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
    Vector<Instruction> instructions = new Vector<>() ;
    //创建表头
    String[] columnNames = { "Addr.", "Asm.", "Bin.", "Hex." };
    //Vector<String> columnNames = new Vector<String>();
//    //创建显示数据
//    Object[][] data = {
//            {"0", "Jmp #XXX", "C3A6"},
//            {"1", "bbb", "1111"},
//            {"2", "ccc", "2222"},
//            {"3", "ddd", "3333"},
//    };
    //Vector data = new Vector();
    Cpu cpu;
    ProgramMemoryInfo programMemoryInfo;
    private final int[] memoryBase;

    public InstructionPane(Cpu cpu, ProgramMemoryInfo programMemoryInfo, int[] memoryBase){
        super(new BorderLayout());
        this.cpu = cpu;
        this.programMemoryInfo = programMemoryInfo;
        this.memoryBase = memoryBase;
        currentAddress = cpu.currentAddress();
    }



    public void setInstructions(Vector<Instruction> instructions){
        this.instructions = instructions;
        // update ui
        //instructionList.updateUI();
        updateUI();
        instructionList.invalidate();

    }

    private Vector<Instruction> decode(Cpu cpu, ProgramMemoryInfo programMemoryInfo, int[] memoryBase, int count) {
        currentAddress = cpu.currentAddress();

        Vector<Instruction> instructions = new Vector<Instruction>();
        final int queueSize = cpu.getBusInterfaceUnit().getInstructionQueue().getQueueSize();
        InstructionQueue instructionQueue = new InstructionQueue(){
            int[] queue = new int[queueSize];
            @Override
            public void reset(){
                //for (int i = 0; i < 6; i++)
                //    queue[i] = 0;
                current=0;
            }
            int current;
            @Override
            public void fillInstructionQueue(int instruction){
                queue[current]=instruction;
                current++;
            }

            @Override
            public int[] getQueue() {
                return queue;
            }

            @Override
            public int getQueueSize() {
                return queueSize;
            }
        };
        int offset = 0;
        int base = programMemoryInfo.getBase();
        int programSize = programMemoryInfo.getSize();
        int memoryUpper = base+programSize;

        InstructionDecoder decoder= cpu.getInstructionUnit().createDecoder(); //new Decoder8086();
        int currentAddr = currentAddress;
        for(int i=0; i<count; i++) {
            if(currentAddr+offset+cpu.getBusInterfaceUnit().getInstructionQueue().getQueueSize() >=memoryBase.length )
                break;

            // Featch raw instruction
            cpu.fetchRawInstructions(offset, instructionQueue);
            // Decode
            Instruction instruction = decoder.decode(instructionQueue);
            offset += instruction.getLength();

            instructions.add(instruction);
        }

        return instructions;
    }

    int currentAddress;
    int getRowAddress(int row){
        int rowAddress = currentAddress;    //  cpu.currentAddress();
        for(int i=0; i<row; i++){
            Instruction instruction=instructions.get(i);
            rowAddress += instruction.getLength();
        }
        return rowAddress;
    }
    public void initUi(){
        MemoryManager memoryManager = cpu.getMemoryManager();

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
                        int rowAddress = getRowAddress(row);
                        return Integer.toHexString(rowAddress);
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
                //data[row][col] = value;
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
    int instructionCount = 10;
    public void refreshUI() {
        if(cpu!=null)
            instructions = decode(cpu,programMemoryInfo, memoryBase, instructionCount);
        repaint();
    }

    @Override
    public void invalidate() {
//        if(cpu!=null)
//            instructions = decode(cpu,programMemoryInfo, memoryBase, instructionCount);

        super.invalidate();
    }
}

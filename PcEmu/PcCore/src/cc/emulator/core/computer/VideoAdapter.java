package cc.emulator.core.computer;

import cc.emulator.core.DisplayController;

public abstract class VideoAdapter implements DisplayController, Adapter {
    private int[] memory;
    int videoBase ; //  0xb8000;
    /** The registers accessed by the CPU. */
    private int[] registers;

    public VideoAdapter(int[] memoryBase){
        memory = memoryBase;
        registers = createRegister();
    }
    protected abstract int[] createRegister();

    /**
     * Returns the value of the register with the specified index.
     *
     * @param index
     *            the index
     * @return the value
     */
    @Override
    public int getRegister(int index) {
        return registers[index];
    }

    protected void setRegisterValue(int index, int val) {
        registers[index] = val;
    }

    public int getVideoBase(){
        return videoBase;
    }

    public void setVideoBase(int videoBase) {
        this.videoBase = videoBase;
    }

    public void init(){
    }

    int screenColumn;   // = 80;
    public int getScreenColumn(){
        return screenColumn;
    }
    int screenRow;  // = 25;

    public int getScreenRow(){
        return screenRow;
    }

    public void setScreenColumn(int screenColumn) {
        this.screenColumn = screenColumn;
    }

    public void setScreenRow(int screenRow) {
        this.screenRow = screenRow;
    }

    public int getCharacter(int row, int col) {
        return memory[videoBase + 2 * (col + row * screenColumn)];       // cpu.memory[0xb8000 + 2 * (x + y * 80)];
    }

    public int getAttribute(int row, int col) {
        return memory[videoBase + 2 * (col + row * screenColumn) + 1];   //  cpu.memory[0xb8000 + 2 * (x + y * 80) + 1];;
    }

    public int[] getMemoryBase() {
        return memory;
    }

}

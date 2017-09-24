package cc.emulator.core.cpu;

/**
 * @author Shao Yongqing
 * Date: 2017/9/20.
 */
public abstract class AbstractInstruction implements Instruction, Cloneable{
    // Instruction length
    protected int length;
    // Raw data for the instruction decoded
    protected int rawData[] = null;

    public Object clone(){
        Instruction o = null;
        try{
            o = (Instruction)super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }

        // copy other ohjects(prefixes, raw data, etc.) if available
        copyOthers(o);

        return o;
    }
    protected abstract void copyOthers(Instruction instruction);

    public void decodeMe(int[] raw, int startIndex){
        // Decode opcode
        decode(raw, startIndex);
        // Save raw data
        saveRaw(raw, length);
    }

    protected void setLength(int len) {
        length = len;
    }

    protected void incLength(int delta){
        length += delta;
    }

    @Override
    public int getLength(){
        return length;
    }

    public abstract void decode(int[] raw,int startIndex);


    protected void saveRaw(int[] raw, int length) {
        rawData =  new int[length];
        for(int i=0; i<length; i++){
            rawData[i] = raw[i];
        }
    }


    @Override
    public String toAsm() {
        return "Asm.";
    }

    @Override
    public String toBinary() {
        return "Bin.";
    }

    @Override
    public String toHexadecimal() {
        return "Hex.";
    }

}

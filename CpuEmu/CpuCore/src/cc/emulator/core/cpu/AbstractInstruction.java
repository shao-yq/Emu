package cc.emulator.core.cpu;

/**
 * @author Shao Yongqing
 * Date: 2017/9/20.
 */
public abstract class AbstractInstruction implements Instruction, Cloneable{
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
}

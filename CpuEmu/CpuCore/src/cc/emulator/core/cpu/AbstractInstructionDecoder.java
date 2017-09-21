package cc.emulator.core.cpu;

/**
 * @author Shao Yongqing
 * Date: 2017/9/21.
 */
public abstract class AbstractInstructionDecoder implements InstructionDecoder{
    public AbstractInstructionDecoder(){
        // init the instrution templates
        if(instructions==null){
            initInstructionTemplates();
        }
    }

    @Override
    public Instruction decode(InstructionQueue queue) {
        return decode(queue.getQueue());
    }

    protected abstract Instruction decode(int[] queue);

    @Override
    public void reset() {
        // Dummy
    }

    protected Instruction instructions[]=null;
    protected String instructionNames[]=null;

    // Initialize the instruction classes
    protected void initInstructionTemplates(){
        if(instructionNames==null){
            initInstructionNames();
        }
        instructions = new Instruction[instructionNames.length];

        for(int i=0; i<instructionNames.length; i++){
            try {
                Class instructionClass = Class.forName(getFullInstructionName(instructionNames[i]));
                try {
                    instructions[i] = (Instruction) instructionClass.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    protected String getFullInstructionName(String shorName){
        return getPackagePrefix()+shorName;
    }

    protected abstract String getPackagePrefix();

    protected abstract void initInstructionNames();


}

package cc.emulator.core.computer;

import cc.emulator.core.*;
import cc.emulator.core.Peripheral;
import cc.emulator.core.cpu.Cpu;
import cc.emulator.core.cpu.intel.Intel8086;

import fr.neatmonster.ibmpc.*;

/**
 * @author Shao Yongqing
 * Date: 2017/7/25.
 */
public class PersonalComputer  extends BaseComputer {
        /*
     * External Components
     */
    /**
     * Intel 8237 - Direct Memory Access Controller
     *
     * @see fr.neatmonster.ibmpc.Intel8237
     */
    private final DirectMemoryAccess dma         = new Intel8237();

    /**
     * Intel 8259 - Programmable Interrupt Controller
     *
     * @see fr.neatmonster.ibmpc.Intel8259
     */
    private final ProgrammableInterrupt pic         = new Intel8259();

    /**
     * Intel 8253 - Programmable Interval Timer
     *
     * @see fr.neatmonster.ibmpc.Intel8253
     */
    private final ProgrammableIntervalTimer pit         = new Intel8253(pic);

    /**
     * Intel 8255 - Programmable Peripheral Interface
     *
     * @see fr.neatmonster.ibmpc.Intel8255
     */
    private final ProgrammablePeripheralInterface ppi         = new Intel8255(pic);

    /**
     * Motorola 6845 - Cathode Ray Tube Controller
     *
     * @see fr.neatmonster.ibmpc.Motorola6845
     */
    private final DisplayController crtc        = new Motorola6845();

    /**
     * IBMCGA - Color Graphics Adapter
     *
     * @see fr.neatmonster.ibmpc.IBMCGA
     */
    @SuppressWarnings("unused")
    private  IBMCGA       cga ;//         = new IBMCGA(this, ppi, crtc);

    /**
     * An array containing all peripherals.
     *
     * The CGA, technically a peripheral, interacts directly with the CPU in
     * this implementation and by doing so does not use the I/O space.
     */
    private  cc.emulator.core.Peripheral[] peripherals ;  // = new Peripheral[] { dma, pic, pit, ppi, crtc };

    @Override
    public void reset() {
        super.reset();

        cga         = new IBMCGA((Intel8086) cpu, (Intel8255) ppi, (Motorola6845) crtc);
        peripherals = new Peripheral[] { dma, pic, pit, ppi, crtc };

        cpu.setPeripherals(peripherals);

        cpu.setPic(pic);
        cpu.setPit(pit);
    }

    public PersonalComputer(String configFile) {
        super(configFile);

    }


    protected Cpu createCpu(MemoryManager mm){
        Intel8086 cpu = new Intel8086(mm);

        return cpu;
    }

    protected MemoryManager createMemoryManager(){
        return new  MemoryManager();
    }

}

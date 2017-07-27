package cc.emulator.computer;

import cc.emulator.core.*;
import cc.emulator.core.Peripheral;
import cc.emulator.core.computer.Display;
import cc.emulator.core.computer.MemoryManager;
import cc.emulator.core.computer.PersonalComputer;
import cc.emulator.core.cpu.Cpu;
import cc.emulator.core.cpu.intel.Intel8086;

import fr.neatmonster.ibmpc.*;

/**
 * @author Shao Yongqing
 * Date: 2017/7/25.
 */
public class IBMPC5150 extends PersonalComputer {
        /*
     * External Components
     */
    /**
     * Intel 8237 - Direct Memory Access Controller
     *
     * @see fr.neatmonster.ibmpc.Intel8237
     */
    protected DirectMemoryAccess createDmaController(){
        return new Intel8237();
    }

    /**
     * Intel 8259 - Programmable Interrupt Controller
     *
     * @see fr.neatmonster.ibmpc.Intel8259
     */
    protected  ProgrammableInterrupt createProgrammableInterruptController(){
        return new Intel8259();
    }
    /**
     * Intel 8253 - Programmable Interval Timer
     *
     * @see fr.neatmonster.ibmpc.Intel8253
     */

    protected  ProgrammableIntervalTimer createProgrammableIntervalTimer(ProgrammableInterrupt pic){
        return new Intel8253(pic);
    }
    /**
     * Intel 8255 - Programmable Peripheral Interface
     *
     * @see fr.neatmonster.ibmpc.Intel8255
     */
    protected  ProgrammablePeripheralInterface createProgrammablePeripheralInterface(ProgrammableInterrupt pic){
        return new Intel8255(pic);
    }

    /**
     * Motorola 6845 - Cathode Ray Tube Controller
     *
     * @see fr.neatmonster.ibmpc.Motorola6845
     */
    protected  DisplayController createDisplayController(){
        return new Motorola6845();
    }
    /**
     * IBMCGA - Color Graphics Adapter
     *
     * @see fr.neatmonster.ibmpc.IBMCGA
     */
    @SuppressWarnings("unused")
    //protected   Display display;//         = new IBMCGA(this, ppi, crtc);
    protected Display createDisplay(){
        return new IBMCGA((Intel8086) cpu, (Intel8255) ppi, (Motorola6845) crtc);
    }
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

        peripherals = new Peripheral[] { dma, pic, pit, ppi, crtc };
        cpu.setPeripherals(peripherals);
        cpu.setPic(pic);
        cpu.setPit(pit);
    }

    public IBMPC5150(String configFile) {
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

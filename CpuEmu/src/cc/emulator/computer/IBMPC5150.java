package cc.emulator.computer;

import cc.emulator.core.*;
import cc.emulator.core.Peripheral;
import cc.emulator.core.computer.*;
import cc.emulator.core.cpu.Cpu;
import cc.emulator.arch.x86.intel.Intel8086;

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
    protected ProgrammableInterruptController createProgrammableInterruptController(){
        return new Intel8259();
    }
    /**
     * Intel 8253 - Programmable Interval Timer
     *
     * @see fr.neatmonster.ibmpc.Intel8253
     */

    protected  ProgrammableIntervalTimer createProgrammableIntervalTimer(ProgrammableInterruptController pic){
        return new Intel8253(pic);
    }
    /**
     * Intel 8255 - Programmable Peripheral Interface
     *
     * @see fr.neatmonster.ibmpc.Intel8255
     */
    protected  ProgrammablePeripheralInterface createProgrammablePeripheralInterface(ProgrammableInterruptController pic){
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
        return new IBMCGA((Intel8086) getMainBoard().getCpu(), (Intel8255) ppi, (Motorola6845) crtc);
    }
    /**
     * An array containing all peripherals.
     *
     * The CGA, technically a peripheral, interacts directly with the CPU in
     * this implementation and by doing so does not use the I/O space.
     */
    private  cc.emulator.core.Peripheral[] peripherals ;  // = new Peripheral[] { dma, pic, pit, ppi, crtc };

    @Override
    public MainBoard getMainBoard() {
        return mainBoard;
    }

    @Override
    public KeyBoard getKeyBoard() {
        return null;
    }

    @Override
    public VideoAdapter getVideoAdapter() {
        return null;
    }

    @Override
    public AudioAdapter getAudioAdapter() {
        return null;
    }

    @Override
    public NetworkAdapter getNetworkAdapter() {
        return null;
    }

    @Override
    public Display getDisplay() {
        return null;
    }

    @Override
    public Box getBox() {
        return null;
    }

    @Override
    public StorageDriver[] getStorageDrivers() {
        return new StorageDriver[0];
    }

    @Override
    public void reset() {
        super.reset();
        Cpu cpu = getMainBoard().getCpu();
        peripherals = new Peripheral[] { dma, pic, pit, ppi, crtc };
        cpu.setPeripherals(peripherals);
        cpu.setPic(pic);
        cpu.setPit(pit);
    }

    @Override
    protected MainBoard createMainBoard() {
        return new PC5150MainBoard();
    }

    public IBMPC5150(String configFile) {
        super(configFile);

    }



}

package cc.emulator.computer;

import cc.emulator.core.*;
import cc.emulator.core.computer.*;
import cc.emulator.core.computer.swing.Keyboard;
import cc.emulator.core.cpu.Cpu;
import cc.emulator.x86.i8086.Intel8086;

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
     * @see Intel8237
     */
    protected DirectMemoryAccess createDmaController(){
        return new Intel8237();
    }

    /**
     * Intel 8259 - Programmable Interrupt Controller
     *
     * @see Intel8259
     */
    protected ProgrammableInterruptController createProgrammableInterruptController(){
        return new Intel8259();
    }
    /**
     * Intel 8253 - Programmable Interval Timer
     *
     * @see Intel8253
     */

    protected ProgrammableIntervalTimer createProgrammableIntervalTimer(ProgrammableInterruptController pic){
        return new Intel8253(pic);
    }
    /**
     * Intel 8255 - Programmable Peripheral Interface
     *
     * @see Intel8255
     */
    protected ProgrammablePeripheralInterface createProgrammablePeripheralInterface(ProgrammableInterruptController pic){
        return new Intel8255(pic);
    }


    /**
     *  new a DisplayController
     * @return DisplayController Motorola 6845 - Cathode Ray Tube Controller
     */
    protected DisplayController createDisplayController(){
        return new Motorola6845();
    }

    /**
     *
     * @return Display , IBMCGA - Color Graphics Adapter
     */
    protected Display createDisplay(){
        return new IBMCGA( getMainBoard().getCpu(), (Motorola6845) crtc);
    }

    /**
     *
     * @return keyboard instance
     */
    protected KeyBoard createKeyBoard() {
        return new Keyboard(ppi);
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
        return keyBoard;
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
        return display;
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
        Cpu cpu = getMainBoard().getCpu(0);
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

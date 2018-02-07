package cc.emulator.core.computer.swing;

import cc.emulator.core.ProgrammablePeripheralInterface;
import cc.emulator.core.computer.KeyBoard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Shao Yongqing
 * Date: 2018/2/7.
 */
public class Keyboard implements KeyBoard, KeyListener {

    /**
     * Intel 8255 - Programmable Peripheral Interface
     *
     * @see ProgrammablePeripheralInterface
     */
    private ProgrammablePeripheralInterface ppi;

    /**
     * Instantiates a new Keyboard.
     * @param ppi
     */
    public Keyboard (ProgrammablePeripheralInterface ppi){
        this.ppi = ppi;
    }

    /**
     * Returns the scan code associated to the specified key code.
     *
     * @param keyCode
     *            the key code
     * @param keyLocation
     *            the key location
     */
    public int getScanCode(final int keyCode, final int keyLocation) {
        switch (keyCode) {
            case KeyEvent.VK_ESCAPE:
                return 0x01;
            case KeyEvent.VK_1:
                return 0x02;
            case KeyEvent.VK_2:
                return 0x03;
            case KeyEvent.VK_3:
                return 0x04;
            case KeyEvent.VK_4:
                return 0x05;
            case KeyEvent.VK_5:
                return 0x06;
            case KeyEvent.VK_6:
                return 0x07;
            case KeyEvent.VK_7:
                return 0x08;
            case KeyEvent.VK_8:
                return 0x09;
            case KeyEvent.VK_9:
                return 0x0a;
            case KeyEvent.VK_0:
                return 0x0b;
            case KeyEvent.VK_MINUS:
                if (keyLocation == KeyEvent.KEY_LOCATION_STANDARD)
                    return 0x0c;
                if (keyLocation == KeyEvent.KEY_LOCATION_NUMPAD)
                    return 0x4a;
                break;
            case KeyEvent.VK_EQUALS:
                return 0x0d;
            case KeyEvent.VK_BACK_SPACE:
                return 0x0e;
            case KeyEvent.VK_TAB:
                return 0x0f;
            case KeyEvent.VK_Q:
                return 0x10;
            case KeyEvent.VK_W:
                return 0x11;
            case KeyEvent.VK_E:
                return 0x12;
            case KeyEvent.VK_R:
                return 0x13;
            case KeyEvent.VK_T:
                return 0x14;
            case KeyEvent.VK_Y:
                return 0x15;
            case KeyEvent.VK_U:
                return 0x16;
            case KeyEvent.VK_I:
                return 0x17;
            case KeyEvent.VK_O:
                return 0x18;
            case KeyEvent.VK_P:
                return 0x19;
            case KeyEvent.VK_OPEN_BRACKET:
                return 0x1a;
            case KeyEvent.VK_CLOSE_BRACKET:
                return 0x1b;
            case KeyEvent.VK_ENTER:
                return 0x1c;
            case KeyEvent.VK_CONTROL:
                return 0x1d;
            case KeyEvent.VK_A:
                return 0x1e;
            case KeyEvent.VK_S:
                return 0x1f;
            case KeyEvent.VK_D:
                return 0x20;
            case KeyEvent.VK_F:
                return 0x21;
            case KeyEvent.VK_G:
                return 0x22;
            case KeyEvent.VK_H:
                return 0x23;
            case KeyEvent.VK_J:
                return 0x24;
            case KeyEvent.VK_K:
                return 0x25;
            case KeyEvent.VK_L:
                return 0x26;
            case KeyEvent.VK_SEMICOLON:
                return 0x27;
            case KeyEvent.VK_QUOTE:
                return 0x28;
            case KeyEvent.VK_BACK_QUOTE:
                return 0x29;
            case KeyEvent.VK_SHIFT:
                if (keyLocation == KeyEvent.KEY_LOCATION_LEFT)
                    return 0x2a;
                if (keyLocation == KeyEvent.KEY_LOCATION_RIGHT)
                    return 0x36;
                break;
            case KeyEvent.VK_BACK_SLASH:
                return 0x2b;
            case KeyEvent.VK_Z:
                return 0x2c;
            case KeyEvent.VK_X:
                return 0x2d;
            case KeyEvent.VK_C:
                return 0x2e;
            case KeyEvent.VK_V:
                return 0x2f;
            case KeyEvent.VK_B:
                return 0x30;
            case KeyEvent.VK_N:
                return 0x31;
            case KeyEvent.VK_M:
                return 0x32;
            case KeyEvent.VK_COMMA:
                return 0x33;
            case KeyEvent.VK_PERIOD:
                return 0x34;
            case KeyEvent.VK_SLASH:
                return 0x35;
            case KeyEvent.VK_PRINTSCREEN:
                return 0x37;
            case KeyEvent.VK_ALT:
                return 0x38;
            case KeyEvent.VK_SPACE:
                return 0x39;
            case KeyEvent.VK_CAPS_LOCK:
                return 0x3a;
            case KeyEvent.VK_F1:
                return 0x3b;
            case KeyEvent.VK_F2:
                return 0x3c;
            case KeyEvent.VK_F3:
                return 0x3d;
            case KeyEvent.VK_F4:
                return 0x3e;
            case KeyEvent.VK_F5:
                return 0x3f;
            case KeyEvent.VK_F6:
                return 0x40;
            case KeyEvent.VK_F7:
                return 0x41;
            case KeyEvent.VK_F8:
                return 0x42;
            case KeyEvent.VK_F9:
                return 0x43;
            case KeyEvent.VK_F10:
                return 0x44;
            case KeyEvent.VK_NUM_LOCK:
                return 0x45;
            case KeyEvent.VK_SCROLL_LOCK:
                return 0x46;
            case KeyEvent.VK_HOME:
                return 0x47;
            case KeyEvent.VK_UP:
                return 0x48;
            case KeyEvent.VK_PAGE_UP:
                return 0x49;
            case KeyEvent.VK_LEFT:
                return 0x4b;
            case KeyEvent.VK_RIGHT:
                return 0x4d;
            case KeyEvent.VK_PLUS:
                return 0x4e;
            case KeyEvent.VK_END:
                return 0x4f;
            case KeyEvent.VK_DOWN:
                return 0x50;
            case KeyEvent.VK_PAGE_DOWN:
                return 0x51;
            case KeyEvent.VK_INSERT:
                return 0x52;
            case KeyEvent.VK_DELETE:
                return 0x53;
        }
        return 0x00;
    }

    /**
     * (non-Javadoc)
     *
     * @see java.awt.event.KeyListener#keyPressed(KeyEvent)
     */
    @Override
    public void keyPressed(final KeyEvent e) {
        final int scanCode = getScanCode(e.getKeyCode(), e.getKeyLocation());
        if (scanCode > 0)
            ppi.keyTyped(scanCode);
    }

    /**
     * (non-Javadoc)
     *
     * @see java.awt.event.KeyListener#keyReleased(KeyEvent)
     */
    @Override
    public void keyReleased(final KeyEvent e) {
        final int scanCode = getScanCode(e.getKeyCode(), e.getKeyLocation());
        if (scanCode > 0)
            ppi.keyTyped(0x80 | scanCode);
    }

    /**
     * (non-Javadoc)
     *
     * @see java.awt.event.KeyListener#keyTyped(KeyEvent)
     */
    @Override
    public void keyTyped(final KeyEvent e) {}


}

package cc.emulator.core.swing;

import javax.swing.JPanel;
import java.awt.Graphics;

/**
 * @author Shao Yongqing
 * Date: 2017/8/16.
 */
public abstract class Display extends JPanel implements cc.emulator.core.computer.Display {

    /**
     * (non-Javadoc)
     *
     * @see javax.swing.JComponent#paintComponent(Graphics)
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        final int curAttr = getCursorAttribute();   //  crtc.getRegister(0xa) >> 4;
        final int curLoc = getCursorLocation();     //  crtc.getRegister(0xf) | crtc.getRegister(0xe) << 8;
        int memory[] = getMemoryBase();             //  cpu.getMemoryManager().getMemoryBase();
        int screenColumn = getScreenColumn();
        int screenRow = getScreenRow();
        int videoBaseAddr = getVideoBase();

        for (int y = 0; y < screenRow; ++y)
            for (int x = 0; x < screenColumn; ++x) {
                final int character = memory[videoBaseAddr + 2 * (x + y * screenColumn)];       // cpu.memory[0xb8000 + 2 * (x + y * 80)];
                final int attribute = memory[videoBaseAddr + 2 * (x + y * screenColumn) + 1];   //  cpu.memory[0xb8000 + 2 * (x + y * 80) + 1];
                drawChar(g, x,y,character, attribute);
//                // Draw background first.
//                g.setColor(colors[attribute >>> 4 & 0b111]);
//                g.fillRect(x * 7, y * 12, 7, 12);
//                // Then write foreground.
//                g.setColor(colors[attribute & 0b1111]);
//
                // Draw blinking cursor
                drawCursor(g, curLoc,curAttr, x,y,character, attribute);
//                if (x + y * screenColumn == curLoc && (curAttr & 0b1) == 0b0
//                        && System.currentTimeMillis() % 1000 < 500)
//                    g.drawString("_", x * 7, y * 12 + 9);
//                else
//                    g.drawString("" + mapping[character], x * 7, y * 12 + 9);
            }
    }


    public void setScreenColumn(int screenColumn) {
        this.screenColumn = screenColumn;
    }

    public void setScreenRow(int screenRow) {
        this.screenRow = screenRow;
    }

    public void setVideoBase(int videoBase) {
        this.videoBase = videoBase;
    }

    public void setFontInfo(FontInfo fontInfo) {
        this.fontInfo = fontInfo;
    }

    int screenColumn = 80;
    @Override
    public int getScreenColumn(){
        return screenColumn;
    }

    int screenRow = 25;
    @Override
    public int getScreenRow(){
        return screenRow;
    }

    int videoBase = 0xb8000;
    public int getVideoBase(){
        return videoBase;
    }

    FontInfo fontInfo = new FontInfo(7, 12, "My");
    public int getFontWidth(){
        return fontInfo.width;
    }
    public int getFontHeight(){
        return fontInfo.height;
    }

    protected abstract void drawCursor(Graphics g, int curLoc, int curAttr, int x, int y, int character, int attribute);

    protected abstract void drawChar(Graphics g, int x, int y, int character, int attribute);
}

class FontInfo {
    int width;
    int height;
    String name;

    public FontInfo(int w, int h, String name) {
        this.width = w;
        this.height = h;
        this.name = name;
    }
}
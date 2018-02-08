package cc.emulator.core.computer.swing;

import cc.emulator.core.FontInfo;
import cc.emulator.core.computer.VideoAdapter;

import javax.swing.JPanel;
import java.awt.Graphics;

/**
 * @author Shao Yongqing
 * Date: 2017/8/16.
 */
public abstract class Display extends JPanel implements cc.emulator.core.computer.Display {
    protected VideoAdapter videoAdapter;
    public Display(VideoAdapter videoAdapter) {
        this.videoAdapter = videoAdapter;
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.swing.JComponent#paintComponent(Graphics)
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        final int curAttr = getCursorAttribute();   //  videoAdapter.getRegister(0xa) >> 4;
        final int curLoc = getCursorLocation();     //  videoAdapter.getRegister(0xf) | videoAdapter.getRegister(0xe) << 8;
        int screenColumn = getScreenColumn();
        int screenRow = getScreenRow();

        for (int y = 0; y < screenRow; ++y)
            for (int x = 0; x < screenColumn; ++x) {
                final int character = videoAdapter.getCharacter(y,x);
                        // memory[videoBaseAddr + 2 * (x + y * screenColumn)];       // cpu.memory[0xb8000 + 2 * (x + y * 80)];
                final int attribute = videoAdapter.getAttribute(y,x);
                        // memory[videoBaseAddr + 2 * (x + y * screenColumn) + 1];   //  cpu.memory[0xb8000 + 2 * (x + y * 80) + 1];
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


    public void setFontInfo(FontInfo fontInfo) {
        this.fontInfo = fontInfo;
    }


    public FontInfo getFontInfo() {
        return fontInfo;
    }


    FontInfo fontInfo = new FontInfo(7, 12, "My");
    public int getFontWidth(){
        return fontInfo.getWidth();
    }
    public int getFontHeight(){
        return fontInfo.getHeight();
    }

    protected abstract void drawCursor(Graphics g, int curLoc, int curAttr, int x, int y, int character, int attribute);

    protected abstract void drawChar(Graphics g, int x, int y, int character, int attribute);
}


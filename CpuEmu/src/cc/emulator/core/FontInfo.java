package cc.emulator.core;

/**
 * @author Shao Yongqing
 * Date: 2017/8/16.
 */
public class FontInfo {
    int width;
    int height;
    String name;

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }



    public FontInfo(int w, int h, String name) {
        this.width = w;
        this.height = h;
        this.name = name;
    }
}
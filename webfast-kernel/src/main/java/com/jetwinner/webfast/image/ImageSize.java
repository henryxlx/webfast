package com.jetwinner.webfast.image;

/**
 *
 * @author x230-think-joomla
 * @date 2015/6/22
 */
public class ImageSize {

    private Integer width;

    private Integer height;

    public ImageSize() {
        this.width = 0;
        this.height = 0;
    }

    public ImageSize(Integer width, Integer height) {
        this.width = width;
        this.height = height;
    }

    public void resize(Integer width, Integer height) {
        this.width = width;
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}

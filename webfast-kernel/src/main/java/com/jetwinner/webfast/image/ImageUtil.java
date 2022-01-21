package com.jetwinner.webfast.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @author x230-think-joomla
 * @date 2015/6/22
 */
public abstract class ImageUtil {

    public static final ImageSize getNaturalSize(File file) throws Exception {
        int width = 0, height = 0;
        Image myImage = ImageIO.read(file);
        height = myImage.getHeight(null);
        width = myImage.getWidth(null);
        return new ImageSize(width, height);
    }

    public static final ImageSize resizeByScale(int target, ImageSize imageSize) {
        return resizeByScale(target, imageSize.getWidth(), imageSize.getHeight());
    }

    public static final ImageSize resizeByScale(int target, int width, int height) {
        // takes the larger size of the width and height and applies the
        // formula accordingly...this is so this script will work
        // dynamically with any size image

        double percentage = 1;
        if (width > target || height > target) { // 大于目标像素的图像进行缩略显示
            if (width > height) {
                percentage = (double) target / width;
            } else {
                percentage = (double) target / height;
            }
        }

        // gets the new value and applies the percentage, then rounds the value
        int w = (int) (width * percentage);
        int h = (int) (height * percentage);
        return new ImageSize(w, h);
    }

    /**
     * 根据要求的坐标截取图片
     *
     * @param sourceFullPath
     * @param imageFullPath
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public static void cropPartImage(String sourceFullPath, String imageFullPath, String imageExtName, int x, int y, int width, int height)
            throws RuntimeException {

        Image img = null;
        ImageFilter cropFilter = null;
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(new File(sourceFullPath));
            if (bi == null) {
                throw new RuntimeException("ImageIO.read return null");
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("read image fail, src=", sourceFullPath));
        }
        int srcW = bi.getWidth();
        int srcH = bi.getHeight();
        if (srcW <= 0 || srcH <= 0) {
            throw new RuntimeException(String.format("invalid image, src=", sourceFullPath));
        }
        // 异常的图片参数
        if (x >= srcW || y >= srcH) {
            throw new RuntimeException(
                    String.format("cropPart fail, point (x=%s,y=%s) not in the image(width=%s,height=%s)",
                            x, y, srcW, srcH));
        }
        width = Math.min(width, srcW - x);
        height = Math.min(height, srcH - y);
        try {
            Image image = bi.getScaledInstance(srcW, srcH, Image.SCALE_DEFAULT);
            cropFilter = new CropImageFilter(x, y, width, height);
            img = Toolkit.getDefaultToolkit().createImage(
                    new FilteredImageSource(image.getSource(), cropFilter));
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) tag.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(img, 0, 0, null);
            g.dispose();
            ImageIO.write(tag, "JPEG", new File(imageFullPath));
        } catch (Exception e) {
            throw new RuntimeException("process image error, src=" + imageFullPath, e);
        }
    }

    public static void resizeImage(String sourceFullPath, String imageFullPath,
                                   int newWidth, float quality) throws Exception {

        File originalFile = new File(sourceFullPath);
        File resizedFile = new File(imageFullPath);

        if (quality > 1) {
            throw new IllegalArgumentException("Quality has to be between 0 and 1");
        }

        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
        Image i = ii.getImage();
        Image resizedImage = null;

        int iWidth = i.getWidth(null);
        int iHeight = i.getHeight(null);

        if (iWidth > iHeight) {
            resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight)
                    / iWidth, Image.SCALE_SMOOTH);
        } else {
            resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight,
                    newWidth, Image.SCALE_SMOOTH);
        }

        // This code ensures that all the pixels in the image are loaded.
        Image temp = new ImageIcon(resizedImage).getImage();

        // Create the buffered image.
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),
                temp.getHeight(null), BufferedImage.TYPE_INT_RGB);

        // Copy image to buffered image.
        Graphics g = bufferedImage.createGraphics();

        // Clear background and paint the image.
        g.setColor(Color.white);
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
        g.drawImage(temp, 0, 0, null);
        g.dispose();

        // Soften.
        float softenFactor = 0.05f;
        float[] softenArray = {0, softenFactor, 0, softenFactor,
                1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0};
        Kernel kernel = new Kernel(3, 3, softenArray);
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        bufferedImage = cOp.filter(bufferedImage, null);

        // Write the jpeg to a file.
        FileOutputStream out = new FileOutputStream(resizedFile);

        // Encodes image as a JPEG data stream
        ImageIO.write(bufferedImage, "jpg", out);
    }

    /**
     * 将imageFullPath指定的图片进行等比缩放，最长的边为<t>maxEdgeLength</t>
     *
     * @param imageFullPath 缩放后的图片保存的绝对路径
     * @param maxEdgeLength 边长
     * @return
     */
    public static boolean resizeImage2(String sourceFullPath, String imageFullPath, String imageExtName, int maxEdgeLength)
            throws Exception {
        File file = new File(sourceFullPath);
        if (!file.exists()) {
            return false;
        }
        Image img = ImageIO.read(file);
        // 判断图片格式是否正确
        if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
            return false;
        }
        int width = img.getWidth(null);
        int height = img.getHeight(null);

        boolean isWidthLonger = width > height ? true : false;

        // 得到调整后的尺寸及缩小的比例,如果{width,height}都小于等于maxEdgeLength，使用原图像大小
        if (width > maxEdgeLength || height > maxEdgeLength) {
            double ratio;

            if (isWidthLonger) {
                ratio = ((double) maxEdgeLength) / width;
                width = maxEdgeLength;
                height = ((Double) Math.floor(ratio * height)).intValue();
            } else {
                ratio = ((double) maxEdgeLength) / height;
                width = ((Double) Math.floor(ratio * width)).intValue();
                height = maxEdgeLength;
            }
        } else {
            // newWidth = image.getWidth(null);
            // newHeight = image.getHeight(null);
        }
        FileOutputStream out = null;
        try {
            BufferedImage tag = new BufferedImage((int) width, (int) height,
                    BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(
                    img.getScaledInstance(width, height, Image.SCALE_SMOOTH),
                    0, 0, null);
            out = new FileOutputStream(imageFullPath);
            ImageIO.write(tag, "JPEG", out);
        } catch (Exception e) {
            throw new RuntimeException("resize image error, img=" + imageFullPath, e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return true;
    }
}

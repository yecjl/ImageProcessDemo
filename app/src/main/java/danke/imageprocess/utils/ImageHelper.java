package danke.imageprocess.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * 功能：图像处理的工具类
 * Created by danke on 2017/2/9.
 */

public class ImageHelper {

    /**
     * 处理色调，饱和度，亮度的方法
     *
     * @param bm
     * @param hue        色调
     * @param saturation 饱和度
     * @param lum        亮度
     * @return
     */
    public static Bitmap handleImageEffect(Bitmap bm, float hue, float saturation, float lum) {
        // 根据原始图创建一个新的图, 32位ARGB位图
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp); // 根据bitmap创建画布
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG); // 消除锯齿

        ColorMatrix hueMatrix = new ColorMatrix(); // 设置色相
        hueMatrix.setRotate(0, hue); // R
        hueMatrix.setRotate(1, hue); // G
        hueMatrix.setRotate(2, hue); // B

        ColorMatrix saturationMatrix = new ColorMatrix(); // 设置饱和度
        saturationMatrix.setSaturation(saturation);

        ColorMatrix lumMatrix = new ColorMatrix(); // 设置亮度
        lumMatrix.setScale(lum, lum, lum, 1); // 1：不透明

        ColorMatrix imageMatrix = new ColorMatrix(); // 融合上面的处理
        imageMatrix.postConcat(hueMatrix);
        imageMatrix.postConcat(saturationMatrix);
        imageMatrix.postConcat(lumMatrix);

        // 将ColorMatrix设置给画笔
        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
        // 用画笔绘制到画布上(将原图画在画布上)
        canvas.drawBitmap(bm, 0, 0, paint);
        return bmp;
    }

    /**
     * 处理颜色矩阵
     *
     * @param bm
     * @param src
     * @return
     */
    public static Bitmap handleImageColorMatrix(Bitmap bm, float[] src) {
        Bitmap bitmap = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // 设置颜色矩阵
        ColorMatrix colorMatrix = new ColorMatrix(src);
        // 颜色滤镜，将颜色矩阵应用于图片
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

        canvas.drawBitmap(bm, 0, 0, paint);
        return bitmap;
    }

    /**
     * 底片效果
     *
     * @param bm
     * @return
     */
    public static Bitmap handleImagePixelsNegative(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        int color, r, g, b, a; // 记录当前的像素点和各个rgba
        int[] pixels = new int[width * height];
        // 获取像素矩阵
        bm.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < pixels.length; i++) {
            color = pixels[i];

            // 从像素点中分离出r,g,b,a
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            a = Color.alpha(color);

            // 底片效果 设置的r,g,b
            r = makeCurrentRangeRGB(255 - r);
            g = makeCurrentRangeRGB(255 - g);
            b = makeCurrentRangeRGB(255 - b);

            // 将新的r,g,b,a合成像素点
            pixels[i] = Color.argb(a, r, g, b);
        }

        // 设置像素矩阵，此时已经更改了bitmap
        bmp.setPixels(pixels, 0, width, 0, 0, width, height);

        return bmp;
    }

    /**
     * 处理老照片 怀旧
     *
     * @param bm
     * @return
     */
    public static Bitmap handleImagePixelsOldPhoto(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        int color, r, g, b, a;
        int[] pixels = new int[width * height];
        bm.getPixels(pixels, 0, width, 0, 0, width, height);

        for (int i = 0; i < pixels.length; i++) {
            color = pixels[i];
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            a = Color.alpha(color);

            r = makeCurrentRangeRGB((int) (0.393 * r + 0.769 * g + 0.189 * b));
            g = makeCurrentRangeRGB((int) (0.349 * r + 0.686 * g + 0.168 * b));
            b = makeCurrentRangeRGB((int) (0.272 * r + 0.534 * g + 0.131 * b));

            pixels[i] = Color.argb(a, r, g, b);
        }

        bmp.setPixels(pixels, 0, width, 0, 0, width, height);

        return bmp;
    }

    /**
     * 处理浮雕效果
     *
     * @param bm
     * @return
     */
    public static Bitmap handleImagePixelsRelief(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        int color, r, g, b, a;
        int[] pixels = new int[width * height];
        // 因为每次计算是时候会采用两个像素点，所以原来的像素集合不能改变，否则会计算错误
        int[] newPx = new int[width * height];
        bm.getPixels(pixels, 0, width, 0, 0, width, height);

        // 因为要获取上一个的像素点，所以这里的index要从1开始
        for (int i = 1; i < pixels.length; i++) {
            // 获取前一个的color,r,g,b
            int colorPre = pixels[i - 1];
            r = Color.red(colorPre);
            g = Color.green(colorPre);
            b = Color.blue(colorPre);
            a = Color.alpha(colorPre);

            // 获取当前的color,r,g,b
            color = pixels[i];
            int r1 = Color.red(color);
            int g1 = Color.green(color);
            int b1 = Color.blue(color);

            // 计算新的r,g,b
            r = makeCurrentRangeRGB(r - r1 + 127);
            g = makeCurrentRangeRGB(g - g1 + 127);
            b = makeCurrentRangeRGB(b - b1 + 127);

            // 生成新的像素点
            newPx[i] = Color.argb(a, r, g, b);
        }

        bmp.setPixels(newPx, 0, width, 0, 0, width, height);

        return bmp;
    }

    /**
     * 判断r, g, b 值的正确范围
     *
     * @param rgb
     * @return
     */
    public static int makeCurrentRangeRGB(int rgb) {
        if (rgb > 255) {
            return 255;
        } else if (rgb < 0) {
            return 0;
        }
        return rgb;
    }
}

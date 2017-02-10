package danke.mylibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.widget.ImageView;

/**
 * 功能：图片工具类
 * Created by danke on 2017/2/7.
 */

public class BitmapUtil {

    /**
     * 从文件中加载bitmap
     *
     * @param url
     * @param matrix 对图像的操作
     * @return
     */
    public static Bitmap decodeFile(String url, Matrix matrix) {
        // 从文件中加载图片
        Bitmap bitmap = BitmapFactory.decodeFile(url);
        if (matrix != null) {
            // 根据 matrix 对图片进行操作
            Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            // 释放之前的图片资源
            bitmap.recycle();
            return newBitmap;
        } else {
            return bitmap;
        }
    }

    /**
     * 从文件中加载bitmap
     *
     * @param url
     * @return
     */
    public static Bitmap decodeFile(String url) {
        return decodeFile(url, null);
    }

    /**
     * 从资源文件中获取bitmap
     *
     * @param context
     * @param res
     * @return
     */
    public static Bitmap decodeResource(Context context, int res) {
        return BitmapFactory.decodeResource(context.getResources(), res);
    }

    /**
     * 从像素点中加载bitmap
     *
     * @param width
     * @param height
     * @param pixels
     * @return
     */
    public static Bitmap decodePixels(int width, int height, int[] pixels) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 根据bitmap 获取像素集合
     *
     * @param bitmap
     * @return
     */
    public static int[] getPixels(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        return pixels;
    }

    /**
     * 将文件中加载的bitmap，显示到ImageView
     *
     * @param url
     * @param matrix
     * @param imageView
     */
    public static Bitmap decodeFileShow(String url, Matrix matrix, ImageView imageView) {
        Bitmap bitmap = decodeFile(url, matrix);
        show(bitmap, imageView);
        return bitmap;
    }

    /**
     * 将文件中加载的bitmap，显示到ImageView
     *
     * @param url
     * @param imageView
     */
    public static Bitmap decodeFileShow(String url, ImageView imageView) {
        Bitmap bitmap = decodeFile(url);
        show(bitmap, imageView);
        return bitmap;
    }

    /**
     * 从资源文件中获取bitmap，显示到ImageView
     *
     * @param context
     * @param res
     * @param imageView
     */
    public static Bitmap decodeResourceShow(Context context, int res, ImageView imageView) {
        Bitmap bitmap = decodeResource(context, res);
        show(bitmap, imageView);
        return bitmap;
    }

    /**
     * 将bitmap显示到ImageView
     *
     * @param bitmap
     * @param imageView
     */
    public static void show(Bitmap bitmap, ImageView imageView) {
        if (bitmap != null && imageView != null) {
            imageView.setImageBitmap(bitmap);
        }
    }
}

package danke.imageprocess;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import danke.imageprocess.utils.ImageHelper;
import danke.mylibrary.utils.BitmapUtil;

/**
 * 功能：颜色矩阵
 * Created by danke on 2017/2/10.
 */

public class ColorMatrixActivity extends Activity {
    @Bind(R.id.iv_bitmap)
    ImageView ivBitmap;
    @Bind(R.id.gl_content)
    GridLayout glContent;
    @Bind(R.id.bt_set)
    Button btSet;
    @Bind(R.id.bt_reset)
    Button btReset;

    private static final int ETCOUNT = 20;
    private EditText[] etArray = new EditText[ETCOUNT];
    private float[] colorMatrixDatas = new float[ETCOUNT];
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colormatrix_activity);
        ButterKnife.bind(this);

        mBitmap = BitmapUtil.decodeResourceShow(this, R.mipmap.img01, ivBitmap);

        // 当控件加载完会调用
        glContent.post(new Runnable() {
            @Override
            public void run() {
                int width = glContent.getWidth() / 5;
                int height = glContent.getHeight() / 4;
                initEditTexts(width, height);
                initColorMatrixData();
            }
        });
    }

    /**
     * 初始化EditText控件
     *
     * @param width
     * @param height
     */
    public void initEditTexts(int width, int height) {
        for (int i = 0; i < ETCOUNT; i++) {
            EditText editText = new EditText(this);
            // 将EditText保存到数组中，方便控制数据
            etArray[i] = editText;
            glContent.addView(editText, width, height);
        }
    }

    /**
     * 初始化ColorMatrix 矩阵数据
     */
    public void initColorMatrixData() {
        for (int i = 0; i < ETCOUNT; i++) {
            // 0, 6, 12, 18 置为1，其他为0， 因为这样的矩阵，可以保证图像还是原来的[RGBA]
            if (i % 6 == 0) {
                colorMatrixDatas[i] = 1;
            } else {
                colorMatrixDatas[i] = 0;
            }

            etArray[i].setText(String.valueOf(colorMatrixDatas[i]));
        }
    }

    /**
     * 从EditText中获取对应的颜色矩阵
     */
    public void getColorMatrixDatas() {
        for (int i = 0; i < ETCOUNT; i++) {
            colorMatrixDatas[i] = Float.parseFloat(etArray[i].getText().toString());
        }
    }

    @OnClick(R.id.bt_set)
    public void setColorMatrix(View view) {
        getColorMatrixDatas();
        BitmapUtil.show(ImageHelper.handleImageColorMatrix(mBitmap, colorMatrixDatas), ivBitmap);
    }

    @OnClick(R.id.bt_reset)
    public void resetColorMatrix(View view) {
        initColorMatrixData();
        BitmapUtil.show(ImageHelper.handleImageColorMatrix(mBitmap, colorMatrixDatas), ivBitmap);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, ColorMatrixActivity.class));
    }
}

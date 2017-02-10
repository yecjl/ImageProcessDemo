package danke.imageprocess;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import danke.imageprocess.utils.ImageHelper;
import danke.mylibrary.utils.BitmapUtil;

/**
 * 功能：色调，饱和度，亮度
 * Created by danke on 2017/2/9.
 */

public class PrimaryActivity extends Activity implements SeekBar.OnSeekBarChangeListener {
    @Bind(R.id.iv_bitmap)
    ImageView ivBitmap;
    @Bind(R.id.sb_hue)
    SeekBar sbHue;
    @Bind(R.id.ll_hue)
    LinearLayout llHue;
    @Bind(R.id.sb_saturation)
    SeekBar sbSaturation;
    @Bind(R.id.ll_saturation)
    LinearLayout llSaturation;
    @Bind(R.id.sb_lum)
    SeekBar sbLum;
    @Bind(R.id.ll_lum)
    LinearLayout llLum;

    private float mHue, mSaturation, mLum;
    private static final int MAX_VALUE = 255;
    private static final int MID_VALUE = 127;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primary_activity);
        ButterKnife.bind(this);
        // 加载图片
        mBitmap = BitmapUtil.decodeResource(this, R.mipmap.img01);

        sbHue.setOnSeekBarChangeListener(this);
        sbSaturation.setOnSeekBarChangeListener(this);
        sbLum.setOnSeekBarChangeListener(this);

        // 设置最大值
        sbHue.setMax(MAX_VALUE);
        sbSaturation.setMax(MAX_VALUE);
        sbLum.setMax(MAX_VALUE);

        // 初始化seekbar当前位置为中间
        sbHue.setProgress(MID_VALUE);
        sbSaturation.setProgress(MID_VALUE);
        sbLum.setProgress(MID_VALUE);

        BitmapUtil.show(mBitmap, ivBitmap);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sb_hue:
                mHue = (progress - MID_VALUE) * 1.0f / MID_VALUE * 180;
                break;
            case R.id.sb_saturation:
                mSaturation = progress * 1.0f / MID_VALUE;
                break;
            case R.id.sb_lum:
                mLum = progress * 1.0f / MID_VALUE;
                break;
        }

        BitmapUtil.show(ImageHelper.handleImageEffect(mBitmap, mHue, mSaturation, mLum), ivBitmap);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, PrimaryActivity.class));
    }
}

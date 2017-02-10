package danke.imageprocess;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import danke.imageprocess.utils.ImageHelper;
import danke.mylibrary.utils.BitmapUtil;

/**
 * 功能：处理像素点
 * Created by danke on 2017/2/10.
 */

public class PixelHandleActivity extends Activity {
    @Bind(R.id.imageView1)
    ImageView imageView1;
    @Bind(R.id.imageView2)
    ImageView imageView2;
    @Bind(R.id.imageView3)
    ImageView imageView3;
    @Bind(R.id.imageView4)
    ImageView imageView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pixelhandle_activity);
        ButterKnife.bind(this);

        Bitmap bitmap = BitmapUtil.decodeResourceShow(this, R.mipmap.img01, imageView1);
        BitmapUtil.show(ImageHelper.handleImagePixelsNegative(bitmap), imageView2);
        BitmapUtil.show(ImageHelper.handleImagePixelsOldPhoto(bitmap), imageView3);
        BitmapUtil.show(ImageHelper.handleImagePixelsRelief(bitmap), imageView4);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, PixelHandleActivity.class));
    }
}

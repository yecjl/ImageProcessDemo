package danke.imageprocess;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Bind(R.id.bt_primaryHandle)
    Button btPrimaryHandle;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    @Bind(R.id.bt_colorMatrix)
    Button btColorMatrix;
    @Bind(R.id.bt_pixelHandle)
    Button pixelHandle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_primaryHandle)
    public void primaryHandle(View view) {
        PrimaryActivity.start(this);
    }

    @OnClick(R.id.bt_colorMatrix)
    public void colorMatrix(View view) {
        ColorMatrixActivity.start(this);
    }

    @OnClick(R.id.bt_pixelHandle)
    public void pixelHandle(View view) {
        PixelHandleActivity.start(this);
    }
}

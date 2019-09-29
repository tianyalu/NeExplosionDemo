package explosiondemo.ne.sty.com.neexplosiondemo.explosion;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import java.util.Random;

/**
 * Created by tian on 2019/9/27.
 */

public class Utils {
    public static final Random RANDOM = new Random(System.currentTimeMillis());
    private static final Canvas CANVAS = new Canvas();
    private static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;

    public static int dp2Px(int dp) {
        return Math.round(dp * DENSITY);
    }

    public static Bitmap createBitmapFromView(View view) {
        view.clearFocus(); //使view失去焦点恢复原本样式
        Bitmap bitmap = createBitmapSafely(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888, 1);
        if (bitmap != null) {
            synchronized(CANVAS) {
                CANVAS.setBitmap(bitmap);
                view.draw(CANVAS);
                CANVAS.setBitmap(null);
            }
        }
        return bitmap;
    }

    /**
     *
     * @param width
     * @param height
     * @param config
     * @param retryCount 重试次数，发生内存溢出时回收重试
     * @return
     */
    public static Bitmap createBitmapSafely(int width, int height, Bitmap.Config config, int retryCount) {
        try {
            return Bitmap.createBitmap(width, height, config);
        }catch (OutOfMemoryError e) {
            e.printStackTrace();
            if(retryCount > 0) {
                System.gc();
                return createBitmapSafely(width, height, config, retryCount - 1);
            }
            return null;
        }
    }
}

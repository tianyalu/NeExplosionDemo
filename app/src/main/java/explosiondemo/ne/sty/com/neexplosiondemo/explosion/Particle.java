package explosiondemo.ne.sty.com.neexplosiondemo.explosion;

/**
 * Created by tian on 2019/9/27.
 */

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 粒子类
 */
public abstract class Particle {
    // 属性
    protected float cx;
    protected float cy;
    protected int color;

    public Particle(float x, float y, int color) {
        cx = x;
        cy = y;
        this.color = color;
    }

    // 计算
    protected abstract void calculate(float factor);

    // 绘制
    protected abstract void draw(Canvas canvas, Paint paint);

    // 逐帧绘制
    public void advance(Canvas canvas, Paint paint, float factor) {
        calculate(factor);
        draw(canvas, paint);
    }
}

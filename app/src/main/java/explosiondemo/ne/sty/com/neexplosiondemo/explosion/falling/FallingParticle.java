package explosiondemo.ne.sty.com.neexplosiondemo.explosion.falling;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import explosiondemo.ne.sty.com.neexplosiondemo.explosion.Particle;
import explosiondemo.ne.sty.com.neexplosiondemo.explosion.falling.FallingParticleFactory;

import static explosiondemo.ne.sty.com.neexplosiondemo.explosion.Utils.RANDOM;

/**
 * Created by tian on 2019/9/27.
 */

public class FallingParticle extends Particle {
    private float radius = FallingParticleFactory.PART_WH;
    private float alpha = 1.0f; //不透明度：0完全透明 1完全不透明
    private Rect mBound;

    public FallingParticle( int color, float x, float y, Rect bound) {
        super(x, y, color);
        mBound = bound;
    }

    /**
     * @param factor 动画完成百分比
     */
    @Override
    protected void calculate(float factor) {
        cx = cx + factor * RANDOM.nextInt(mBound.width()) * (RANDOM.nextFloat() - 0.5f);
        cy = cy + factor * RANDOM.nextInt(mBound.height() / 2);

        radius = radius - factor * RANDOM.nextInt(2);
        alpha = (1f - factor) * (1 + RANDOM.nextFloat());
    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setAlpha((int) (Color.alpha(color) * alpha)); //这样透明颜色就不是黑色了
        canvas.drawCircle(cx, cy, radius, paint);
    }
}

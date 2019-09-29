package explosiondemo.ne.sty.com.neexplosiondemo.explosion.upfalling;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import explosiondemo.ne.sty.com.neexplosiondemo.explosion.Particle;

/**
 * Created by tian on 2019/9/29.
 */

public class ExplodeParticle extends Particle {
    float radius = ExplodeParticleFactory.PART_WH;
    float alpha;
    float cx;
    float cy;
    float baseCx;
    float baseCy;
    float baseRadius;
    float top;
    float bottom;
    float mag;
    float neg;
    float life;
    float overflow;

    public ExplodeParticle(float x, float y, int color) {
        super(x, y, color);
    }

    /**
     *
     * @param factor 动画执行进度百分比
     */
    @Override
    protected void calculate(float factor) {
        float f = 0f;
        float normalization = factor / ExplodeParticleFactory.END_VALUE;
        if(normalization < life || normalization > 1f - overflow) {
            alpha = 0f;
            return;
        }
        normalization = (normalization - life) / (1f - life - overflow);
        float f2 = normalization * ExplodeParticleFactory.END_VALUE;
        if(normalization >= 0.7f) {
            f = (normalization - 0.7f) / 0.3f;
        }
        alpha = 1f - f;
        f = bottom * f2;
        cx = baseCx + f;
        cy = (float) (baseCy - this.neg * Math.pow(f, 2.0)) - f * mag;
        radius = ExplodeParticleFactory.V + (baseRadius - ExplodeParticleFactory.V) * f2;
    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setAlpha((int) (Color.alpha(color) * alpha)); // 这样透明颜色就不是黑色了
        canvas.drawCircle(cx, cy, radius, paint);
    }


}

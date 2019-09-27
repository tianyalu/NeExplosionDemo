package explosiondemo.ne.sty.com.neexplosiondemo.explosion;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by tian on 2019/9/27.
 */

/**
 * 粒子动画控制类（控制动画进度）
 */
public class ExplosionAnimator extends ValueAnimator {
    public static int default_duration = 1500;
    // 粒子
    private Particle[][] mParticles;
    // 粒子工厂
    private ParticleFactory mParticleFactory;
    // 动画场地
    private View mContainer;
    private Paint mPaint;

    public ExplosionAnimator(View view, Bitmap bitmap, Rect bound, ParticleFactory particleFactory) {
        mContainer = view;
        mParticleFactory = particleFactory;
        mPaint = new Paint();
        setFloatValues(0.0f, 1.0f);
        setDuration(default_duration);
        mParticles = this.mParticleFactory.generateParticles(bitmap, bound);
    }

    // 绘制
    public void draw(Canvas canvas) {
        if(!isStarted()) {
            // 动画绘制关闭
            return;
        }
        // 所有粒子开始运动
        for (Particle[] mParticle : mParticles) {
            for (Particle particle : mParticle) {
                particle.advance(canvas, mPaint, (Float) getAnimatedValue());
            }
        }
        mContainer.invalidate();
    }

    @Override
    public void start() {
        super.start();
        mContainer.invalidate();
    }
}

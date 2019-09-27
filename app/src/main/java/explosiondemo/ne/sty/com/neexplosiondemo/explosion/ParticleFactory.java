package explosiondemo.ne.sty.com.neexplosiondemo.explosion;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Created by tian on 2019/9/27.
 */

/**
 * 粒子工厂
 */
public abstract class ParticleFactory {
    public abstract Particle[][] generateParticles(Bitmap bitmap, Rect bound);
}

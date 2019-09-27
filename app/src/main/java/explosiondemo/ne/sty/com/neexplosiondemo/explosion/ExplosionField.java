package explosiondemo.ne.sty.com.neexplosiondemo.explosion;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import static explosiondemo.ne.sty.com.neexplosiondemo.explosion.Utils.RANDOM;

/**
 * 动画场地
 * Created by tian on 2019/9/27.
 */
public class ExplosionField extends View {
    // 同时执行多个动画
    private ArrayList<ExplosionAnimator> explosionAnimators;
    private HashMap<View, ExplosionAnimator> explosionAnimatorHashMap;
    // 粒子工厂（生产不同的特效）
    private ParticleFactory mParticleFactory;
    // 需要实现爆炸效果的控件
    private OnClickListener onClickListener;

    public ExplosionField(Context context, ParticleFactory particleFactory) {
        super(context);
        init(particleFactory);
    }

    private void init(ParticleFactory particleFactory) {
        this.explosionAnimators = new ArrayList<>();
        this.explosionAnimatorHashMap = new HashMap<>();
        this.mParticleFactory = particleFactory;
        //添加动画区域到Activity
        attach2Activity();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(ExplosionAnimator animator : explosionAnimators) {
            animator.draw(canvas);
        }
    }

    /**
     * 给Activity加上全屏覆盖的ExplosionField
     */
    private void attach2Activity() {
        // 找到decorView添加到当前布局
        ViewGroup decorView = (ViewGroup) ((Activity)getContext()).getWindow().getDecorView();

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        decorView.addView(this, lp);
    }

    // 添加监听
    public void addListener(View view) {
        if(view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                addListener(viewGroup.getChildAt(i));
            }
        }else {
            view.setClickable(true);
            view.setOnClickListener(getOnClickListener());
        }
    }

    private OnClickListener getOnClickListener() {
        if(onClickListener == null) {
            onClickListener = new OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 触发爆炸效果
                    explode(view);
                    if(clickCallback != null) {
                        clickCallback.onClick(view);
                    }
                }
            };
        }
        return onClickListener;
    }

    // 爆炸效果
    private void explode(final View view) {
        // 防止重复点击
        if(explosionAnimatorHashMap.get(view) != null && explosionAnimatorHashMap.get(view).isStarted()) {
            return;
        }
        if(view.getVisibility() != View.VISIBLE || view.getAlpha() == 0) {
            return;
        }

        // 先去获取控件的位置
        final Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);  //得到view相当于整个屏幕的坐标
        int contentTop = ((ViewGroup) getParent()).getTop();
        Rect frame = new Rect();
        ((Activity) getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        rect.offset(0, -contentTop - statusBarHeight); //去掉状态栏和标题栏高度
        if(rect.width() == 0 || rect.height() == 0) {
            return;
        }

        // 震动动画
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(150);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setTranslationX((RANDOM.nextFloat() - 0.5f) * view.getWidth() * 0.05f);
                view.setTranslationY((RANDOM.nextFloat() - 0.5f) * view.getHeight() * 0.05f);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                explode(view, rect);
            }
        });
        animator.start();
    }

    private void explode(final View view, Rect rect) {
        final ExplosionAnimator animator = new ExplosionAnimator(this, Utils.createBitmapFromView(view), rect, mParticleFactory);
        explosionAnimators.add(animator);
        explosionAnimatorHashMap.put(view, animator);
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                view.setClickable(false);
                //缩小，透明动画
                view.animate().setDuration(150).scaleX(0f).scaleY(0f).alpha(0f).start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(150).start();
                view.setClickable(true);
                //动画结束时从动画集中移除
                explosionAnimators.remove(animation);
                explosionAnimatorHashMap.remove(view);
                animation = null;
            }
        });

        animator.start();
    }

    private ClickCallback clickCallback;

    public void setClickCallback(ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }
}

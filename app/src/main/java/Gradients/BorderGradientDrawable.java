package Gradients;



import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class BorderGradientDrawable extends Drawable {
    private final Paint paint;
    private final RectF rectF;
    private final int strokeWidth;
    private static final int DURATION = 1500; // Duración de la animación en milisegundos

    public BorderGradientDrawable(Context context, int startColor, int endColor, int strokeWidth) {
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.rectF = new RectF();
        this.strokeWidth = strokeWidth;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        animateBorderColor(context, startColor, endColor, 3000);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        rectF.set(bounds.left + strokeWidth / 2f, bounds.top + strokeWidth / 2f,
                bounds.right - strokeWidth / 2f, bounds.bottom - strokeWidth / 2f);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(rectF, 0, 0, paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return android.graphics.PixelFormat.TRANSLUCENT;
    }

    private void animateBorderColor(Context context, int startColor, int endColor, int duration) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor);
        colorAnimation.setDuration(duration);
        colorAnimation.setRepeatCount(ValueAnimator.INFINITE);
        colorAnimation.setRepeatMode(ValueAnimator.REVERSE);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                paint.setColor((int) animator.getAnimatedValue());
                invalidateSelf();
            }
        });
        colorAnimation.start();
    }
}

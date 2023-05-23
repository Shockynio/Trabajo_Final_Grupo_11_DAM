package Gradients;



import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * Clase Drawable personalizada que representa un borde con efecto de degradado.
 */
public class BorderGradientDrawable extends Drawable {
    private final Paint paint;
    private final RectF rectF;
    private final int strokeWidth;
    private static final int DURATION = 1500; // Duración de la animación en milisegundos

    /**
     * Construye un objeto BorderGradientDrawable con los colores y el ancho del trazo especificados.
     *
     * @param context      El contexto de la aplicación o actividad.
     * @param startColor   El color de inicio del degradado.
     * @param endColor     El color de fin del degradado.
     * @param strokeWidth El ancho del trazo del borde.
     */
    public BorderGradientDrawable(Context context, int startColor, int endColor, int strokeWidth) {
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.rectF = new RectF();
        this.strokeWidth = strokeWidth;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        animateBorderColor(context, startColor, endColor, 3000);
    }

    /**
     * Se invoca cuando cambia el tamaño del límite del Drawable.
     *
     * @param bounds Los límites del Drawable.
     */
    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        rectF.set(bounds.left + strokeWidth / 2f, bounds.top + strokeWidth / 2f,
                bounds.right - strokeWidth / 2f, bounds.bottom - strokeWidth / 2f);
    }

    /**
     * Dibuja el Drawable en el lienzo de dibujo especificado.
     *
     * @param canvas El lienzo en el que se dibuja el Drawable.
     */
    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(rectF, 0, 0, paint);
    }

    /**
     * Establece la opacidad del Drawable.
     *
     * @param alpha El valor de opacidad.
     */
    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    /**
     * Establece el filtro de color del Drawable.
     *
     * @param colorFilter El filtro de color a aplicar.
     */
    @Override
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    /**
     * Devuelve la opacidad del Drawable.
     *
     * @return La opacidad del Drawable.
     */
    @Override
    public int getOpacity() {
        return android.graphics.PixelFormat.TRANSLUCENT;
    }

    /**
     * Realiza una animación del color del borde mediante un efecto de degradado.
     *
     * @param context     El contexto de la aplicación o actividad.
     * @param startColor  El color de inicio del degradado.
     * @param endColor    El color de fin del degradado.
     * @param duration    La duración de la animación en milisegundos.
     */
    private void animateBorderColor(Context context, int startColor, int endColor, int duration) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor);
        colorAnimation.setDuration(duration);
        colorAnimation.setRepeatCount(ValueAnimator.INFINITE);
        colorAnimation.setRepeatMode(ValueAnimator.REVERSE);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /**
             * Método invocado durante la actualización de la animación del color del borde.
             * Establece el color del trazo del borde con el valor animado y actualiza el dibujo del Drawable.
             *
             * @param animator El objeto ValueAnimator que contiene el valor animado en formato entero.
             */
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                paint.setColor((int) animator.getAnimatedValue());
                invalidateSelf();
            }
        });
        colorAnimation.start();
    }
}

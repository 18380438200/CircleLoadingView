package com.example.circleloadingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * create by libo
 * create on 2020/7/22
 * description 圆环进度加载view
 */
public class CircleProgressView extends View {
    /**
     * 背景圆环paint
     */
    private Paint bgPaint;
    /**
     * 进度圆环paint
     */
    private Paint progressPaint, smallCirclePaint;
    private Paint textPaint1, textPaint2;
    /**
     * 背景圆环半径
     */
    private int bgRadus = 60;
    /**
     * 当前进度百分比，总数为100
     */
    private int curPercentProgress = 0;
    /**
     * 文字显示内容
     */
    private String content;
    private String fixText;

    public CircleProgressView(Context context) {
        super(context);
        init();
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setBgPaint();
        setProgressPaint();
        setTextPaint();
    }

    private void setBgPaint() {
        bgPaint = new Paint();
        bgPaint.setColor(getResources().getColor(R.color.white));
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(10);
        bgPaint.setAntiAlias(true);

        smallCirclePaint = new Paint();
        smallCirclePaint.setColor(getResources().getColor(R.color.white));
    }

    private void setProgressPaint() {
        progressPaint = new Paint();
        progressPaint.setColor(getResources().getColor(R.color.orange));
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(14);
        progressPaint.setAntiAlias(true);
    }

    private void setTextPaint() {
        textPaint1 = new Paint();
        textPaint1.setColor(getResources().getColor(R.color.orange));
        textPaint1.setTextSize(50);
        textPaint1.setAntiAlias(true);

        textPaint2 = new Paint();
        textPaint2.setColor(getResources().getColor(R.color.black));
        textPaint2.setTextSize(44);
        textPaint2.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //圆直径
        int bgCircleWidth = dp2px(getContext(), bgRadus * 2);
        //圆与矩形外边距
        int padding = (getWidth() - bgCircleWidth) / 2;

        //居中画背景圆环
        RectF rectF = new RectF(padding, padding, padding + bgCircleWidth, padding + bgCircleWidth);
        canvas.drawArc(rectF, -90, 360, false, bgPaint);

        Rect contentRect = new Rect();
        //画中心文字
        content = "当前" + curPercentProgress * 2 + "M";
        //将文本居中显示，需要获取文本的宽度，再计算左右边距
        //获取文本宽度，高度：  getTextBounds 是将TextView 的文本放入一个Rect矩形中， 测量TextView的高度和宽度  .witdh()  .height()获取
        textPaint1.getTextBounds(content, 0, content.length(), contentRect);
        canvas.drawText(content, (getWidth() - contentRect.width()) / 2, (getHeight() - contentRect.height()) / 2, textPaint1);

        fixText = "垃圾文件";
        textPaint2.getTextBounds(fixText, 0, fixText.length(), contentRect);
        canvas.drawText(fixText, (getWidth() - contentRect.width()) / 2, (getHeight() + contentRect.height() * 2) / 2, textPaint2);

        //画进度圆弧
        /**
         * 参数：（中文）
         * 　　oval - 　　　　用于确定圆弧形状与尺寸的椭圆边界（即椭圆外切矩形）
         * 　　startAngle -    开始角度（以时钟3点的方向为0°，12点钟为-90°，顺时针为正方向）
         * 　　sweepAngle -    扫过角度 圆弧范围为 startAngle-startAngle+sweepAngle，如果sweepAngle为0，则不显示圆弧
         * 　　useCenter -     是否包含圆心，true即为扇形，false为圆弧
         * 　　paint -           绘制圆弧的画笔
         */
        RectF rectFProgress = new RectF(padding, padding, padding + bgCircleWidth, padding + bgCircleWidth);
        canvas.drawArc(rectFProgress, -90, curPercentProgress * 360 / 100, false, progressPaint);

        drawThumb(canvas, bgCircleWidth / 2);

    }

    /**
     * 更新当前进度
     *
     * @param progress 进度值范围 0-100
     */
    public void updateProgress(int progress) {
        if (progress < 0 || progress > 100) {
            return;
        }

        curPercentProgress = progress;
        invalidate();
    }

    private void drawThumb(Canvas canvas, int r) {
        //画终端同心圆
        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;
        float angle = (float) (curPercentProgress * 360 / 100 * Math.PI / 180);  // *Math.PI/180将角度转为弧度，PI为180度

        //需要将圆实时画在圆进度轨迹上，简单地用到了三角函数计算，(centerX,centerY)为原点，r为半径，轨迹方程为x^2+y^2=r^2; x=sin(angle),y=cos(angle)
        float x = (float) (centerX + r * Math.sin(angle));
        float y = (float) (centerY - r * Math.cos(angle));
        canvas.drawCircle(x, y, dp2px(getContext(), 4), progressPaint);

        canvas.drawCircle(x, y, dp2px(getContext(), 3), smallCirclePaint);
    }

    public static int dp2px(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }
}

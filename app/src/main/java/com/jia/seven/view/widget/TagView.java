package com.jia.seven.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.jia.seven.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 2015/11/17.
 */
public class TagView extends TextView {

    private Paint mCirclePaint;

    // 圆与字的间距
    private float circleSpace;

    // 字间距
    private float wordSpace;

    private int mWidth;
    private int mHeight;

    private List<Word> mWords = new ArrayList<>();

    public TagView(Context context) {
        super(context);
//        init(getResources().getColor(R.color.color_item_tag_bg));
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray type = context.obtainStyledAttributes(attrs, R.styleable.TagView);
        init(type.getColor(R.styleable.TagView_circle_color, Color.parseColor("#FFF0F0F0")));
        type.recycle();
    }

    private void init(int color) {
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(color);
    }

    /**
     * 设置圆形大小
     * circleSpace = 圆半径 - 字半径;
     */
    public void setCircleSize(float circleSpace) {
        if (circleSpace > 0) {
            this.circleSpace = circleSpace;
        }
    }

    /**
     * 设置字间距
     *
     * @param charSpace
     */
    public void setWordSpace(float charSpace) {
        if (charSpace > 0) {
            this.wordSpace = charSpace;
        }
    }

    public void setCirlceColor(int color) {
        mCirclePaint.setColor(color);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measure();
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!mWords.isEmpty()) {
            getPaint().setTextAlign(Paint.Align.CENTER); // 文字居中
            getPaint().setColor(getCurrentTextColor());
            for (Word w : mWords) {
                canvas.drawCircle(w.cx, w.cy, w.radius, mCirclePaint);
            }
            for (Word w : mWords) {
                canvas.drawText(w.c, 0, 1, w.tx, w.ty, getPaint());
            }
        }
    }

    private void measure() {

        float right = getPaddingLeft();

        mWords.clear();

        if (!TextUtils.isEmpty(getText())) {

            float charWidth = getPaint().measureText("测");

            if (circleSpace == 0f)
                circleSpace = charWidth / 3;
            if (wordSpace == 0f)
                wordSpace = charWidth / 5;

            // 圆半径
            float circleRadius = charWidth / 2 + circleSpace;
            // 第二个圆开始左移距离
            float circleLeftOffset = -(2 * circleSpace - wordSpace);

            Paint.FontMetricsInt fontMetrics = getPaint().getFontMetricsInt();

            for (int i = 0; i < getText().length(); i++) {
                Word word = new Word();

                // 计算背景圆位置
                if (i == 0) {
                    word.cx = right + circleRadius;
                } else {
                    word.cx = right + circleRadius + circleLeftOffset;
                }

                word.cy = getPaddingTop() + circleRadius;
                word.radius = circleRadius;

                // 计算字位置
                word.tx = word.cx;
//                word.ty = (getPaddingTop() + 2 * circleRadius - fontMetrics.bottom - fontMetrics.top) / 2;

                word.ty = getPaddingTop() + (2 * circleRadius - (fontMetrics.bottom - fontMetrics.top)) / 2 - fontMetrics.top;

                word.c = getText().subSequence(i, i + 1);

                right = word.cx + circleRadius;

                mWords.add(word);
            }

            mWidth = (int) (right + getPaddingRight() + 0.5);
            mHeight = (int) (getPaddingTop() + getPaddingBottom() + circleRadius * 2 + 0.5);

        } else {
            mWidth = 0;
            mHeight = 0;
        }
    }

    class Word {
        float cx;
        float cy;
        float radius;
        float tx;
        float ty;
        CharSequence c;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        requestLayout();
    }

}

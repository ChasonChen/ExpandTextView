package com.chasonc.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by chasonchen on 2018/4/11.
 */

public class ExpandTextView extends TextView {

    private static SpannableString elipseString;//收起的文字
    private static SpannableString notElipseString;//展开的文字

    private String mExpandText;
    private int mExpandTextColor;
    private String mFoldText;
    private int mMaxLines;

    private TextPaint paint;
    private int width;

    public ExpandTextView(Context context) {
        super(context);
        init(context, null);
    }

    public ExpandTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ExpandTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        if (null != attrs) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandTextView);
            mExpandTextColor = typedArray.getColor(R.styleable.ExpandTextView_expandTextColor, Color.RED);
            mExpandText = typedArray.getString(R.styleable.ExpandTextView_expandText);
            mFoldText = typedArray.getString(R.styleable.ExpandTextView_foldText);
            mExpandText = TextUtils.isEmpty(mExpandText) ? "全部" : mExpandText;
            mFoldText = TextUtils.isEmpty(mFoldText) ? "收起" : mFoldText;
            mMaxLines = typedArray.getInt(R.styleable.ExpandTextView_maxLines,3);
        } else {
            mExpandText = "全部";
            mFoldText = "收起";
            mExpandTextColor = Color.RED;
        }
        //获取TextView的画笔对象
        paint = getPaint();
        //每行文本的布局宽度
        width = getResources().getDisplayMetrics().widthPixels - dip2px(getContext(), getPaddingLeft()+getPaddingRight());
    }

    public void setExpandContent(String content) {
        //实例化StaticLayout 传入相应参数
        StaticLayout staticLayout = new StaticLayout(content, paint, width, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
        //判断content是行数是否超过最大限制行数3行
        if (staticLayout.getLineCount() > mMaxLines) {
            //定义展开后的文本内容
            String string1 = content + mFoldText;
            notElipseString = new SpannableString(string1);
            //给收起两个字设成蓝色
            notElipseString.setSpan(new ForegroundColorSpan(Color.BLUE), string1.length() - mFoldText.length(), string1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            //获取到第三行最后一个文字的下标
            int index = staticLayout.getLineStart(mMaxLines) - 1;
            //定义收起后的文本内容
            String substring = content.substring(0, index - mExpandText.length()) + "..." + mExpandText;
            elipseString = new SpannableString(substring);
            //给查看全部设成蓝色
            elipseString.setSpan(new ForegroundColorSpan(mExpandTextColor), substring.length() - mExpandText.length(), substring.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //设置收起后的文本内容
            setText(elipseString);
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.isSelected()) {
                        //如果是收起的状态
                        setText(notElipseString);
                        setSelected(false);
                    } else {
                        //如果是展开的状态
                        setText(elipseString);
                        setSelected(true);
                    }
                }
            });
            setSelected(true);
        } else {
            //没有超过 直接设置文本
            setText(content);
            setOnClickListener(null);
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(Context mContext, float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

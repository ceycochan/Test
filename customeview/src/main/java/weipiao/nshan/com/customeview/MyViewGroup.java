package weipiao.nshan.com.customeview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class MyViewGroup extends ViewGroup {

    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //获取子View中宽度最大的值
    private int getMaxChildWidth() {
        int childCount = getChildCount();
        int maxWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getMeasuredWidth() > maxWidth) {
                maxWidth = childView.getMeasuredWidth();
            }
        }
        return maxWidth;
    }

    //所有子View的高度相加
    private int getTotalHeight() {
        int childCount = getChildCount();
        int height = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            height += childView.getMeasuredHeight();
        }
        return height;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //将所有的子View测量:调用每个子View的onMeasure函数
        //measureChild是对单个view进行测量
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();
        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else {
            //如果宽高是包裹内容
            if (widthMode == MeasureSpec.AT_MOST & heightMode == MeasureSpec.AT_MOST) {
                //将高度设置为所有子View的高度相加,宽度设为子View中最大的宽度
                int height = getTotalHeight();
                int width = getMaxChildWidth();
                setMeasuredDimension(width, height);
            } else if (heightMode == MeasureSpec.AT_MOST) {
                //如果只有高度是包裹内容:宽度设置为ViewGroup自己的测量宽度,高度为子View总和
                setMeasuredDimension(widthSize, getTotalHeight());
            } else if (widthMode == MeasureSpec.AT_MOST) {
                //如果只有宽度是包裹内容:宽度设置为子View综合,高度设置为VG自己的测量宽度
                setMeasuredDimension(getMaxChildWidth(), heightSize);
            }
        }
    }

    //摆放子View
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        //记录当前高度位置
        int currentHeight = t;
        //将子View逐个摆放
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            int height = childView.getMeasuredHeight();
            int width = childView.getMeasuredWidth();
            //摆放子View:参数分别是子View矩形区域的左上右下边
            childView.layout(l, currentHeight, l + width, currentHeight + height);
            currentHeight += height;
        }
    }
}

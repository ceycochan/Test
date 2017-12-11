package weipiao.nshan.com.customedview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/10/20 0020.
 */
public class CharAvatarView extends ImageView {
    //得到【类】的简写名称: CharAvatarView
    private static final String TAG = CharAvatarView.class.getSimpleName();
    //颜色画板集
    private static final int[] colors = {
            0xff1abc9c, 0xff16a085, 0xfff1c40f, 0xfff39c12, 0xff2ecc71,
            0xff27ae60, 0xffe67e22, 0xffd35400, 0xff3498db, 0xff2980b9,
            0xffe74c3c, 0xffc0392b, 0xff9b59b6, 0xff8e44ad, 0xffbdc3c7,
            0xff34495e, 0xff2c3e50, 0xff95a5a6, 0xff7f8c8d, 0xffec87bf,
            0xffd870ad, 0xfff69785, 0xff9ba37e, 0xffb49255, 0xffb49255, 0xffa94136
    };


    private Paint mPaintBackground;

    private Paint mPaintText;

    private Rect mRect;

    private String text;

    private int charHash;
    //程序实例化采用:View被新建
    public CharAvatarView(Context context) {
        this(context, null);
    }
    //Layout实例化: 把XML内的参数通过AttributeSet带入到View内
    public CharAvatarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //主题的Style信息,从XML文件中传入到View内
    public CharAvatarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //Paint.ANTI_ALIAS_FLAG:抗锯齿
        mPaintBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        //Rect主要用来表示坐标中的一块矩形区域
        //Rect rect=new Rect(100,50,300,500);| 左上角(100,50) 右下角(300,500)
        mRect = new Rect();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);//父类规定:宽高相同
    }
    //自定义控件核心代码＠onDraw();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null!=text){
            //取得背景颜色
            int color = colors[charHash % colors.length];
            //画圆
            mPaintBackground.setColor(color);
            canvas.drawCircle(getWidth()/2,getWidth()/2,getWidth()/2,mPaintBackground);
            //写字
            mPaintText.setColor(Color.WHITE);
            mPaintText.setTextSize(getWidth()/2);
            //设置空心线宽
            mPaintText.setStrokeWidth(3);
            mPaintText.getTextBounds(text,0,1,mRect);
            //垂直居中
            /*
            * 1. getMeasuredHeight():测量后的宽高
            * 2.
            * */
            Paint.FontMetricsInt fontMetricsInt = mPaintText.getFontMetricsInt();
            int baseline =(getMeasuredHeight()- fontMetricsInt.bottom-fontMetricsInt.top)/2;
            //左右居中
            mPaintText.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(text,getWidth()/2,baseline,mPaintText);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void setText(String content) {
        if (content==null){
            throw new NullPointerException("字符串不能空");
        }
        //取内容的第一个字符
        this.text = String.valueOf(content.toCharArray()[0]);
        //如果是字母换成大写
        this.text=text.toUpperCase();
        charHash=this.text.hashCode();
        //重绘
        invalidate();
    }
}

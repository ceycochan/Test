package weipiao.nshan.com.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Administrator on 2016/10/24 0024.
 */
public class MyView extends SurfaceView implements SurfaceHolder.Callback {
    //①.写一个类,继承SurfaceView;
    //②.实现SurfaceHolder.Callback接口;
    //③.得到一个SurfaceHolder对象,添加监听holder.addCallback(this);
    //④.一般情况下,可以在surfaceCreate()方法里开启一个绘制视图的线程;
    //⑤.可以surfaceChanged()方法对绘制出来的视图view的宽高等进行二次设置;
    //⑥.一般在surfaceDestroyed()方法销毁释放各种占用的资源.

    private SurfaceHolder mHolder;
    private UpdateViewThread thread;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //得到SurfaceHolder对象
        mHolder = getHolder();
        //添加监听
        mHolder.addCallback(this);
        thread = new UpdateViewThread();
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //创建surface的方法
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        thread.start();
    }

    //surfaceView不停更新界面的方法
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    //销毁surfaceview的方法
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        thread.isStart = false;
    }

    //绘制圆的线程
    class UpdateViewThread extends Thread {

        private Paint mPaint;
        boolean isStart = false;
        private float x = 100;
        private float y = 100;
        private float radius = 10;

        public UpdateViewThread() {
            isStart = true;
            mPaint = new Paint();
            mPaint.setColor(Color.BLUE);
            //画笔的样式
            mPaint.setStyle(Paint.Style.STROKE);
            //边线的宽度
            mPaint.setStrokeWidth(2);
        }

        @Override
        public void run() {
            while (isStart) {
                //锁定画布
                Canvas canvas = mHolder.lockCanvas();

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (radius > 100) {
                    radius = 10;
                }

                //清空掉原有的内容
                canvas.drawColor(Color.BLACK);
                canvas.translate(200,200);

                radius += 10;
                //进行视图的绘制
                canvas.drawCircle(x, y, radius, mPaint);

                //解锁画布
                mHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
}

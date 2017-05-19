package cn.edu.hust.maokelong.ourpets;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FloatWindowMassageView extends LinearLayout {

    /**
     * 记录大悬浮窗的宽度
     */
    public static int viewWidth;
    public static FloatWindowService floatwindowservice;

    public static String content=null;
    public static int flag=1;
    public static int count=0;
    /**
     * 记录大悬浮窗的高度
     */
    public static int viewHeight;

    public FloatWindowMassageView(final Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.float_window_massage, this);
        final View view = findViewById(R.id.Massage_window_layout);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        TextView Massge_View = (TextView) findViewById(R.id.Massge_View);
        Massge_View.getBackground().setAlpha(140);//0~255透明度值
        Massge_View.setText(MyWindowManager.getMassage());

       /* Button weixin = (Button) findViewById(R.id.button);
        weixin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, SettingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                MyWindowManager.removeMassageWindow(getContext());

            }
        });*/




        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 点击窗口外部区域可消除
                // 这点的实现主要将悬浮窗设置为全屏大小，外层有个透明背景，中间一部分视为内容区域
                // 所以点击内容区域外部视为点击悬浮窗外部
                int x = (int) event.getX();
                int y = (int) event.getY();
                Rect rect = new Rect();
                //View work_space=findViewById(R.id.Massage_window_layout_work_space);
                view.getGlobalVisibleRect(rect);
                if (rect.contains(x, y)) {
                    //打开小悬浮窗，同时关闭大悬浮窗
                    MyWindowManager.removeMassageWindow(getContext());
                    flag=1;

                }

                return false;
            }
        });
    }


}
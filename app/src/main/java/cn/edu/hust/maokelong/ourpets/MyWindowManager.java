package cn.edu.hust.maokelong.ourpets;


import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import cn.edu.hust.maokelong.ourpets.R;

public class MyWindowManager {

    private static FloatWindowMassageView MassageWindow;
    public static TextView imageView;
    private  static String scount=null;
    public static int flag1=0;
    /**
     * 大悬浮窗View的实例
     */


    public static String content1=null;




    private static LayoutParams MassageWindowParams;

    /**
     * 用于控制在屏幕上添加或移除悬浮窗
     */

    private static NotimassageService mnotimassageService;


    private static FloatWindowSmallView smallWindow;

    /**
     * 大悬浮窗View的实例
     */
    private static FloatWindowBigView bigWindow;

    /**
     * 小悬浮窗View的参数
     */
    private static LayoutParams smallWindowParams;

    /**
     * 大悬浮窗View的参数
     */
    private static LayoutParams bigWindowParams;

    /**
     * 用于控制在屏幕上添加或移除悬浮窗
     */
    private static WindowManager mWindowManager;

    /**
     * 用于获取手机可用内存
     */
    private static ActivityManager mActivityManager;
    private static SettingActivity settingActivity;
    /**
     * 创建一个小悬浮窗。初始位置为屏幕的右部中间位置。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void createSmallWindow(Context context) {
        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (smallWindow == null) {
            smallWindow = new FloatWindowSmallView(context);
            if (smallWindowParams == null) {
                smallWindowParams = new LayoutParams();
                smallWindowParams.type = LayoutParams.TYPE_PHONE;
                smallWindowParams.format = PixelFormat.RGBA_8888;
                smallWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | LayoutParams.FLAG_NOT_FOCUSABLE;
                smallWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                smallWindowParams.width = FloatWindowSmallView.viewWidth;
                smallWindowParams.height = FloatWindowSmallView.viewHeight;
                smallWindowParams.x = screenWidth;
                smallWindowParams.y = screenHeight / 2;
            }
            smallWindow.setParams(smallWindowParams);
            windowManager.addView(smallWindow, smallWindowParams);
        }
    }

    public static void createMassageWindow(Context context) {


        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (MassageWindow == null) {
            MassageWindow = new FloatWindowMassageView(context);
            if ( MassageWindowParams == null) {
                int flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;






                MassageWindowParams = new LayoutParams();
                MassageWindowParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

                // WindowManager.LayoutParams.TYPE_SYSTEM_ALERT

                // 设置flag


                // | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                // 如果设置了WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，弹出的View收不到Back键的事件
                MassageWindowParams.flags = flags;
                // 不设置这个弹出框的透明遮罩显示为黑色
                MassageWindowParams.format = PixelFormat.TRANSLUCENT;
                // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
                // 设置 FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按
                // 不设置这个flag的话，home页的划屏会有问题

                //MassageWindowParams.width = LayoutParams.MATCH_PARENT;
                // MassageWindowParams.height = LayoutParams.MATCH_PARENT;

                //  MassageWindowParams.gravity = Gravity.CENTER;

                MassageWindowParams.gravity = Gravity.LEFT | Gravity.TOP;



                // MassageWindowParams.type = LayoutParams.TYPE_PHONE;
                // MassageWindowParams.format = PixelFormat.RGBA_8888;
                //  MassageWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
                // | LayoutParams.FLAG_NOT_FOCUSABLE;
                //MassageWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                MassageWindowParams.width = FloatWindowMassageView.viewWidth;
                MassageWindowParams.height = FloatWindowMassageView.viewHeight;
                MassageWindowParams.x = screenWidth;
                MassageWindowParams.y = screenHeight / 2;
            }
            windowManager.addView(MassageWindow, MassageWindowParams);
        }
    }

    public static void removeMassageWindow(Context context) {
        if (MassageWindow != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(MassageWindow);
            MassageWindow = null;
            MassageWindow.content=null;

        }
    }

    public static void getMassage(Context context) {
        if (MassageWindow != null) {
            TextView Massge_View = (TextView) MassageWindow.findViewById(R.id.Massge_View);
            Massge_View.getBackground().setAlpha(160);//0~255透明度值
            Massge_View.setText(getMassage());
        }
    }

    private static String getRunningActivityName(Context context) {
        String contextString = context.toString();
        return contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));
    }

    public static void getDot(Context context){
        if(settingActivity.massage_flag==1){
            get_massage(context);
        }
        else {
            imageView = (TextView) smallWindow.findViewById(R.id.red_dot);
            MassageWindow.count=0;
            imageView.setVisibility(View.GONE);
            flag1=0;
        }
    }
    public static void get_massage(Context context) {
        MassageWindow.content=mnotimassageService.content;
        String A=getRunningActivityName(context);
        if(A.equals("com.tencent.mm.ui.LauncherUI"))
        {
            MassageWindow.flag=1;
            MassageWindow.count=0;
        }
        if((content1==MassageWindow.content)&& MassageWindow.flag==1) {
            imageView = (TextView) smallWindow.findViewById(R.id.red_dot);
            MassageWindow.count=0;
            scount=String.valueOf(MassageWindow.count);
            imageView.setText(scount);
            imageView.setVisibility(View.GONE);
            flag1=0;
        }
        else if (content1!=MassageWindow.content) {
            imageView = (TextView) smallWindow.findViewById(R.id.red_dot);
            scount=String.valueOf(MassageWindow.count);
            imageView.setText(scount);
            if(MassageWindow.count!=0)
                imageView.setVisibility(View.VISIBLE);
            content1=MassageWindow.content;
            MassageWindow.flag=0;
            MassageWindow.count++;
            flag1=1;

        }
        else  {
            imageView = (TextView) smallWindow.findViewById(R.id.red_dot);
            scount=String.valueOf(MassageWindow.count);
            imageView.setText(scount);
            if(MassageWindow.count!=0)
                imageView.setVisibility(View.VISIBLE);
            MassageWindow.flag=0;
            flag1=1;
        }
    }



    public static String getMassage() {
        return mnotimassageService.content;
    }


    /**
     * 将小悬浮窗从屏幕上移除。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void removeSmallWindow(Context context) {
        if (smallWindow != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(smallWindow);
            smallWindow = null;
        }
    }

    /**
     * 创建一个大悬浮窗。位置为屏幕正中间。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void createBigWindow(Context context) {
        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (bigWindow == null) {
            bigWindow = new FloatWindowBigView(context);
            if (bigWindowParams == null) {
                bigWindowParams = new LayoutParams();
                bigWindowParams.x = screenWidth / 2 - FloatWindowBigView.viewWidth / 2;
                bigWindowParams.y = screenHeight / 2 - FloatWindowBigView.viewHeight / 2;
                bigWindowParams.type = LayoutParams.TYPE_PHONE;
                bigWindowParams.format = PixelFormat.RGBA_8888;
                bigWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                bigWindowParams.width = FloatWindowBigView.viewWidth;
                bigWindowParams.height = FloatWindowBigView.viewHeight;
            }
            windowManager.addView(bigWindow, bigWindowParams);
        }
    }

    /**
     * 将大悬浮窗从屏幕上移除。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void removeBigWindow(Context context) {
        if (bigWindow != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(bigWindow);
            bigWindow = null;
        }
    }

    /**
     * 更新小悬浮窗的TextView上的数据，显示内存使用的百分比。
     *
     * @param context 可传入应用程序上下文。
     */
    public static void updateUsedPercent(Context context) {
    }

    /**
     * 是否有悬浮窗(包括小悬浮窗和大悬浮窗)显示在屏幕上。
     *
     * @return 有悬浮窗显示在桌面上返回true，没有的话返回false。
     */
    public static boolean isWindowShowing() {
        return smallWindow != null || bigWindow != null;
    }

    /**
     * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
     *
     * @param context 必须为应用程序的Context.
     * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
     */
    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

    /**
     * 如果ActivityManager还未创建，则创建一个新的ActivityManager返回。否则返回当前已创建的ActivityManager。
     *
     * @param context 可传入应用程序上下文。
     * @return ActivityManager的实例，用于获取手机可用内存。
     */
    private static ActivityManager getActivityManager(Context context) {
        if (mActivityManager == null) {
            mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        }
        return mActivityManager;
    }

    /**
     * 计算已使用内存的百分比，并返回。
     *
     * @param context 可传入应用程序上下文。
     * @return 已使用内存的百分比，以字符串形式返回。
     */
    public static String getUsedPercentValue(Context context) {
        String dir = "/proc/meminfo";
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            br.close();
            long totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll("\\D+", ""));
            long availableSize = getAvailableMemory(context) / 1024;
            int percent = (int) ((totalMemorySize - availableSize) / (float) totalMemorySize * 100);
            return percent + "%";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "悬浮窗";
    }

    /**
     * 获取当前可用内存，返回数据以字节为单位。
     *
     * @param context 可传入应用程序上下文。
     * @return 当前可用内存。
     */
    private static long getAvailableMemory(Context context) {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        getActivityManager(context).getMemoryInfo(mi);
        return mi.availMem;
    }

}
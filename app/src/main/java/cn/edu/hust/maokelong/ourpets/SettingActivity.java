package cn.edu.hust.maokelong.ourpets;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import cn.edu.hust.maokelong.ourpets.alarm.AlarmActivity;

public class SettingActivity extends Activity {


    private TextView about;
    private TextView clock;
    private TextView blueteeth;
    private TextView petset;
    private Switch switch_work;
    private Switch switch_boot_auto;
    private Switch switch_messager;
    private MyReceiver receiver = null;
    public static int massage_flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences mySharedPreferences2 = getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        setContentView(R.layout.activity_setting);
        SharedPreferences mySharedPreferences1 = getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = mySharedPreferences1.edit();
        /* 设置“开启桌面宠物”开关有关事件 */
        switch_work = (Switch) findViewById(R.id.switch_work);
        switch_work.setChecked(mySharedPreferences2.getBoolean("work_ischecked", false));
        switch_work.setOnCheckedChangeListener(new
                                                       CompoundButton.OnCheckedChangeListener() {
                                                           @Override
                                                           public void onCheckedChanged(CompoundButton buttonView, boolean
                                                                   isChecked) {
                                                               editor.putBoolean("work_ischecked", isChecked);
                                                               editor.commit();
                                                               if (isChecked) {
                                                                   //选中时启动Service
                                                                   Intent intent = new Intent(SettingActivity.this,
                                                                           FloatWindowService.class);
                                                                   startService(intent);
                                                               } else {
                                                                   //非选中时移除所有悬浮窗，并停止Service
                                                                   Context context = getBaseContext();
                                                                   MyWindowManager.removeBigWindow(context);
                                                                   MyWindowManager.removeSmallWindow(context);
                                                                   Intent intent = new Intent(context, FloatWindowService.class);
                                                                   context.stopService(intent);
                                                               }
                                                           }
                                                       });


      /*设置“接收微信消息”开关有关事件*/
        /* 设置“开机自动启动”开关有关事件 */
        switch_messager = (Switch) findViewById(R.id.switch_messager);
        switch_messager.setChecked(mySharedPreferences2.getBoolean("messager_ischecked", false));//默认值怎么处理？
        switch_messager.setOnCheckedChangeListener(new
                                                           CompoundButton.OnCheckedChangeListener() {
                                                               @Override
                                                               public void onCheckedChanged(CompoundButton buttonView, boolean
                                                                       isChecked) {

                                                                   editor.putBoolean("messager_ischecked", switch_messager.isChecked());
                                                                   editor.commit();
                                                                   if (isChecked) {

                                                                       massage_flag=1;
                                                                   } else {
                                                                       massage_flag=0;

                                                                   }
                                                               }
                                                           });


        //闹铃
        clock = (TextView) findViewById(R.id.textView4);
        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg) {
                //点击操作
                Intent intent=new Intent(SettingActivity.this, AlarmActivity.class);
                startActivity(intent);
            }
        });

        //宠物设置
        petset = (TextView) findViewById(R.id.textView6);
        petset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                //点击操作

            }
        });

        startService(new Intent(SettingActivity.this, NotimassageService.class));

        //注册广播接收器
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("cn.edu.hust.maokelong.ourpets.NotimassageService");
        SettingActivity.this.registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        //结束服务
        stopService(new Intent(SettingActivity.this, NotimassageService.class));
        super.onDestroy();
    }


    /**
     * 获取广播数据
     *
     */
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String content = bundle.getString("count");
        }
    }


}


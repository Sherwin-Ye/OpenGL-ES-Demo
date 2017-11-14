package com.sherwin.rapid.base.framework.widget.util;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.DrawableRes;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Sherwin.Ye on 2017/2/6.15:24
 * 通知栏通知工具类
 */
public class NotificationUtil {

    public static int sendNotification(Context context, @DrawableRes int resId, String title, String content) {
        return sendNotification(context, null, resId, title, content);
    }

    public static int sendNotificationToActivity(Context context, Class<? extends Activity> activityClass, @DrawableRes int resId, String title, String content) {
        Intent intent = new Intent(context,activityClass);
        return sendNotification(context, intent, resId, title, content);
    }
    /**
     * 创建一个通知栏消息
     *
     * @param context
     * @param actionIntent 通知栏的action
     * @param resId
     * @param title
     * @param content
     * @return
     */
    public static int sendNotification(Context context, Intent actionIntent, @DrawableRes int resId, String title, String content) {
        //获取NotificationManager实例
        NotificationManager notifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //实例化NotificationCompat.Builde并设置相关属性
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                //设置小图标
                .setSmallIcon(resId)
                //设置通知标题
                .setContentTitle(title)
                //点击通知后自动清除
                .setAutoCancel(true)
                //设置通知内容
                .setContentText(content)
                .setDefaults(Notification.DEFAULT_SOUND);
        //获取PendingIntent
        if (actionIntent != null) {
            PendingIntent mainPendingIntent = PendingIntent.getActivity(context, 0, actionIntent, PendingIntent.FLAG_ONE_SHOT);
            builder.setContentIntent(mainPendingIntent);
        }
        //设置通知时间，默认为系统发出通知的时间，通常不用设置
        //.setWhen(System.currentTimeMillis());
        //通过builder.build()方法生成Notification对象,并发送通知,id=1
        int notifyId = (int) System.currentTimeMillis();
        notifyManager.notify(notifyId, builder.build());
        return notifyId;
    }

}

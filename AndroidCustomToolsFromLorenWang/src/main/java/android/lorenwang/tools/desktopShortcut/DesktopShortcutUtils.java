package android.lorenwang.tools.desktopShortcut;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.lorenwang.tools.base.LogUtils;
import android.lorenwang.tools.common.AtlwAndJavaCommonUtils;
import android.lorenwang.tools.messageTransmit.FlyMessageUtils;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.pm.ShortcutInfoCompat;
import android.support.v4.content.pm.ShortcutManagerCompat;
import android.support.v4.graphics.drawable.IconCompat;

import static android.lorenwang.tools.messageTransmit.FlyMessageMsgTypes.DESKTOP_SHORTCUT_CREATE_SUCCESS;


/**
 * 创建时间：2018-11-28 上午 11:38:45
 * 创建人：王亮（Loren wang）
 * 功能作用：桌面快捷方式管理类
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */
public class DesktopShortcutUtils {
    private final String TAG = getClass().getName();
    private static DesktopShortcutUtils desktopShortcutUtils;
    private DesktopShortcutUtils(){

    }
    public static DesktopShortcutUtils getInstance(){
        if(desktopShortcutUtils == null){
            desktopShortcutUtils = new DesktopShortcutUtils();
        }
        return desktopShortcutUtils;
    }

    private DesktopShortcutOptionsCallback desktopShortcutOptionsCallback;

    /**
     * 设置回调
     * @param desktopShortcutOptionsCallback
     */
    public void setDesktopShortcutOptionsCallback(DesktopShortcutOptionsCallback desktopShortcutOptionsCallback) {
        this.desktopShortcutOptionsCallback = desktopShortcutOptionsCallback;
    }

    /**
     * 添加桌面快捷方式
     * @param context 上下文
     * @param openClass 打开的class
     * @param openUrlKey 打开url的key
     * @param title 标题
     * @param url 链接
     * @param bitmap icon位图
     */
    public void addDesktopShortcut(
            Context context,Class openClass,String openUrlKey
            , String title,String url, Bitmap bitmap){
        if(ShortcutManagerCompat.isRequestPinShortcutSupported(context)){
            Intent intent = new Intent(context,openClass);
            intent.setAction(Intent.ACTION_VIEW); //action必须设置，不然报错
            intent.putExtra(openUrlKey,url);
            Bundle bundle = new Bundle();
            bundle.putString(openUrlKey,url);
            intent.putExtras(bundle);

            //设置快捷方式信息
            ShortcutInfoCompat shortcutInfoCompat =
                    new ShortcutInfoCompat.Builder(context, AtlwAndJavaCommonUtils.getInstance().generateUuid(true))
                            .setIcon(IconCompat.createWithBitmap(bitmap))
                            .setShortLabel(title)
                            .setIntent(intent)
                            .build();
            //快捷方式添加回调
            PendingIntent shortcutCallbackIntent = PendingIntent.getBroadcast(context
                    , 0, new Intent(context, DesktopShortcutReceiver.class)
                    , PendingIntent.FLAG_UPDATE_CURRENT);


            //请求添加快捷方式
            if(ShortcutManagerCompat.requestPinShortcut(context,shortcutInfoCompat,shortcutCallbackIntent.getIntentSender())){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    //创建消息接收监听
                    FlyMessageUtils.getInstance().registMsgCallback(context, DESKTOP_SHORTCUT_CREATE_SUCCESS
                            , new FlyMessageUtils.FlyMessgeCallback() {
                                @Override
                                public void msg(int msgType, Object... msgs) {
                                    LogUtils.logI(TAG,"快捷方式添加主屏幕成功");
                                    if(desktopShortcutOptionsCallback != null){
                                        desktopShortcutOptionsCallback.addSuccess();
                                    }
                                }
                            },true,context instanceof Activity);
                }
            }
        }
    }

}

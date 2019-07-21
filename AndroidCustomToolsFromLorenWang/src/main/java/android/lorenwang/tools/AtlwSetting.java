package android.lorenwang.tools;

import android.app.Activity;
import android.app.Application;
import android.lorenwang.tools.messageTransmit.AtlwFlyMessageUtils;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import static android.lorenwang.tools.messageTransmit.AtlwFlyMessageMsgTypes.ACTIVITY_LIFECYCLE_CALLBACKS_ON_CREATE;
import static android.lorenwang.tools.messageTransmit.AtlwFlyMessageMsgTypes.ACTIVITY_LIFECYCLE_CALLBACKS_ON_DESTROYED;
import static android.lorenwang.tools.messageTransmit.AtlwFlyMessageMsgTypes.ACTIVITY_LIFECYCLE_CALLBACKS_ON_PAUSED;
import static android.lorenwang.tools.messageTransmit.AtlwFlyMessageMsgTypes.ACTIVITY_LIFECYCLE_CALLBACKS_ON_RESUMED;
import static android.lorenwang.tools.messageTransmit.AtlwFlyMessageMsgTypes.ACTIVITY_LIFECYCLE_CALLBACKS_ON_SAVE_INSTANCE_STATE;
import static android.lorenwang.tools.messageTransmit.AtlwFlyMessageMsgTypes.ACTIVITY_LIFECYCLE_CALLBACKS_ON_START;
import static android.lorenwang.tools.messageTransmit.AtlwFlyMessageMsgTypes.ACTIVITY_LIFECYCLE_CALLBACKS_ON_STOPPED;

/**
 * 创建时间：2019-01-29 下午 15:41:53
 * 创建人：王亮（Loren wang）
 * 功能作用：安卓工具类设置
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */
public class AtlwSetting {
    /**
     * 是否是debug模式
     */
    public static boolean isDebug = false;
    /**
     * debug日志存储地址
     */
    public static String debugLogFileSavePath = null;
    /**
     * 是否注册了生命周期监听
     */
    private static boolean isRegistActivityLifecycleCallback = false;
    /**
     * 设计稿或者屏幕宽度分辨率
     */
    public static int SCREEN_LAYOUT_BASE_WIDTH = 750;
    /**
     * 设计稿或者屏幕高度度分辨率
     */
    public static int SCREEN_LAYOUT_BASE_HEIGHT = 1334;

    /**
     * activity生命周期监听
     */
    private static final Application.ActivityLifecycleCallbacks ACTIVITY_LIFECYCLE_CALLBACKS = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            AtlwFlyMessageUtils.getInstance().sendMsg(ACTIVITY_LIFECYCLE_CALLBACKS_ON_CREATE, true, activity, bundle);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            AtlwFlyMessageUtils.getInstance().sendMsg(ACTIVITY_LIFECYCLE_CALLBACKS_ON_START, true, activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            AtlwFlyMessageUtils.getInstance().sendMsg(ACTIVITY_LIFECYCLE_CALLBACKS_ON_RESUMED, true, activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {
            AtlwFlyMessageUtils.getInstance().sendMsg(ACTIVITY_LIFECYCLE_CALLBACKS_ON_PAUSED, true, activity);
        }

        @Override
        public void onActivityStopped(Activity activity) {
            AtlwFlyMessageUtils.getInstance().sendMsg(ACTIVITY_LIFECYCLE_CALLBACKS_ON_STOPPED, true, activity);
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            AtlwFlyMessageUtils.getInstance().sendMsg(ACTIVITY_LIFECYCLE_CALLBACKS_ON_SAVE_INSTANCE_STATE, true, activity, bundle);
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            AtlwFlyMessageUtils.getInstance().sendMsg(ACTIVITY_LIFECYCLE_CALLBACKS_ON_DESTROYED, true, activity);
        }
    };

    /**
     * application注册生命周期监听
     *
     * @param application
     */
    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static void registActivityLifecycleCallbacks(Application application) {
        synchronized (AtlwSetting.class) {
            if (application != null && !isRegistActivityLifecycleCallback) {
                isRegistActivityLifecycleCallback = true;
                application.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE_CALLBACKS);
            }
        }
    }

    /**
     * 返回是否注册了生命周期监听
     *
     * @return
     */
    public static boolean isRegistActivityLifecycleCallback() {
        return isRegistActivityLifecycleCallback;
    }
}
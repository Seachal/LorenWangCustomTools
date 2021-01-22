package android.lorenwang.commonbaseframe.pulgins.login;

import android.lorenwang.commonbaseframe.pulgins.AcbflwPluginCallBack;
import android.lorenwang.commonbaseframe.pulgins.AcbflwPluginUtil;
import android.lorenwang.tools.base.AtlwLogUtil;

import com.tencent.mm.opensdk.modelmsg.SendAuth;

/**
 * 功能作用：登陆工具类
 * 创建时间：2019-12-27 16:41
 * 创建人：王亮（Loren wang）
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */

public class AcbflwLoginUtil {
    private final String TAG = "QtLoginUtils";
    private static volatile AcbflwLoginUtil optionsInstance;

    private AcbflwLoginUtil() {
    }

    public static AcbflwLoginUtil getInstance() {
        if (optionsInstance == null) {
            synchronized (AcbflwLoginUtil.class) {
                if (optionsInstance == null) {
                    optionsInstance = new AcbflwLoginUtil();
                }
            }
        }
        return optionsInstance;
    }

    /**
     * 微信登陆
     */
    public void loginToWeChat(AcbflwPluginCallBack callBack) {
        AtlwLogUtil.logUtils.logI(TAG, "准备发送微信登陆");
        // send oauth request
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        String key = String.valueOf(callBack.hashCode());
        AcbflwPluginUtil.getInstance().setWeChatLoginCallbackKey(key);
        AcbflwPluginUtil.getInstance().addCallBack(key, callBack);
        AcbflwPluginUtil.getInstance().getApi().sendReq(req);
    }
}
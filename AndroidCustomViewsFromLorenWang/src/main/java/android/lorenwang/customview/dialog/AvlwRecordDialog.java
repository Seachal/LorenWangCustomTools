package android.lorenwang.customview.dialog;

import android.content.Context;
import android.lorenwang.customview.R;

/**
 * 创建时间：2019-07-21 下午 22:08:43
 * 创建人：王亮（Loren wang）
 * 功能作用：录音弹窗
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */

public class AvlwRecordDialog extends BaseDialog{
    public AvlwRecordDialog(Context context) {
        super(context, R.layout.dialog_confirm_cancel_1, R.style.dialog_confirm_cancel_1
                , R.style.dialog_anim_for_center, false,false,false);
    }

    /**
     * 取消录音提示隐藏
     */
    public void cancelRecordHintHide() {

    }

    /**
     * 取消录音提示显示
     */
    public void cancelRecordHintShow() {

    }

    /**
     * 当前录音时间
     * @param nowRecordTime 录音详细时间
     */
    public void nowRecordTime(long nowRecordTime) {
    }

    /**
     * 录音时间过短
     */
    public void recordTimeShort() {

    }

    /**
     * 开始录音
     */
    public void startRecord() {


    }
}
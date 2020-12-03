package kotlinbase.lorenwang.tools.extend

import android.content.Context
import android.lorenwang.tools.app.AtlwActivityUtils
import android.lorenwang.tools.app.AtlwScreenUtils

/**
 * 功能作用：Context扩展
 * 初始注释时间： 2020/12/3 10:36 上午
 * 创建人：王亮（Loren）
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 *
 * @author 王亮（Loren）
 */

/**
 * 通过context获取app实例
 */
fun <C : Context> C.kttlwGetAppApplication() : Context? {
    return AtlwActivityUtils.getInstance().getApplicationContext(this)
}

/**
 * 获取屏幕宽度
 */
fun kttlwGetScreenWidth() : Int {
    return AtlwScreenUtils.getInstance().screenWidth
}

/**
 * 获取屏幕高度
 */
fun kttlwGetScreenHeight() : Int {
    return AtlwScreenUtils.getInstance().screenHeight
}
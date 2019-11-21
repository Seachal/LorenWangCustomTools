package kotlinbase.lorenwang.tools.extend

/**
 * 功能作用：基础函数扩展
 * 创建时间：2019-11-14 下午 23:17:43
 * 创建人：王亮（Loren wang）
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */
/**
 * 检测基础数据是否为空
 */
fun Any?.isEmpty(): Boolean {
    return this == null
}

/**
 * 数据检测，根据检测结果调用不同方法，调用结束后返回数据
 */
fun <T, R> T?.emptyCheck(emptyFun: () -> R, notEmptyFun: (T) -> R): R {
    return if (this.isEmpty()) {
        emptyFun()
    } else {
        notEmptyFun(this!!)
    }
}

/**
 * 空检测，如果为空调用空方法
 */
fun <T, R> T?.emptyCheck(emptyFun: () -> R) {
    if (this.isEmpty()) {
        emptyFun()
    }
}

/**
 * 待检测参数中是否包含空数据
 */
fun <T, P, R> T.haveEmptyCheck(params: Array<P>, emptyFun: () -> R, notEmptyFun: () -> R): R {
    params.forEach {
        if (it.isEmpty()) {
            return emptyFun()
        }
    }
    return notEmptyFun()
}

package android.lorenwang.commonbaseframe.list

import android.lorenwang.commonbaseframe.adapter.AcbflwBaseRecyclerViewHolder
import android.lorenwang.commonbaseframe.adapter.AcbflwBaseType
import android.view.View

/**
 * 功能作用：列表数据操作装饰器接口
 * 创建时间：2020-01-07 17:54
 * 创建人：王亮（Loren wang）
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */
interface AcbflwBaseListDataOptionsDecorator<T> {
    /**
     * 获取列表家长群的viewHolder
     *
     * @return 返回要加载使用的viewHolder
     */
    fun getListViewHolder(viewType: Int, itemView: View): AcbflwBaseRecyclerViewHolder<T>

    /**
     * 清空数据列表
     */
    fun clear()

    /**
     * 添加数据和布局id
     *
     * @param list     数据列表
     * @param layoutId 布局id
     */
    fun singleTypeLoad(list: List<T>?, layoutId: Int, haveMoreData: Boolean)

    /**
     * 添加转换后的basetype数据
     *
     * @param list basetype数据列表
     */
    fun multiTypeLoad(list: List<AcbflwBaseType<T>>?, haveMoreData: Boolean)

    /**
     * 清除旧数据并添加新数据和布局id
     *
     * @param list     数据列表
     * @param layoutId 布局id
     */
    fun singleTypeRefresh(list: List<T>?, layoutId: Int, haveMoreData: Boolean)

    /**
     * 清除旧数据并添加转换后的basetype数据
     *
     * @param list basetype数据列表
     */
    fun multiTypeRefresh(list: List<AcbflwBaseType<T>>?, haveMoreData: Boolean)

    /**
     * 显示空视图
     *
     * @param layoutId 布局资源id
     * @param desc     空视图实例
     */
    fun showEmptyView(layoutId: Int, desc: T?, haveMoreData: Boolean)

    /**
     * 获取适配器数据
     *
     * @return 适配器数据列表
     */
    val adapterDataList: ArrayList<AcbflwBaseType<T>>
}

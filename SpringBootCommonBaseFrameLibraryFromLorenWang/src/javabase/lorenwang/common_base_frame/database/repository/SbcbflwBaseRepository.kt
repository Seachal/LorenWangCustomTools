package javabase.lorenwang.common_base_frame.database.repository

import javabase.lorenwang.common_base_frame.database.table.SbcbflwBaseTb
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean

/**
 * 功能作用：用户数据库表操作
 * 创建时间：2019-09-19 下午 15:10:49
 * 创建人：王亮（Loren）
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */
@NoRepositoryBean
interface SbcbflwBaseRepository<T : SbcbflwBaseTb> : JpaRepository<T, Long>, JpaSpecificationExecutor<T>

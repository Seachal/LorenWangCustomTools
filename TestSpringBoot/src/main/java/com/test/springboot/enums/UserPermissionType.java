package com.test.springboot.enums;

import javabase.lorenwang.common_base_frame.enums.SbcbflwBaseUserPermissionType;

/**
 * 功能作用：用户权限
 * 创建时间：2020-07-13 11:41 上午
 * 创建人：王亮（Loren wang）
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：基类以及子类必须使用java文件格式，否则会导致无法使用父类变量
 *
 * @author 王亮（Loren wang）
 */

public class UserPermissionType extends SbcbflwBaseUserPermissionType {
    public UserPermissionType(int type, String des) {
        super(type, des);
    }
}
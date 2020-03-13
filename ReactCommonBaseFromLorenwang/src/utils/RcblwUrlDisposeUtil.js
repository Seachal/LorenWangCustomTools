import RcblwVariableDisposeUtil from "./RcblwVariableDisposeUtil";

/**
 * 功能作用：url相关处理
 * 初始注释时间： 2020/3/13 15:28
 * 注释创建人：LorenWang（王亮）
 * 方法介绍：
 * 思路：
 * 修改人：
 * 修改时间：
 * 备注：
 */
const RcblwUrlDisposeUtil = {
    /**
     * 格式化url地址获取参数值
     * @param key 参数名称
     * @returns {string} 参数值
     */
    getUrlParams(key) {
        if (RcblwVariableDisposeUtil.paramsIsString(key)) {
            //地址转码
            let url = decodeURI(document.URL);
            let reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
            let arg = window.location.search.substr(1).match(reg);
            if (arg != null) {
                return unescape(arg[2]);
            }
        }
        return key
    }
};
export default RcblwUrlDisposeUtil;

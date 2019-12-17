package javabase.lorenwang.common_base_frame.email

import javabase.lorenwang.common_base_frame.SbcbflwPropertiesConfig
import javabase.lorenwang.tools.JtlwLogUtils
import kotlinbase.lorenwang.tools.extend.emptyCheck
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.MimeMessageHelper


/**
 * 功能作用：email相关操作
 * 创建时间：2019-12-16 12:13
 * 创建人：王亮（Loren wang）
 * 思路：
 * 方法：
 * 1、发送邮件---sendEmailMessage(title,content,tiEmail)
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */
class SbcbflwEmailUtils {
    private var javaMailSender: JavaMailSenderImpl? = null

    companion object {
        private var optionsUtils: SbcbflwEmailUtils? = null
        val instance: SbcbflwEmailUtils
            get() {
                if (optionsUtils == null) {
                    synchronized(this::class.java) {
                        if (optionsUtils == null) {
                            optionsUtils = SbcbflwEmailUtils()
                        }
                    }
                }
                return optionsUtils!!
            }
    }

    init {
        try {
            javaMailSender = JavaMailSenderImpl()
            javaMailSender?.javaMailProperties = SbcbflwPropertiesConfig.emailProperties
            javaMailSender?.host = SbcbflwPropertiesConfig.emailHost
            javaMailSender?.username = SbcbflwPropertiesConfig.emailUserName
            javaMailSender?.password = SbcbflwPropertiesConfig.emailUserPassword
            javaMailSender?.testConnection()
            JtlwLogUtils.logI(this::class.java, "邮件发送工具初始化结束，测试连接状态")
        } catch (e: Exception) {
            javaMailSender = null
            JtlwLogUtils.logE(this::class.java, "邮件发送工具初始化失败，失败原因：${e.message}")
        }
    }

    /**
     * 发送邮件
     * @param title 邮件标题
     * @param content 邮件内容
     * @param toEmail 目标邮件
     * @return 邮件发送结果，true成功
     */
    fun sendEmailMessage(title: String, content: String, toEmail: String): Boolean {
        return javaMailSender.emptyCheck({
            JtlwLogUtils.logI(this::class.java, "向${toEmail}发送邮件失败,邮件发送实例无法初始化")
            false
        }, {
            try {
                JtlwLogUtils.logI(this::class.java, "开始向${toEmail}发送邮件")
                val message = it.createMimeMessage()
                val messageHelper = MimeMessageHelper(message, true, "utf-8")
                messageHelper.setFrom(SbcbflwPropertiesConfig.emailUserName)
                messageHelper.setTo(toEmail)
                messageHelper.setSubject(title)
                messageHelper.setText(content)
                it.send(message)
                JtlwLogUtils.logI(this::class.java, "向${toEmail}发送邮件成功")
                true
            } catch (e: Exception) {
                JtlwLogUtils.logI(this::class.java, "向${toEmail}发送邮件失败：${e.message}")
                false
            }
        })
    }
}
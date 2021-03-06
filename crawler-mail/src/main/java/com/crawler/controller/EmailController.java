package com.crawler.controller;

import com.crawler.domain.BaseEntity;
import com.crawler.exception.CrawlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static com.crawler.constant.ResponseCodeConst.MESSAGE_CODE_OK;

/**
 * 发送邮件Controller
 */
@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    protected TemplateEngine thymeleaf;

    /**
     * 发送简单邮件
     */
    @GetMapping("/email")
    public BaseEntity email(BaseEntity be) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("110@qq.com");
        message.setTo("120@qq.com");
        message.setSubject("主题：简单邮件");
        message.setText("测试邮件内容");
        mailSender.send(message);
        be.setMsgCode(MESSAGE_CODE_OK.getCode());
        be.setContent("发送简单邮件成功");
        return be;
    }

    /**
     * 发送模版邮件
     */
    @GetMapping("/sendTemplateEmail")
    public BaseEntity sendTemplateEmail(BaseEntity be) throws CrawlerException {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);
        Context con = new Context();
        con.setVariable("hello", "crawler");
        String emailText = thymeleaf.process("emailTemplate", con);
        try {
            messageHelper.setTo("120@qq.com");
            messageHelper.setFrom("110@qq.com");
            messageHelper.setSubject("主题：简单邮件");
            messageHelper.setText(emailText, true);
        } catch (MessagingException e) {
            throw new CrawlerException(e);
        }
        // 发送邮件
        mailSender.send(mailMessage);
        be.setMsgCode(MESSAGE_CODE_OK.getCode());
        be.setContent("发送模版邮件成功");
        return be;
    }
}

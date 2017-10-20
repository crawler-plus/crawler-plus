package com.crawler.controller;

import com.crawler.constant.Const;
import com.crawler.domain.BaseEntity;
import com.crawler.exception.CrawlerException;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value="发送简单邮件", notes="发送简单邮件")
    @GetMapping("/email")
    public BaseEntity email() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("110@qq.com");
        message.setTo("120@qq.com");
        message.setSubject("主题：简单邮件");
        message.setText("测试邮件内容");
        mailSender.send(message);
        BaseEntity be = new BaseEntity();
        be.setMsgCode(Const.MESSAGE_CODE_OK);
        be.setContent("发送简单邮件成功");
        return be;
    }

    /**
     * 发送模版邮件
     */
    @ApiOperation(value="发送模版邮件", notes="发送模版邮件")
    @GetMapping("/sendTemplateEmail")
    public BaseEntity sendTemplateEmail() throws CrawlerException {
        BaseEntity be = new BaseEntity();
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
        be.setMsgCode(Const.MESSAGE_CODE_OK);
        be.setContent("发送模版邮件成功");
        return be;
    }
}

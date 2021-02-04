package com.rjpk.logpolice.autoconfigure;

import com.rjpk.logpolice.notice.channel.MailSendNoticeChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

/**
 * @ClassName LogpoliceMailAutoConfiguration
 * @Description 邮件自动装配
 * @Author xuxiangnan
 * @Date 2021/1/28 13:27
 */
@ConditionalOnProperty(name = "log.police.send", havingValue = "mail")
@Configuration
@AutoConfigureAfter({MailSenderAutoConfiguration.class})
@ConditionalOnBean({MailSender.class, MailProperties.class})
public class LogpoliceMailAutoConfiguration {
    private final MailSender mailSender;
    private final MailProperties mailProperties;

    @Autowired
    public LogpoliceMailAutoConfiguration(MailSender mailSender, MailProperties mailProperties) {
        this.mailSender = mailSender;
        this.mailProperties = mailProperties;
    }

    @Bean
    public MailSendNoticeChannel mailSendNoticeChannel() {
        return new MailSendNoticeChannel(mailSender, mailProperties);
    }
}

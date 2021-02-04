package com.rjpk.logpolice.notice.channel;

import com.rjpk.logpolice.notice.entity.ExceptionNotice;
import com.rjpk.logpolice.properties.LogpoliceProperties;
import com.rjpk.logpolice.utils.ApplicationContextProvider;
import com.rjpk.logpolice.utils.enums.NoticeSendEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * @ClassName MailSendNoticeChannel
 * @Description 邮件发送
 * @Author xuxiangnan
 * @Date 2021/1/28 13:46
 */
@Slf4j
public class MailSendNoticeChannel implements SendNoticeChannel {
    private final MailSender mailSender;
    private final MailProperties mailProperties;

    public MailSendNoticeChannel(MailSender mailSender, MailProperties mailProperties) {
        this.mailSender = mailSender;
        this.mailProperties = mailProperties;
    }

    @Override
    public NoticeSendEnum getType() {
        return NoticeSendEnum.MAIL;
    }

    @Override
    public void send(ExceptionNotice exceptionNotice) {
        LogpoliceProperties logpoliceProperties = ApplicationContextProvider.getBean(LogpoliceProperties.class);
        String toMails = logpoliceProperties.getToMails();
        if(StringUtils.isEmpty(toMails)){
            return;
        }
        String[] toMailsArr = toMails.split(",");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String fromEmail = mailProperties.getUsername();
        mailMessage.setFrom(fromEmail);
        mailMessage.setTo(toMailsArr);
        String[] cc = new String[0];
        if (cc != null && cc.length > 0) {
            mailMessage.setCc(cc);
        }
        String[] bcc = new String[0];
        if (bcc != null && bcc.length > 0) {
            mailMessage.setBcc(bcc);
        }
        mailMessage.setText(exceptionNotice.getText());
        mailMessage.setSubject(String.format("来自%s的异常提醒", exceptionNotice.getProject()));
        mailSender.send(mailMessage);
    }
}

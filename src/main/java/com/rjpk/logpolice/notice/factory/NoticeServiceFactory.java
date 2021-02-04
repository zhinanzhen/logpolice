package com.rjpk.logpolice.notice.factory;

import com.rjpk.logpolice.notice.channel.SendNoticeChannel;
import com.rjpk.logpolice.notice.channel.SendNoticeStatisticChannel;
import com.rjpk.logpolice.utils.ApplicationContextProvider;
import com.rjpk.logpolice.utils.LockUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

 /**
  * @ClassName NoticeServiceFactory
  * @Description
  * @Author xuxiangnan
  * @Date 2021/1/28 17:25
  */
@Slf4j
public class NoticeServiceFactory {
    public SendNoticeChannel getNoticeChannel() {
        SendNoticeChannel sendNoticeChannel = ApplicationContextProvider.getBean(SendNoticeChannel.class);
        Assert.notNull(sendNoticeChannel,"NoticeServiceFactory.getNoticeChannel SendNoticeChannel not exists");
        return sendNoticeChannel;
    }

     public SendNoticeStatisticChannel getSendNoticeStatisticChannel() {
         SendNoticeStatisticChannel sendNoticeStatisticChannel = ApplicationContextProvider.getBean(SendNoticeStatisticChannel.class);
         Assert.notNull(sendNoticeStatisticChannel,"NoticeServiceFactory.getSendNoticeStatisticChannel SendNoticeStatisticChannel not exists");
         return sendNoticeStatisticChannel;
     }

    public LockUtils getLockUtils() {
        LockUtils lockUtils = ApplicationContextProvider.getBean(LockUtils.class);
        Assert.notNull(lockUtils,"NoticeServiceFactory.getLockUtils LockUtils not exists");
        return lockUtils;
    }
}

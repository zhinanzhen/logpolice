package com.rjpk.logpolice.notice.channel;

import com.rjpk.logpolice.notice.entity.ExceptionStatistic;
import com.rjpk.logpolice.utils.ConcurrentCache;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;

 /**
  * @ClassName ExceptionStatisticLocalCache
  * @Description 统计本地缓存
  * @Author xuxiangnan
  * @Date 2021/1/28 20:54
  */
@Slf4j
public class ExceptionStatisticLocalCache implements SendNoticeStatisticChannel {

    private final ConcurrentCache<String, ExceptionStatistic> exceptionStatisticMap;

    public ExceptionStatisticLocalCache(ConcurrentCache<String, ExceptionStatistic> exceptionStatisticMap) {
        this.exceptionStatisticMap = exceptionStatisticMap;
    }

    @Override
    public Optional<ExceptionStatistic> findByOpenId(String openId) {
        ExceptionStatistic exceptionStatistic = exceptionStatisticMap.get(openId);
        if (Objects.isNull(exceptionStatistic)) {
            return Optional.empty();
        }
        return Optional.of(new ExceptionStatistic(exceptionStatistic.getOpenId(),
                exceptionStatistic.getShowCount(),
                exceptionStatistic.getLastShowedCount(),
                exceptionStatistic.getFirstTime(),
                exceptionStatistic.getNoticeTime()));
    }

    @Override
    public boolean save(String openId, ExceptionStatistic exceptionStatistic) {
        exceptionStatisticMap.put(openId, exceptionStatistic);
        return true;
    }
}

package com.vay.invest_bot.scheduler;

import com.vay.invest_bot.service.InvestBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvalidationScheduler {

    private final InvestBotService investBotService;

    @Scheduled(cron = "* 0 0 * * ?")
    public void invalidateCache() {
        investBotService.clearUSDCache();
        investBotService.clearEURCache();
    }
}

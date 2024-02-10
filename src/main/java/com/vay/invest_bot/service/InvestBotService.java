package com.vay.invest_bot.service;

import com.vay.invest_bot.exception.ServiceException;

public interface InvestBotService {
    String getUSD() throws ServiceException;
    String getEUR() throws ServiceException;
}

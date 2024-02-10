package com.vay.invest_bot.command;

import com.vay.invest_bot.exception.ServiceException;
import com.vay.invest_bot.service.InvestBotService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StartCommand {


    public static String startCommand() {
        var text = """
                Добро пожаловать в InvestBot!
                                
                Здесь вы можете узнать курс валют на сегодня, установленные ЦБ РФ.
                                
                Для этого воспользуйтесь командами:
                /usd - курса доллара
                /eur - курса евро
                                
                Дополнительные команды:
                /help - получение справки
                """;
        return text;
    }

//    private void usdCommand(Long chatId) {
//        String formattedText;
//        try {
//            var usd = investBotService.getUSD();
//            var text = "курс доллара на %s составляет %s";
//            formattedText = String.format(text, LocalDate.now().format(DateTimeFormatter.ofPattern("d MMMM")), usd);
//        } catch (ServiceException e) {
//            log.error("Ошибка получения курса доллара", e);
//            formattedText = "Сервис не доступен, попробуйте позже";
//        }
//        sendMessage(chatId, formattedText);
//    }
//
//    private void eurCommand(Long chatId) {
//        String formattedText;
//        try {
//            var eur = investBotService.getEUR();
//            var text = "курс евро на %s составляет %s";
//            formattedText = String.format(text, LocalDate.now().format(DateTimeFormatter.ofPattern("d MMMM")), eur);
//        } catch (ServiceException e) {
//            log.error("Ошибка получения курса евро", e);
//            formattedText = "Сервис не доступен, попробуйте позже";
//        }
//        sendMessage(chatId, formattedText);
//    }
}

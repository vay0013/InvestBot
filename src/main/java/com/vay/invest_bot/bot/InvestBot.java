package com.vay.invest_bot.bot;

import com.vay.invest_bot.exception.ServiceException;
import com.vay.invest_bot.service.InvestBotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class InvestBot extends TelegramLongPollingBot {

    private final InvestBotService investBotService;

    public InvestBot(@Value("${bot.token}") String token, InvestBotService investBotService) {
        super(token);
        this.investBotService = investBotService;
    }

    private static final String START = "/start";
    private static final String USD = "/usd";
    private static final String EUR = "/eur";
    private static final String HELP = "/help";

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        var message = update.getMessage().getText();
        var chatId = update.getMessage().getChatId();
        switch (message) {
            case START -> startCommand(chatId);
            case USD -> usdCommand(chatId);
            case EUR -> eurCommand(chatId);
            case HELP -> helpCommand(chatId);
            default -> unknownCommand(chatId);
        }
    }

    @Override
    public String getBotUsername() {
        return "MyBestInvestBot";
    }

    private void startCommand(Long chatId) {
        var text = """
                Добро пожаловать в InvestBot!
                                
                Здесь вы можете узнать курс валют на сегодня, установленные ЦБ РФ.
                                
                Для этого воспользуйтесь командами:
                /usd - курса доллара
                /eur - курса евро
                                
                Дополнительные команды:
                /help - получение справки
                """;
        sendMessage(chatId, text);
    }

    private void usdCommand(Long chatId) {
        String formattedText;
        try {
            var usd = investBotService.getUSD();
            var text = "курс доллара на %s составляет %s";
            formattedText = String.format(text, LocalDate.now().format(DateTimeFormatter.ofPattern("d MMMM")), usd);
        } catch (ServiceException e) {
            log.error("Ошибка получения курса доллара", e);
            formattedText = "Сервис не доступен, попробуйте позже";
        }
        sendMessage(chatId, formattedText);
    }

    private void eurCommand(Long chatId) {
        String formattedText;
        try {
            var eur = investBotService.getEUR();
            var text = "курс евро на %s составляет %s";
            formattedText = String.format(text, LocalDate.now().format(DateTimeFormatter.ofPattern("d MMMM")), eur);
        } catch (ServiceException e) {
            log.error("Ошибка получения курса евро", e);
            formattedText = "Сервис не доступен, попробуйте позже";
        }
        sendMessage(chatId, formattedText);
    }

    private void helpCommand(Long chatId) {
        var text = """
                Справочная информация по боту.
                
                Для получения текущего курса валют воспользуйтесь командами:
                /usd - курс доллара
                /eur - курс евро
                """;
        sendMessage(chatId, text);
    }

    private void unknownCommand(Long chatId) {
        var text = "Такой команды нет";
        sendMessage(chatId, text);
    }

    private void sendMessage(Long chatId, String text) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения", e);
        }
    }
}


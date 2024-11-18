package com.belov.pavel.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Log4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String BOT_NAME;

    @Value("${bot.token}")
    private String BOT_TOKEN;

    private UpdateController updateController;

    public TelegramBot(UpdateController updateController){
        this.updateController = updateController;
    }

    @PostConstruct
    public void init(){
        updateController.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage();
        updateController.processUpdate(update);
    }

    public void sendAnswerMessage(SendMessage m){
        if (m!=null){
            try {
                execute(m);
            }catch (TelegramApiException e){
                log.error(e);
            }
        }
    }
}

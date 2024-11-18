package com.belov.pavel.controller;

import com.belov.pavel.service.UpdateProducer;
import com.belov.pavel.utils.MessageUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.belov.pavel.RabbitMQ.*;

@Log4j
@Component
public class UpdateController {
    private TelegramBot bot;
    private final MessageUtils messageUtils;
    private final UpdateProducer updateProducer;

    public UpdateController(MessageUtils messageUtils, UpdateProducer updateProducer){
        this.messageUtils = messageUtils;
        this.updateProducer = updateProducer;
    }

    public void registerBot(TelegramBot bot) {
        this.bot = bot;
    }

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Received update is null");
            return;
        }

        if (update.getMessage() != null) distributeMessageByType(update);
        else log.error("Unsupported message type is received : " + update);
    }

    private void distributeMessageByType(Update update) {
        var message = update.getMessage();

        if (message.getText() != null) processTextMessage(update);
        else if (message.getDocument()!= null) processDocMessage(update);
        else if (message.getPhoto() != null) processPhotoMessage(update);
        else setUnsupportedMessageTypeView(update);

    }

    private void setUnsupportedMessageTypeView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,
                "Unsupported message type!");
        setView(sendMessage);
    }

    public void setView(SendMessage sendMessage) {
        bot.sendAnswerMessage(sendMessage);
    }
    private void setFileReceivedView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,
                "File received and being processed...");
        setView(sendMessage);
    }

    private void processPhotoMessage(Update update) {
        setFileReceivedView(update);
        updateProducer.produce(PHOTO_MESSAGE_UPDATE, update);
    }

    private void processDocMessage(Update update) {
        setFileReceivedView(update);
        updateProducer.produce(DOC_MESSAGE_UPDATE, update);
    }

    private void processTextMessage(Update update) {
        updateProducer.produce(TEXT_MESSAGE_UPDATE, update);
    }


}

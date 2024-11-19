package com.belov.pavel.service.impl;

import com.belov.pavel.entity.RawData;
import com.belov.pavel.repository.RawDataRepo;
import com.belov.pavel.service.MainService;
import com.belov.pavel.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {
        private final RawDataRepo repo;
        private final ProducerService producerService;

    @Override
    public void processTextMessage(Update update) {
        saveRawData(update);

        var message = update.getMessage();
        var sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Hello from consumer NODE");
        producerService.produceAnswers(sendMessage);
    }

    private void saveRawData(Update update) {
        RawData rawData = RawData.builder()
                .event(update)
                .build();
        repo.save(rawData);
    }













}

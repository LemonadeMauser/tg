package com.belov.pavel.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public interface ProducerService {
    void produceAnswers(SendMessage sendMessage);
}

package com.belov.pavel.service.impl;

import com.belov.pavel.controller.UpdateController;
import com.belov.pavel.service.AnswerConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.belov.pavel.RabbitMQ.ANSWER_MESSAGE;

@Service
@RequiredArgsConstructor
public class AnswerConsumerImpl implements AnswerConsumer {
   private final UpdateController controller;


    @Override
    @RabbitListener(queues = ANSWER_MESSAGE)
    public void consume(SendMessage sendMessage) {
        controller.setView(sendMessage);
    }
}

package com.belov.pavel.service.impl;

import com.belov.pavel.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.belov.pavel.RabbitMQ.ANSWER_MESSAGE;

@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void produceAnswers(SendMessage sendMessage) {
        rabbitTemplate.convertAndSend(ANSWER_MESSAGE, sendMessage);
    }












}

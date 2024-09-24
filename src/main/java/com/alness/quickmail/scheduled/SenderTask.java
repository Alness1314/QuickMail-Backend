package com.alness.quickmail.scheduled;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SenderTask {
    @Scheduled(cron = "${CRON_EXPRESION}")
    private void sendMail(){
        log.info("fecha actual: {}", LocalDateTime.now());
    }
}

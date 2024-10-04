package com.alness.quickmail.scheduled;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alness.quickmail.alert.service.AlertSendService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SenderTask {
    @Autowired
    private AlertSendService alertSendService;

    @Scheduled(cron = "${CRON_EXPRESION}")
    private void sendMail() {
        Long inicio = System.currentTimeMillis();
        try {

            log.trace("Send e-mail Scheduled started at {}", new Date());
            alertSendService.sendAlert();

        } catch (Exception e) {
            log.error("error al ejecutar la funcion scheduled ", e);
        } finally {
            log.info("TIEMPO Autodiagnostico JOB | executeInternal >>> {} milisegundos",
                    (double) (System.currentTimeMillis() - inicio));
        }

    }
}

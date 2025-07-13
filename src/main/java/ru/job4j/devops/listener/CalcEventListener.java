package ru.job4j.devops.listener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.job4j.devops.models.CalcEvent;
import ru.job4j.devops.services.CalcEventService;

@Component
@AllArgsConstructor
@Slf4j
public class CalcEventListener {
    private final CalcEventService calcEventService;

    @KafkaListener(topics = "createCalcEvent", groupId = "job4j")
    public void createCalcEvent(CalcEvent calcEvent) {
        log.debug("Received calcEvent: {}", calcEvent.getId());
        calcEventService.create(calcEvent);
    }
}

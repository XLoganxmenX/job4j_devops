package ru.job4j.devops.listener;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import ru.job4j.devops.config.ContainersConfig;
import ru.job4j.devops.models.CalcEvent;
import ru.job4j.devops.models.User;
import ru.job4j.devops.repository.UserRepository;
import ru.job4j.devops.services.CalcEventService;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
public class CalcEventListenerTest extends ContainersConfig {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private CalcEventService calcEventService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenCreateCalcEvent() {
        var user = new User();
        user.setUsername("test");
        userRepository.save(user);

        var calcEvent = new CalcEvent(user, 1, 2, 3, LocalDateTime.now(), "add");
        kafkaTemplate.send("createCalcEvent", calcEvent);
        await()
                .pollInterval(Duration.ofSeconds(3))
                .atMost(10, SECONDS)
                .untilAsserted(() -> {
                    var foundedEvents = calcEventService.findAll();
                    assertThat(foundedEvents)
                            .isNotEmpty()
                            .hasSize(1);
                    assertThat(foundedEvents.get(0))
                            .usingRecursiveComparison()
                            .ignoringFields("id", "createDate")
                            .isEqualTo(calcEvent);
                });
    }
}

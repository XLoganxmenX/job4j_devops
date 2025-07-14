package ru.job4j.devops.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.devops.config.ContainersConfig;
import ru.job4j.devops.models.CalcEvent;
import ru.job4j.devops.models.User;
import ru.job4j.devops.repository.UserRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CalcEventServiceTest extends ContainersConfig {
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
        var createdEvent = calcEventService.create(calcEvent);
        var foundedEvent = calcEventService.findById(createdEvent.getId());

        assertThat(foundedEvent).isPresent();
        assertThat(createdEvent).usingRecursiveComparison()
                .ignoringFields("createDate")
                .isEqualTo(foundedEvent.get());
    }
}

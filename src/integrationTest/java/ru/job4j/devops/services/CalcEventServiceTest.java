package ru.job4j.devops.services;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.job4j.devops.models.CalcEvent;
import ru.job4j.devops.models.User;
import ru.job4j.devops.repository.UserRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CalcEventServiceTest {
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    ).withReuse(true);

    @Autowired
    private CalcEventService calcEventService;

    @Autowired
    private UserRepository userRepository;

    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("spring.liquibase.enabled", () -> "true");
        registry.add("spring.liquibase.change-log", () -> "classpath:db/changelog/db.changelog-master.xml");
    }

    @BeforeAll
    static void beforeAll() {
        POSTGRES.start();
    }

    @AfterAll
    static void afterAll() {
        POSTGRES.stop();
    }

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

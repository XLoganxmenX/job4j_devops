package ru.job4j.devops.listener;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;
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
public class CalcEventListenerTest {
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    private static final KafkaContainer KAFKA = new KafkaContainer(
            DockerImageName.parse("apache/kafka:3.7.2")
    );

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private CalcEventService calcEventService;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    static void beforeAll() {
        POSTGRES.start();
        KAFKA.start();
    }

    @AfterAll
    static void afterAll() {
        POSTGRES.stop();
        KAFKA.stop();
    }

    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.consumer.auto-offset-reset", () -> "earliest");
        registry.add("spring.kafka.bootstrap-servers", KAFKA::getBootstrapServers);
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

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

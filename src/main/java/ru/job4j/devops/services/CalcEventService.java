package ru.job4j.devops.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.devops.models.CalcEvent;
import ru.job4j.devops.repository.CalcEventRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CalcEventService {
    @Autowired
    private CalcEventRepository repository;

    public CalcEvent create(CalcEvent calcEvent) {
        return repository.save(calcEvent);
    }

    public Optional<CalcEvent> findById(Long eventId) {
        return repository.findById(eventId);
    }

    public List<CalcEvent> findAll() {
        return repository.findAll();
    }
}

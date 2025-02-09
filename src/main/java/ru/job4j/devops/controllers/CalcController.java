package ru.job4j.devops.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.devops.models.Result;
import ru.job4j.devops.models.TwoArgs;

/**
 * Rest-контроллер для выполнения базовых арифметических операций, таких как сложение и умножение
 * для объекта {@link TwoArgs}.
 * Класс взаимодействует с классами {@link TwoArgs} для получения входных данных
 * и {@link Result} для формирования ответа.
 * Обращение к контроллеру осуществляется через endpoint - /calc
 */
@RestController
@RequestMapping("calc")
public class CalcController {
    /**
     * Метод принимает объект {@link TwoArgs} и суммирует его поля.
     * @param twoArgs аргументы для рассчета
     * @return класс {@link Result} с результатом суммирования в обертке {@link ResponseEntity} со статусом 200
     */
    @PostMapping("summarise")
    public ResponseEntity<Result> summarise(@RequestBody TwoArgs twoArgs) {
        var result = twoArgs.getFirst() + twoArgs.getSecond();
        return ResponseEntity.ok(new Result(result));
    }

    /**
     * Метод принимает объект {@link TwoArgs} и умножает его поля.
     * @param twoArgs аргументы для рассчета
     * @return класс {@link Result} с результатом умножения в обертке {@link ResponseEntity} со статусом 200
     */
    @PostMapping("times")
    public ResponseEntity<Result> times(@RequestBody TwoArgs twoArgs) {
        var result = twoArgs.getFirst() * twoArgs.getSecond();
        return ResponseEntity.ok(new Result(result));
    }
}

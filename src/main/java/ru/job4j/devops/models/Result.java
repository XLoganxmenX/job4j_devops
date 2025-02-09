package ru.job4j.devops.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, представляющий собой результат арифметических операций.
 * Используется для передачи выходных данных арифметических операций.
 * Состоит из полей: {@code value} - значение результата
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private double value;
}

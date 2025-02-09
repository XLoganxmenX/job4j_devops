package ru.job4j.devops.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, представляющий собой пару числовых аргументов типа double.
 * Используется для передачи двух чисел в качестве входных данных для арифметических операций.
 * Состоит из полей: {@code first} - первый аргумент и {@code second} - второй аргумент
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TwoArgs {
    private double first;
    private double second;
}

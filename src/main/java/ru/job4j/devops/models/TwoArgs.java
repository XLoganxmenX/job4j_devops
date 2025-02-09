package ru.job4j.devops.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  ласс, представл€ющий собой пару числовых аргументов типа double.
 * »спользуетс€ дл€ передачи двух чисел в качестве входных данных дл€ арифметических операций.
 * —остоит из полей: {@code first} - первый аргумент и {@code second} - второй аргумент
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TwoArgs {
    private double first;
    private double second;
}

package ru.job4j.devops.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  ласс, представл€ющий собой результат арифметических операций.
 * »спользуетс€ дл€ передачи выходных данных арифметических операций.
 * —остоит из полей: {@code value} - значение результата
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private double value;
}

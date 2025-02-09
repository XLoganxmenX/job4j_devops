package ru.job4j.devops.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.devops.models.Result;
import ru.job4j.devops.models.TwoArgs;

/**
 * Rest-���������� ��� ���������� ������� �������������� ��������, ����� ��� �������� � ���������
 * ��� ������� {@link TwoArgs}.
 * ����� ��������������� � �������� {@link TwoArgs} ��� ��������� ������� ������
 * � {@link Result} ��� ������������ ������.
 * ��������� � ����������� �������������� ����� endpoint - /calc
 */
@RestController
@RequestMapping("calc")
public class CalcController {
    /**
     * ����� ��������� ������ {@link TwoArgs} � ��������� ��� ����.
     * @param twoArgs ��������� ��� ��������
     * @return ����� {@link Result} � ����������� ������������ � ������� {@link ResponseEntity} �� �������� 200
     */
    @PostMapping("summarise")
    public ResponseEntity<Result> summarise(@RequestBody TwoArgs twoArgs) {
        var result = twoArgs.getFirst() + twoArgs.getSecond();
        return ResponseEntity.ok(new Result(result));
    }

    /**
     * ����� ��������� ������ {@link TwoArgs} � �������� ��� ����.
     * @param twoArgs ��������� ��� ��������
     * @return ����� {@link Result} � ����������� ��������� � ������� {@link ResponseEntity} �� �������� 200
     */
    @PostMapping("times")
    public ResponseEntity<Result> times(@RequestBody TwoArgs twoArgs) {
        var result = twoArgs.getFirst() * twoArgs.getSecond();
        return ResponseEntity.ok(new Result(result));
    }
}

package ru.job4j.devops.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * �����, �������������� ����� ���� �������� ���������� ���� double.
 * ������������ ��� �������� ���� ����� � �������� ������� ������ ��� �������������� ��������.
 * ������� �� �����: {@code first} - ������ �������� � {@code second} - ������ ��������
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TwoArgs {
    private double first;
    private double second;
}

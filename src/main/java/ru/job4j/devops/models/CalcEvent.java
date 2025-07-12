package ru.job4j.devops.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "calc_events")
public class CalcEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private int first;
    private int second;
    private int result;

    @Column(name = "create_date")
    private LocalDateTime createDate = LocalDateTime.now();

    private String type;

    public CalcEvent(User user, int first, int second, int result, LocalDateTime createDate, String type) {
        this.user = user;
        this.first = first;
        this.second = second;
        this.result = result;
        this.createDate = createDate;
        this.type = type;
    }
}

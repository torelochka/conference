package ru.zheleznov.impl.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Room room;

    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private Talk talk;

    private Date dateStart;
    private Date dateEnd;
}

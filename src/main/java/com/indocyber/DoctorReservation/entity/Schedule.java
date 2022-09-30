package com.indocyber.DoctorReservation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "Date")
    private LocalDate date;

    @Column(name = "StartTreat")
    private LocalTime startTreat;

    @Column(name = "EndTreat")
    private LocalTime endTreat;

    @ManyToOne
    @JoinColumn(name = "Doctor_Id")
    private Doctor doctor;

    @Column(name = "IsAvailable")
    private Boolean isAvailable;
}

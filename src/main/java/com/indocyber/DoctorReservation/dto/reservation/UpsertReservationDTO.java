package com.indocyber.DoctorReservation.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpsertReservationDTO {
    private Long id;
    private Long patientId;
    private Long scheduleId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
}

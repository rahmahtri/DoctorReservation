package com.indocyber.DoctorReservation.dto.patient;

import com.indocyber.DoctorReservation.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListPatientDTO {
    private List<UpsertPatientDTO> patientDTOS;
}

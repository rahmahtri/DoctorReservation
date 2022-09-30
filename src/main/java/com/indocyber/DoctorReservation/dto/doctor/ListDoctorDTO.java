package com.indocyber.DoctorReservation.dto.doctor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListDoctorDTO {
    private List<UpsertDoctorDTO> doctorDTOS;
}

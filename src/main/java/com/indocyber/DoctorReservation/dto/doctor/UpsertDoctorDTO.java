package com.indocyber.DoctorReservation.dto.doctor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpsertDoctorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String degree;
    private String contactPhone;
    private String email;
    private String address;
}

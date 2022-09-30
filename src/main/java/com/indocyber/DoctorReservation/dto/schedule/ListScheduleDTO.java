package com.indocyber.DoctorReservation.dto.schedule;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListScheduleDTO {
    List<UpsertScheduleDTO> scheduleDTOS;
}

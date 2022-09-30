package com.indocyber.DoctorReservation.service;

import com.indocyber.DoctorReservation.dto.schedule.ListScheduleDTO;
import com.indocyber.DoctorReservation.dto.schedule.UpsertScheduleDTO;
import com.indocyber.DoctorReservation.entity.Schedule;

import java.util.List;

public interface ScheduleService {
    List<Schedule> getAllSchedule();

    List<Schedule> getSchedule(Integer page);

    UpsertScheduleDTO findScheduleByID(Long id);

    public Long saveSchedule(UpsertScheduleDTO dto);

    public void saveSchedule(UpsertScheduleDTO dto, Long id);

    void deleteSchedule(Long id);

    List<Schedule> addMultipleSchedule(ListScheduleDTO scheduleDTO);

    Schedule findById(Long scheduleId);
}

package com.indocyber.DoctorReservation.repository;

import com.indocyber.DoctorReservation.entity.Schedule;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query(value = "Select * From Schedule as sdl Where sdl.isAvailable = 1", nativeQuery = true)
    List<Schedule> findAllSchedule(Pageable pagination);

    @Query(value = "Select * From Schedule as sdl Where sdl.isAvailable = 1", nativeQuery = true)
    List<Schedule> findAllSchedule();
}

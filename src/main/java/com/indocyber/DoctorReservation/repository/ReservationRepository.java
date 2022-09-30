package com.indocyber.DoctorReservation.repository;

import com.indocyber.DoctorReservation.entity.Reservation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = "Select * From Reservation as res", nativeQuery = true)
    List<Reservation> findReservation(Pageable pagination);
}

package com.indocyber.DoctorReservation.service;

import com.indocyber.DoctorReservation.dto.reservation.ListReservationDTO;
import com.indocyber.DoctorReservation.dto.reservation.UpsertReservationDTO;
import com.indocyber.DoctorReservation.entity.Reservation;

import java.util.List;

public interface ReservationService {
    List<Reservation> getAllReservations();

    List<Reservation> getReservation(Integer page);

    Reservation findReservationById(Long id);

    public Long saveReservation(UpsertReservationDTO dto);

    public void saveReservation(UpsertReservationDTO dto, Long id);

    public void deleteReservation(Long id);

    List<Reservation> addMultipleReservations(ListReservationDTO reservationDTO);
}

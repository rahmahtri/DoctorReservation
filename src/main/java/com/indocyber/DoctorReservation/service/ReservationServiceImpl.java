package com.indocyber.DoctorReservation.service;

import com.indocyber.DoctorReservation.dto.reservation.ListReservationDTO;
import com.indocyber.DoctorReservation.dto.reservation.UpsertReservationDTO;
import com.indocyber.DoctorReservation.entity.Patient;
import com.indocyber.DoctorReservation.entity.Reservation;
import com.indocyber.DoctorReservation.entity.Schedule;
import com.indocyber.DoctorReservation.repository.PatientRepository;
import com.indocyber.DoctorReservation.repository.ReservationRepository;
import com.indocyber.DoctorReservation.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService{

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    private final int rowsInPage = 2;

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> getReservation(Integer page) {
        Pageable pagination = PageRequest.of(page-1, rowsInPage, Sort.by("Id"));
        List<Reservation> reservations = reservationRepository.findReservation(pagination);
        return reservations;
    }

    @Override
    public Reservation findReservationById(Long id) {
        Optional<Reservation> findReservation = reservationRepository.findById(id);
        Reservation reservation = findReservation.get();
        reservation.getId();
        reservation.getPatient();
        reservation.getSchedule();
        reservation.getCreatedAt();
        return reservation;
    }

    @Override
    public Long saveReservation(UpsertReservationDTO dto) {
        Optional<Patient> findPatient = patientRepository.findById(dto.getPatientId());
        Patient patient = findPatient.get();

        Optional<Schedule> findSchedule = scheduleRepository.findById(dto.getScheduleId());
        Schedule schedule = findSchedule.get();
        schedule.setIsAvailable(false);

        LocalDate createdAt = LocalDate.now();

        Reservation entity = new Reservation(
                dto.getId(),
                patient,
                schedule,
                createdAt
        );

        Reservation respond = reservationRepository.save(entity);
        return respond.getId();
    }

    @Override
    public void saveReservation(UpsertReservationDTO dto, Long id) {
        Optional<Patient> findPatient = patientRepository.findById(dto.getPatientId());
        Patient patient = findPatient.get();

        Optional<Schedule> findSchedule = scheduleRepository.findById(dto.getScheduleId());
        Schedule schedule = findSchedule.get();
        schedule.setIsAvailable(false);

        LocalDate createdAt = LocalDate.now();

        Optional<Reservation> findReservation = reservationRepository.findById(id);
        Reservation reservation = findReservation.get();

        reservation.setId(id);
        reservation.setPatient(patient);
        reservation.setSchedule(schedule);
        reservation.setCreatedAt(createdAt);

        reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(Long id) {

        Optional<Reservation> findReservation = reservationRepository.findById(id);
        Reservation reservation = findReservation.get();

        Optional<Schedule> findSchedule = scheduleRepository.findById(reservation.getSchedule().getId());
        Schedule schedule = findSchedule.get();
        schedule.setIsAvailable(true);

        reservationRepository.deleteById(id);
    }

    @Override
    public List<Reservation> addMultipleReservations(ListReservationDTO reservationDTO) {

        List<Reservation> reservations = reservationDTO.getReservationDTOS()
                .stream()
                .map(r -> {
                    Optional<Patient> findPatient = patientRepository.findById(r.getPatientId());
                    Patient patient = findPatient.get();

                    Optional<Schedule> findSchedule = scheduleRepository.findById(r.getScheduleId());
                    Schedule schedule = findSchedule.get();
                    schedule.setIsAvailable(false);

                    LocalDate createdAt = LocalDate.now();

                    return new Reservation(
                            r.getId(),
                            patient,
                            schedule,
                            createdAt
                    );
                })
                .collect(Collectors.toList());

        return reservationRepository.saveAll(reservations);
    }
}

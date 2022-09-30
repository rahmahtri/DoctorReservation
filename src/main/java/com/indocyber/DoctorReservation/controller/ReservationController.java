package com.indocyber.DoctorReservation.controller;

import com.indocyber.DoctorReservation.dto.reservation.ListReservationDTO;
import com.indocyber.DoctorReservation.dto.reservation.UpsertReservationDTO;
import com.indocyber.DoctorReservation.entity.Reservation;
import com.indocyber.DoctorReservation.entity.Schedule;
import com.indocyber.DoctorReservation.service.ReservationService;
import com.indocyber.DoctorReservation.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<Object> getAllData(){
        try {
            List<Reservation> reservations = reservationService.getAllReservations();
            return ResponseEntity.status(HttpStatus.OK).body(reservations);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error");
        }
    }

    @GetMapping("/page={page}")
    public ResponseEntity<Object> get(@PathVariable(required = false) Integer page){
        page = (page == null) ? 1 : page;
        try {
            List<Reservation> reservations = reservationService.getReservation(page);
            return ResponseEntity.status(HttpStatus.OK).body(reservations);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error");
        }
    }

    @GetMapping("id={id}")
    public ResponseEntity<Object> findById(@PathVariable(required = true) Long id){
        try {
            Reservation reservation = reservationService.findReservationById(id);
            return ResponseEntity.status(HttpStatus.OK).body(reservation);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error");
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody UpsertReservationDTO dto){
        try {
            Schedule schedule = scheduleService.findById(dto.getScheduleId());
            if(schedule.getIsAvailable() == true){
                reservationService.saveReservation(dto);
                return ResponseEntity.status(HttpStatus.OK).body("Reservation berhasil ditambahkan");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("Reservation tidak berhasil ditambahkan");
            }

        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time server error");
        }
    }

    @PutMapping("/id={id}")
    public ResponseEntity<Object> put(@RequestBody UpsertReservationDTO dto, @PathVariable Long id){
        try {
            reservationService.saveReservation(dto, id);
            return ResponseEntity.status(HttpStatus.OK).body("Reservation berhasil di-update");
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error");
        }
    }

    @DeleteMapping("/id={id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Long id){
        try {
            reservationService.deleteReservation(id);
            return ResponseEntity.status(HttpStatus.OK).body("Reservation berhasil dihapus");
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error.");
        }
    }

    @PostMapping("/addMultipleReservations")
    public ResponseEntity<Object> addMultipleReservations(@RequestBody ListReservationDTO reservationDTO){
        try {
            List<Reservation> reservations = reservationService.addMultipleReservations(reservationDTO);
            return new ResponseEntity<>(reservations, HttpStatus.CREATED);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.OK).body("There is a run-time error.");
        }
    }
}

package com.indocyber.DoctorReservation.controller;

import com.indocyber.DoctorReservation.dto.schedule.ListScheduleDTO;
import com.indocyber.DoctorReservation.dto.schedule.UpsertScheduleDTO;
import com.indocyber.DoctorReservation.entity.Schedule;
import com.indocyber.DoctorReservation.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    private ResponseEntity<Object> getAllSchedule(){
        try {
            List<Schedule> scheduleList = scheduleService.getAllSchedule();
            return ResponseEntity.status(HttpStatus.OK).body(scheduleList);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping(value = {
            "/page={page}",
            "/day={day}",
            "/page={page}&day={day}"
    })
    public ResponseEntity<Object> get(@PathVariable(required = false) Integer page){
        page = (page == null) ? 1 : page;
        try {
            List<Schedule> schedules = scheduleService.getSchedule(page);
            return ResponseEntity.status(HttpStatus.OK).body(schedules);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error");
        }
    }

    @GetMapping("/id={id}")
    public ResponseEntity<Object> findById(@PathVariable(required = true) Long id){
        try {
            UpsertScheduleDTO dto = scheduleService.findScheduleByID(id);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error");
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody UpsertScheduleDTO dto){
        try {
            scheduleService.saveSchedule(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Schedule berhasil ditambahkan");
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error");
        }
    }

    @PutMapping("/id={id}")
    public ResponseEntity<Object> put(@RequestBody UpsertScheduleDTO dto, @PathVariable Long id){
        try {
            scheduleService.saveSchedule(dto, id);
            return ResponseEntity.status(HttpStatus.OK).body("Schedule berhasil di-update");
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error");
        }
    }

    @DeleteMapping("/id={id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Long id){
        try {
            scheduleService.deleteSchedule(id);
            return ResponseEntity.status(HttpStatus.OK).body("Schedule berhasil dihapus");
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error");
        }
    }

    @PostMapping("/addMultipleSchedules")
    public ResponseEntity<Object> addMultipleSchedule(@RequestBody ListScheduleDTO scheduleDTO){
        try {
            List<Schedule> schedules = scheduleService.addMultipleSchedule(scheduleDTO);
            return new ResponseEntity<>(schedules, HttpStatus.CREATED);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error");
        }
    }
}

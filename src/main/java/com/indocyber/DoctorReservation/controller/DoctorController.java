package com.indocyber.DoctorReservation.controller;

import com.indocyber.DoctorReservation.dto.doctor.ListDoctorDTO;
import com.indocyber.DoctorReservation.dto.doctor.UpsertDoctorDTO;
import com.indocyber.DoctorReservation.entity.Doctor;
import com.indocyber.DoctorReservation.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public ResponseEntity<Object> getAllDoctors(){
        try {
            List<Doctor> doctorList = doctorService.getAllDoctors();
            return ResponseEntity.status(HttpStatus.OK).body(doctorList);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping(value = {
            "/page={page}",
            "/name={fullName}",
            "/page={page}&name={fullName}"
    })
    public ResponseEntity<Object> get(@PathVariable(required = false) Integer page,
                                      @PathVariable(required = false) String fullName){
        page = (page == null) ? 1 : page;
        fullName = (fullName == null) ? "" : fullName;
        try {
            List<Doctor> doctors = doctorService.getDoctor(fullName, page);
            return ResponseEntity.status(HttpStatus.OK).body(doctors);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("/id={id}")
    public ResponseEntity<Object> findById(@PathVariable(required = true) Long id){
        try {
            UpsertDoctorDTO dto = doctorService.findDoctorById(id);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody UpsertDoctorDTO dto){
        try {
            doctorService.saveDoctor(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Data Doctor berhasil ditambahkan");
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PutMapping("/id={doctorId}")
    public ResponseEntity<Object> put(@RequestBody Doctor doctor, @PathVariable Long doctorId){
        try {
            UpsertDoctorDTO findDoctor = doctorService.findDoctorById(doctorId);
            if(findDoctor != null) {
                doctorService.updateById(doctor, doctorId);
            }
            return ResponseEntity.status(HttpStatus.OK).body("Data Doctor berhasil diupdate");
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @DeleteMapping("/id={id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Long id){
        try {
            doctorService.deleteDoctor(id);
            return ResponseEntity.status(HttpStatus.CREATED).body("Data Patient berhasil dihapus");
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping("/addMultipleDoctors")
    public ResponseEntity<Object> addMultipleDoctors(@RequestBody ListDoctorDTO doctorDTOS){
        try {
            List<Doctor> doctors = doctorService.addMultipleDoctors(doctorDTOS);
            return new ResponseEntity<>(doctors, HttpStatus.CREATED);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }
}

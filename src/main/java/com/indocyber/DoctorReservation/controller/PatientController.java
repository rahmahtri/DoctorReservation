package com.indocyber.DoctorReservation.controller;

import com.indocyber.DoctorReservation.dto.patient.ListPatientDTO;
import com.indocyber.DoctorReservation.dto.patient.UpsertPatientDTO;
import com.indocyber.DoctorReservation.entity.Patient;
import com.indocyber.DoctorReservation.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity<Object> getAllPatients(){
        try {
            List<Patient> patientList = patientService.getAllPatient();
            return ResponseEntity.status(HttpStatus.OK).body(patientList);
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
            List<Patient> patients = patientService.getPatient(fullName, page);
            return ResponseEntity.status(HttpStatus.OK).body(patients);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping(value ={
            "/pages",
            "/pages/name={fullName}"
    })
    public ResponseEntity<Object> getTotalPage(@PathVariable(required = false) String fullName){
        fullName = (fullName == null) ? "" : fullName;
        try {
            Long totalPages = patientService.getTotalPages(fullName);
            return ResponseEntity.status(HttpStatus.OK).body(totalPages);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable(required = true) Long id){
        try {
            UpsertPatientDTO dto = patientService.findUpsertPatientById(id);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }


    @PostMapping
    public ResponseEntity<Object> post(@RequestBody UpsertPatientDTO dto){
        try {
            patientService.savePatient(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Data Patient berhasil ditambahkan");
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PutMapping({"/id={patientId}"})
    public ResponseEntity<Object> put(@RequestBody Patient patient, @PathVariable Long patientId){
        try {
            UpsertPatientDTO findPatient = patientService.findUpsertPatientById(patientId);
                if(findPatient != null) {
                    patientService.updateById(patient, patientId);
                }
            return ResponseEntity.status(HttpStatus.CREATED).body("Data Patient berhasil diupdate");
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @DeleteMapping("/id={id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Long id){
        try {
            patientService.deletePatient(id);
            return ResponseEntity.status(HttpStatus.OK).body("Data Doctor berhasil dihapus");
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping("/addMultiplePatients")
    public ResponseEntity<Object> addMultiplePatients(@RequestBody ListPatientDTO patientDTOS){
        try{
            List<Patient> patients = patientService.addMultiplePatients(patientDTOS);
            return new ResponseEntity<>(patients, HttpStatus.CREATED);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

}

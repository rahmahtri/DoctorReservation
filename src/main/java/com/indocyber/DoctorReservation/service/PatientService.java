package com.indocyber.DoctorReservation.service;

import com.indocyber.DoctorReservation.dto.patient.ListPatientDTO;
import com.indocyber.DoctorReservation.dto.patient.UpsertPatientDTO;
import com.indocyber.DoctorReservation.entity.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> getPatient(String fullName, Integer page);

    public Long getTotalPages(String fullName);

    UpsertPatientDTO findUpsertPatientById(Long id);

    public Long savePatient(UpsertPatientDTO dto);

    List<Patient> getAllPatient();

    void updateById(Patient patient, Long patientId);

    void deletePatient(Long id);

    List<Patient> addMultiplePatients(ListPatientDTO patientDTOS);
}

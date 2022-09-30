package com.indocyber.DoctorReservation.service;

import com.indocyber.DoctorReservation.dto.doctor.ListDoctorDTO;
import com.indocyber.DoctorReservation.dto.doctor.UpsertDoctorDTO;
import com.indocyber.DoctorReservation.entity.Doctor;

import java.util.List;

public interface DoctorService{
    List<Doctor> getAllDoctors();

    List<Doctor> getDoctor(String fullName, Integer page);

    UpsertDoctorDTO findDoctorById(Long id);

    public Long saveDoctor(UpsertDoctorDTO dto);

    public void updateById(Doctor doctor, Long doctorId);

    public void deleteDoctor(Long id);

    List<Doctor> addMultipleDoctors(ListDoctorDTO doctorDTOS);
}

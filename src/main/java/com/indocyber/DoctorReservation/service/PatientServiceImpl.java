package com.indocyber.DoctorReservation.service;

import com.indocyber.DoctorReservation.dto.patient.ListPatientDTO;
import com.indocyber.DoctorReservation.dto.patient.UpsertPatientDTO;
import com.indocyber.DoctorReservation.entity.Patient;
import com.indocyber.DoctorReservation.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService{

    @Autowired
    private PatientRepository patientRepository;

    private final int rowsInPage = 2;

    @Override
    public List<Patient> getPatient(String fullName, Integer page) {
        Pageable pagination = PageRequest.of(page-1, rowsInPage, Sort.by("id"));
        List<Patient> patients = patientRepository.findAll(fullName, pagination);
        return patients;
    }

    @Override
    public Long getTotalPages(String fullName) {
        double totalData = (double)(patientRepository.count(fullName));
        long totalPage = (long)(Math.ceil(totalData/rowsInPage));
        return totalPage;
    }

    @Override
    public UpsertPatientDTO findUpsertPatientById(Long id) {
        Optional<Patient> nullableEntity = patientRepository.findById(id);
        Patient entity = nullableEntity.get();

        UpsertPatientDTO dto = new UpsertPatientDTO(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getGender(),
                entity.getBirthDate(),
                entity.getDisease(),
                entity.getContactPhone(),
                entity.getEmail(),
                entity.getAddress()
        );

        return dto;
    }

    @Override
    public Long savePatient(UpsertPatientDTO dto) {
        Patient entity = new Patient(
                dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getGender(),
                dto.getBirthDate(),
                dto.getDisease(),
                dto.getContactPhone(),
                dto.getEmail(),
                dto.getAddress()
        );
        Patient respond = patientRepository.save(entity);
        return respond.getId();
    }

    @Override
    public List<Patient> getAllPatient() {
        return patientRepository.findAll();
    }

    @Override
    public void updateById(Patient patient, Long patientId) {
        patient.setId(patientId);
        patientRepository.save(patient);
    }

    @Override
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    @Override
    public List<Patient> addMultiplePatients(ListPatientDTO patientDTOS) {

        List<Patient> patients = patientDTOS.getPatientDTOS()
                .stream()
                .map(p -> {
                    return new Patient(
                            p.getId(),
                            p.getFirstName(),
                            p.getLastName(),
                            p.getGender(),
                            p.getBirthDate(),
                            p.getDisease(),
                            p.getContactPhone(),
                            p.getEmail(),
                            p.getAddress());
                })
                .collect(Collectors.toList());

        return patientRepository.saveAll(patients);
    }


}

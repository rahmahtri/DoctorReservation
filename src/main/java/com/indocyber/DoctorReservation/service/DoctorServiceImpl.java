package com.indocyber.DoctorReservation.service;

import com.indocyber.DoctorReservation.dto.doctor.ListDoctorDTO;
import com.indocyber.DoctorReservation.dto.doctor.UpsertDoctorDTO;
import com.indocyber.DoctorReservation.entity.Doctor;
import com.indocyber.DoctorReservation.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService{

    @Autowired
    private DoctorRepository doctorRepository;

    private final int rowsInPage = 2;

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public List<Doctor> getDoctor(String fullName, Integer page) {
        Pageable pagination = PageRequest.of(page-1, rowsInPage, Sort.by("id"));
        List<Doctor> doctors = doctorRepository.findAllDoctor(fullName, pagination);
        return doctors;
    }

    @Override
    public UpsertDoctorDTO findDoctorById(Long id) {
        Optional<Doctor> nullableEntity = doctorRepository.findById(id);
        Doctor entity = nullableEntity.get();
        UpsertDoctorDTO doctorDTO = new UpsertDoctorDTO(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getDegree(),
                entity.getContactPhone(),
                entity.getEmail(),
                entity.getAddress()
        );
        return doctorDTO;
    }

    @Override
    public Long saveDoctor(UpsertDoctorDTO dto) {
        Doctor entity = new Doctor(
                dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getDegree(),
                dto.getContactPhone(),
                dto.getEmail(),
                dto.getAddress()
        );

        Doctor respond = doctorRepository.save(entity);
        return respond.getId();
    }

    @Override
    public void updateById(Doctor doctor, Long doctorId) {
        doctor.setId(doctorId);
        doctorRepository.save(doctor);
    }

    @Override
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    @Override
    public List<Doctor> addMultipleDoctors(ListDoctorDTO doctorDTOS) {

        List<Doctor> doctors = doctorDTOS.getDoctorDTOS()
                .stream()
                .map(doc -> {
                    return new Doctor(
                            doc.getId(),
                            doc.getFirstName(),
                            doc.getLastName(),
                            doc.getDegree(),
                            doc.getContactPhone(),
                            doc.getEmail(),
                            doc.getAddress());
                })
                .collect(Collectors.toList());

        return doctorRepository.saveAll(doctors);
    }
}

package com.indocyber.DoctorReservation.repository;

import com.indocyber.DoctorReservation.entity.Patient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query(value = "Select * From Patient pat where concat(pat.firstName, ' ', pat.lastName) like %:fullName%", nativeQuery = true)
    List<Patient> findAll(@Param("fullName") String fullName,
                          Pageable pagination);

    @Query("""
            Select Count(concat(pat.firstName, ' ', pat.lastName))
            From Patient as pat
            Where concat(pat.firstName, ' ', pat.lastName) like %:fullName% 
            """)
    public Long count(@Param("fullName") String fullName);
}

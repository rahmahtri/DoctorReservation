package com.indocyber.DoctorReservation.repository;

import com.indocyber.DoctorReservation.entity.Doctor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query(value = "Select * From Doctor as doc Where concat(doc.firstName, ' ', doc.lastName) like %:fullName%", nativeQuery = true)
    List<Doctor> findAllDoctor(@Param("fullName") String fullName,
                               Pageable pagination);
}

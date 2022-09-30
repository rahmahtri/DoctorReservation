package com.indocyber.DoctorReservation.repository;

import com.indocyber.DoctorReservation.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,String> {
}

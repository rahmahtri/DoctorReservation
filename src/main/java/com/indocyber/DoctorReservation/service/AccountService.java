package com.indocyber.DoctorReservation.service;

import com.indocyber.DoctorReservation.dto.account.InsertAccountDTO;

public interface AccountService {

    String getAccountRole(String username);

    void addAccount(InsertAccountDTO dto);
}

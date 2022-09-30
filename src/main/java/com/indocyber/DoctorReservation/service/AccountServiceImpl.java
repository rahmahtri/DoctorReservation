package com.indocyber.DoctorReservation.service;

import com.indocyber.DoctorReservation.ApplicationUserDetails;
import com.indocyber.DoctorReservation.configuration.RestSecurityConfig;
import com.indocyber.DoctorReservation.dto.account.InsertAccountDTO;
import com.indocyber.DoctorReservation.entity.Account;
import com.indocyber.DoctorReservation.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public String getAccountRole(String username) {
        Optional<Account> accountOptional = accountRepository.findById(username);

        Account tempAccount = null;
        if(accountOptional.isPresent()){
            tempAccount = accountOptional.get();
        }
        return tempAccount.getRole();
    }

    @Override
    public void addAccount(InsertAccountDTO dto) {
        PasswordEncoder passwordEncoder = RestSecurityConfig.passwordEncoder();
        String hashPassword = passwordEncoder.encode(dto.getPassword());

        Account account = new Account(
                dto.getUsername(),
                hashPassword,
                dto.getRole()
        );

        accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Optional<Account> optionalAccount = accountRepository.findById(username);

            Account tempAccount = null;
            if(optionalAccount.isPresent()){
                tempAccount = optionalAccount.get();
            }

            return new ApplicationUserDetails(tempAccount);
        }
        //method return yaitu userDetails, namun karena interface jadi yg di-return kelas yg mengimplemen userdetails tersebut

}

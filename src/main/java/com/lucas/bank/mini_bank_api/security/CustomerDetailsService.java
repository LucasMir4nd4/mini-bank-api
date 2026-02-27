package com.lucas.bank.mini_bank_api.security;

import com.lucas.bank.mini_bank_api.domain.entity.customer.CustomerDetails;
import com.lucas.bank.mini_bank_api.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {

        var customer = customerRepository.findByCpf(cpf)
                .orElseThrow(()-> new UsernameNotFoundException("CPF NOT FOUND"));

        return new CustomerDetails(customer);
    }
}

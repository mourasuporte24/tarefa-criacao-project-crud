package com.contech.customercard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contech.customercard.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}

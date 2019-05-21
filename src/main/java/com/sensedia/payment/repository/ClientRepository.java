package com.sensedia.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sensedia.payment.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

}

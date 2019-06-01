package com.sensedia.payment.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sensedia.payment.entity.CustomerEntity;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

	Optional<CustomerEntity> findByDocument(String document);
	
	Optional<CustomerEntity> findByPhone(String phone);

}

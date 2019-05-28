package com.sensedia.payment.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sensedia.payment.entity.InstallmentEntity;

@Repository
public interface InstallmentRepository extends CrudRepository<InstallmentEntity, UUID>  {
	
}

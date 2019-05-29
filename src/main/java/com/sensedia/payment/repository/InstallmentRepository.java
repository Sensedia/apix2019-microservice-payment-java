package com.sensedia.payment.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sensedia.payment.entity.InstallmentEntity;
import com.sensedia.payment.enumeration.InstallmentStatus;

@Repository
public interface InstallmentRepository extends CrudRepository<InstallmentEntity, UUID>  {

	List<InstallmentEntity> findByDebitIdAndStatusOrderByExpirationDateAsc(UUID id, InstallmentStatus pending);
	
}

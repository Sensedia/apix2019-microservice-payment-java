package com.sensedia.payment.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sensedia.payment.domain.ClientEntity;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, UUID> {

}

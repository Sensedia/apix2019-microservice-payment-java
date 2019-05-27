package com.sensedia.payment.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.sensedia.payment.entity.DebitEntity;

@Repository
public interface DebitRepository extends CrudRepository<DebitEntity,UUID>  {
  
  public List<DebitEntity> findByClientId(UUID clientId);

}

package com.sensedia.payment.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.sensedia.payment.entity.DebitEntity;

@Repository
public interface DebitRepository extends CrudRepository<DebitEntity,UUID>  {
  
  /**
   * Finds persons by using the last name as a search criteria.
   * @param lastName  
   * @return  A list of persons which last name is an exact match with the given last name.
   *          If no persons is found, this method returns an empty list.
   */
  public List<DebitEntity> findByClientId(String clientId);

}

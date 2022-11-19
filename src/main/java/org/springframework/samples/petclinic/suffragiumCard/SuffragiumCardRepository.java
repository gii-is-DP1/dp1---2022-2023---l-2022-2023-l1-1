package org.springframework.samples.petclinic.suffragiumCard;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuffragiumCardRepository extends CrudRepository<SuffragiumCard, Integer> {
    
}

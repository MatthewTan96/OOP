package com.IS442.teamsixtester.repositories.Domain;

import com.IS442.teamsixtester.model.Domain.Domain;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface DomainRepository extends CrudRepository<Domain, String>{

}

package com.demo.nace.service.data;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface NaceRepository extends CrudRepository<NaceRecord,Long> {

  Optional<NaceRecord> findByOrder(Long order);

}

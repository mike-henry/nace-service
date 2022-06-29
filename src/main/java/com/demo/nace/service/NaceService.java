package com.demo.nace.service;

import com.demo.nace.service.data.NaceRecord;
import com.demo.nace.service.data.NaceRepository;
import org.springframework.stereotype.Service;

@Service
public class NaceService {

  final NaceRepository repository;

  public NaceService(NaceRepository repository) {
    this.repository = repository;
  }

  public NaceRecord getRecordByOrder(Long order) {
     return repository.findByOrder(order)
         .orElseThrow( ()-> new OrderNumberNotFoundException(order) );
  }

  public void saveNace(NaceRecord naceRecord) {
    repository.save(naceRecord);
  }
}

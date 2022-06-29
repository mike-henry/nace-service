package com.demo.nace.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.demo.nace.service.data.NaceRecord;
import com.demo.nace.service.data.NaceRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NaceServiceTest {

  private NaceService subject;

  @Mock
  private NaceRepository mockRepository;

  @BeforeEach
  void initialize(){
    subject = new NaceService(mockRepository);
  }

  @Test
  @DisplayName("a search by existing order Number returns an order")
  void findExistingOrderTest(){
    Long sampleOrderNumber = 398481L;
    String sampleId = "999";
    NaceRecord dummyRecord = new NaceRecord();
    dummyRecord.setOrder(sampleOrderNumber);
    dummyRecord.setId(sampleId);
    when(mockRepository.findByOrder(eq(dummyRecord.getOrder()))).thenReturn(Optional.of(dummyRecord));
    NaceRecord result = subject.getRecordByOrder(sampleOrderNumber);
    assertThat(result.getId(),equalTo(sampleId));
  }

  @Test
  @DisplayName("a search by non existing order Number throws an Exception")
  void findNonExistingOrderTest(){
    Long sampleOrderNumber = 398481L;
    when(mockRepository.findByOrder(eq(sampleOrderNumber))).thenReturn(Optional.empty());
    OrderNumberNotFoundException thrown = Assertions.assertThrows(OrderNumberNotFoundException.class, () -> {
      subject.getRecordByOrder(sampleOrderNumber);
    });
    assertThat(thrown.getMessage(),equalTo("Order 398481 not found"));
  }

  @Test
  @DisplayName("A save  results  ion call to save in the repository")
  void saveOrderTest(){
    Long sampleOrderNumber = 398481L;
    String sampleId = "999";
    NaceRecord dummyRecord = new NaceRecord();
    dummyRecord.setOrder(sampleOrderNumber);
    dummyRecord.setId(sampleId);
    ArgumentCaptor<NaceRecord> saved = ArgumentCaptor.forClass(NaceRecord.class);
    subject.saveNace(dummyRecord);
    verify(mockRepository).save(saved.capture());
    assertThat(saved.getValue().getId(),equalTo(sampleId));
  }

}

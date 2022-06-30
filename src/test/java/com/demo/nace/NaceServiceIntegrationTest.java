package com.demo.nace;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.demo.nace.service.NaceServiceController;
import com.demo.nace.service.data.NaceRecord;
import com.demo.nace.service.data.NaceRepository;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

@SpringBootTest
@Slf4j
public class NaceServiceIntegrationTest {

  @Autowired
  private NaceRepository repository;  // for preparation only
  @Value("classpath:/NACE_REV2.csv")
  private Resource resourceFile;     //for preparation only
  private boolean dataLoaded = false;

  @Autowired
  private NaceServiceController frontEnd;

  @BeforeEach
  void init() throws IOException {
    if (!dataLoaded) {
      loadData();
      dataLoaded = true;
    }
  }

  @Test
  @DisplayName("Simple Get Reads Information From Data Base")
  void simpleGetCheck(){
    NaceRecord record = frontEnd.getCount(398481L);
    assertThat(record.getDescription(),equalTo("AGRICULTURE, FORESTRY AND FISHING"));
  }

  private void loadData() throws IOException {
    Reader in = new FileReader(resourceFile.getFile());
    Iterable<CSVRecord> lines = CSVFormat.EXCEL.withFirstRecordAsHeader()
        .parse(in);
    for (CSVRecord line : lines) {
      NaceRecord record = new NaceRecord();
      record.setOrder(Long.parseLong(line.get("Order")));
      record.setCode(line.get("Code"));
      record.setLevel(line.get("Level"));
      record.setParent(line.get("Parent"));
      record.setDescription(line.get("Description"));
      record.setIncluded(line.get("This item includes"));
      record.setAlsoIncluded(line.get("This item also includes"));
      record.setRulings(line.get("Rulings"));
      record.setExcluded(line.get("This item excludes"));
      record.setReference(line.get("Reference to ISIC Rev. 4"));
      log.debug("Test Preparation - order [{}], Level [{}] Code [{}] included [{}] ISIC Rev. 4 reference [{}]",record.getOrder(),record.getLevel(),record.getCode(),record.getIncluded(), record.getReference());
      repository.save(record);
    }
  }
}

package com.demo.nace.service.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Data;


@Data
@Entity(name="NACE_RECORD")
public class NaceRecord {

  @Id
  @Column(name = "record_id")
  @JsonIgnore
  private String id = UUID.randomUUID().toString();

  @Column(unique=true,name="order_num")
  private Long order;
  private String level;
  private String code;
  private String parent;
  @Lob
  private String description;
  @Lob
  private String included;
  @Lob
  private String alsoIncluded;
  @Lob
  private String rulings;
  @Lob
  private String excluded;
  private String reference;

}

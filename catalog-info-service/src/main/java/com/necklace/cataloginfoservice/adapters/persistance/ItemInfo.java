package com.necklace.cataloginfoservice.adapters.persistance;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class ItemInfo {

  @Id
  private String itemInfoId;
  private String name;
  private String category;
  private List<String> tags;
  private String description;
  private Double price;

}

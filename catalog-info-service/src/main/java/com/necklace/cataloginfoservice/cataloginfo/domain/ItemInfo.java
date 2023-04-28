package com.necklace.cataloginfoservice.cataloginfo.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemInfo {

  private String name;
  private String category;
  private List<String> tags;
  private String description;
  private Double price;
}

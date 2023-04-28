package com.necklace.cataloginfoservice.adapters.persistance.entity;

import com.necklace.cataloginfoservice.cataloginfo.domain.ItemInfo;
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
public class ItemInfoEntity {

  @Id
  private String itemInfoId;
  private String name;
  private String category;
  private List<String> tags;
  private String description;
  private Double price;

  public static ItemInfoEntity fromDomain(ItemInfo itemInfo) {
    return new ItemInfoEntity(null, itemInfo.getName(), itemInfo.getCategory(), itemInfo.getTags(),
        itemInfo.getDescription(), itemInfo.getPrice());
  }

  public ItemInfo toDomain() {
    return new ItemInfo(getName(), getCategory(), getTags(), getDescription(), getPrice());
  }
}

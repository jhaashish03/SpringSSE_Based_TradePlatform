package com.practice.customerservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table
@Setter@Getter@AllArgsConstructor@NoArgsConstructor
public class Holding {


  @Id
  private UUID holdingId;
  private String ticker;
  private Integer quantity;
  private String customerId;
}

package com.practice.customerservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("customer")
@Setter
@Getter@AllArgsConstructor@NoArgsConstructor
public class Customer {

  @Id
  private UUID customerId;
  private String name;
  private String email;

  private Double portfolioBalance;
  private Double availableBalance;
}

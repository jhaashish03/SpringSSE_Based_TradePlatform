package com.practice.customerservice.mapper;

import com.practice.customerservice.entities.Customer;
import com.practice.customerservice.entities.Holding;
import dto.CustomerProfile;
import dto.HoldingDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

  @Mapping(target = "holdings", source = "holdingFlux")
  CustomerProfile mapCustomer(Customer customer, List<Holding> holdingFlux);

  List<HoldingDetail> mapHoldings(List<Holding> holdings);
  HoldingDetail mapHolding(Holding holding);

  @Mapping(target = "portfolioBalance", ignore = true)
  @Mapping(target = "customerId", ignore = true)
  @Mapping(target = "availableBalance", source = "availableBalance")
  Customer mapCreateCustomer(dto.CreateCustomerRequest createCustomerRequest);
}

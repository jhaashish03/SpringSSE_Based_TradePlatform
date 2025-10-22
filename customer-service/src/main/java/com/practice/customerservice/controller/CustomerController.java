package com.practice.customerservice.controller;

import com.practice.customerservice.service.CustomerService;
import dto.CreateCustomerRequest;
import dto.CustomerProfile;
import dto.TradeRequest;
import dto.TradeResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping("/profile/{customerId}")
  public Mono<CustomerProfile> getCustomerProfile(@PathVariable(value = "customerId", required = false) String customerProfileId) {
    return customerService.getCustomer(customerProfileId);
  }
  @PostMapping("/trade")
  public Mono<TradeResponse> trade(@RequestBody TradeRequest tradeRequest){
    return customerService.trade(tradeRequest);
  }
  @PostMapping("/create")
  public Mono<CustomerProfile> createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest){
    return customerService.createCustomer(createCustomerRequest);
  }
}

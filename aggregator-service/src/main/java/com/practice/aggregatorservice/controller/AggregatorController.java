package com.practice.aggregatorservice.controller;

import com.practice.aggregatorservice.service.AggregatorService;
import dto.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/aggregator")
public class AggregatorController {

  private AggregatorService aggregatorService;
  public AggregatorController(AggregatorService aggregatorService) {
    this.aggregatorService = aggregatorService;
  }

  @GetMapping(value = "/stocksPriceUpdate",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<StockPriceUpdate> getStockPriceStream(){
    return aggregatorService.getStockPriceStream();
  }
  @PostMapping(value = "/newCustomer")
  public Mono<CustomerProfile> createNewCustomer(@RequestBody CreateCustomerRequest createCustomerRequest){
    return aggregatorService.createNewCustomer(createCustomerRequest);
  }
  @GetMapping("/{customerId}")
  public Mono<CustomerProfile> getCustomerProfile(@PathVariable String customerId){
    return aggregatorService.getCustomerDetails(customerId);
  }
  @PostMapping("/{customerId}/trade")
  public Mono<TradeResponse> createTrade(@PathVariable("customerId") String customerId, @RequestBody TradeRequest tradeRequest){
    tradeRequest.setCustomerId(customerId);
    return aggregatorService.trade(tradeRequest);
  }

}

package com.practice.aggregatorservice.service;

import dto.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AggregatorService {

  private WebClient stockServiceWebClient;

  private WebClient  customerSerivceWebClient;

  public AggregatorService(@Qualifier("stockService") WebClient stockServiceWebClient,@Qualifier("customerService") WebClient customerSerivceWebClient) {
    this.stockServiceWebClient = stockServiceWebClient;
    this.customerSerivceWebClient = customerSerivceWebClient;
  }

  public Flux<StockPriceUpdate> getStockPriceStream() {
   return stockServiceWebClient.get()
        .uri("/stocks/priceStream")
         .header("Accept", MediaType.APPLICATION_NDJSON_VALUE)
        .retrieve()
        .bodyToFlux(StockPriceUpdate.class);
  }

  public Mono<CustomerProfile> createNewCustomer(CreateCustomerRequest createCustomerRequest) {
    return customerSerivceWebClient.post()
        .uri("/customers/create")
        .bodyValue(createCustomerRequest)
        .retrieve().bodyToMono(CustomerProfile.class);
  }
  public Mono<CustomerProfile> getCustomerDetails(String customerId) {
    return customerSerivceWebClient.get()
        .uri("/customers/profile/{customerId}",customerId)
        .retrieve().bodyToMono(CustomerProfile.class);
  }
  public Mono<TradeResponse> trade(TradeRequest tradeRequest) {
    return customerSerivceWebClient.post()
        .uri("/customers/trade")
        .bodyValue(tradeRequest)
        .retrieve().bodyToMono(TradeResponse.class);
  }
}

package com.practice.customerservice.service;

import com.practice.customerservice.entities.Customer;
import com.practice.customerservice.entities.Holding;
import com.practice.customerservice.mapper.CustomerMapper;
import com.practice.customerservice.repositories.CustomerRepository;
import com.practice.customerservice.repositories.HoldingRepository;
import dto.CreateCustomerRequest;
import dto.CustomerProfile;
import dto.TradeRequest;
import dto.TradeResponse;
import exceptions.CustomerNotFoundException;
import exceptions.TradeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class CustomerService {

  private final CustomerMapper customerMapper;
  private CustomerRepository customerRepository;
  private HoldingRepository holdingRepository;

  public CustomerService(
      CustomerRepository customerRepository, HoldingRepository holdingRepository,
      CustomerMapper customerMapper
  ) {
    this.customerRepository = customerRepository;
    this.holdingRepository = holdingRepository;
    this.customerMapper = customerMapper;
  }

  public Mono<CustomerProfile> getCustomer(String customerId) {
    var customer = customerRepository.findByCustomerId(UUID.fromString(customerId)).switchIfEmpty(
        Mono.error(new CustomerNotFoundException(
            String.format("Customer Not Found with id %s", customerId))));
    return customer.flatMap(c->holdingRepository.findByCustomerId(String.valueOf(c.getCustomerId())).collectList().map(list->customerMapper.mapCustomer(c,list)));

  }

  @Transactional
  public Mono<TradeResponse> trade(TradeRequest tradeRequest) {
    var customer = customerRepository.findByCustomerId(UUID.fromString(tradeRequest.getCustomerId()))
        .switchIfEmpty(Mono.error(new CustomerNotFoundException(
            String.format("Customer Not Found with id %s", tradeRequest.getCustomerId()))));

    var holding = customer.flatMap(c->holdingRepository.findByTickerAndCustomerId(tradeRequest.getTicker().toString(),
        String.valueOf(c.getCustomerId()))
    );
    switch (tradeRequest.getTradeAction()) {
      case BUY -> {
        return holding.defaultIfEmpty(new Holding())
            .zipWith(customer)
            .flatMap(tuple -> executeBuy(tradeRequest, tuple.getT2(), tuple.getT1()));
      }
      case SELL -> {
        return holding.switchIfEmpty(Mono.error(new TradeException("No Holding Found for Ticker " + tradeRequest.getTicker()))).zipWith(customer)
            .flatMap(tuple -> executeSell(tradeRequest, tuple.getT2(), tuple.getT1()));
      }
    }
    return Mono.empty();
  }

  private Mono<TradeResponse> executeSell(
      TradeRequest tradeRequest, Customer customer, Holding holding) {
    if (holding == null) {
      return Mono.error(
          new TradeException("No Holding Found for Ticker " + tradeRequest.getTicker()));
    } else {
      if (holding.getQuantity() < tradeRequest.getQuantity()) {
        return Mono.error(
            new TradeException("Insufficient Holding for Ticker " + tradeRequest.getTicker()));
      } else {
        customer.setPortfolioBalance(customer.getPortfolioBalance() - (tradeRequest.getPrice()
            * tradeRequest.getQuantity()));
        customer.setAvailableBalance(customer.getAvailableBalance() + (tradeRequest.getPrice()
            * tradeRequest.getQuantity()));
        holding.setQuantity(holding.getQuantity() - tradeRequest.getQuantity());
        return  Mono.when(
            customerRepository.save(customer),
            holdingRepository.save(holding)
        ).then(Mono.fromSupplier(()->TradeResponse.builder().customerId(tradeRequest.getCustomerId())
            .availableBalance(customer.getAvailableBalance())
            .portfolioBalance(customer.getPortfolioBalance())
            .quantity(tradeRequest.getQuantity())
            .ticker(tradeRequest.getTicker())
            .build()));
      }
    }
  }

  public Mono<TradeResponse> executeBuy(
      TradeRequest tradeRequest, Customer customer, Holding holding) {
    if( customer.getAvailableBalance()
        < tradeRequest.getPrice() * tradeRequest.getQuantity()){
      return Mono.error(new TradeException("Insufficient Balance"));
    }

    customer.setPortfolioBalance(
        (customer.getPortfolioBalance()==null?0:customer.getPortfolioBalance()) + (tradeRequest.getPrice() * tradeRequest.getQuantity()));
    customer.setAvailableBalance(
        customer.getAvailableBalance() - (tradeRequest.getPrice() * tradeRequest.getQuantity()));
   Mono<Holding> saveHolding;
    if (holding.getCustomerId()==null) {
      holding = new Holding();
      holding.setCustomerId(String.valueOf(customer.getCustomerId()));
      holding.setQuantity(tradeRequest.getQuantity());
      holding.setTicker(tradeRequest.getTicker().toString());
      saveHolding=holdingRepository.save(holding);
    } else {
      holding.setQuantity((holding.getQuantity()==null?0:holding.getQuantity()) + tradeRequest.getQuantity());
      saveHolding=holdingRepository.save(holding);
    }
    return Mono.when(saveHolding,customerRepository.save(customer)).then(Mono.fromSupplier(()->TradeResponse.builder().customerId(tradeRequest.getCustomerId())
        .availableBalance(customer.getAvailableBalance())
        .portfolioBalance(customer.getPortfolioBalance())
        .quantity(tradeRequest.getQuantity())
        .ticker(tradeRequest.getTicker())
        .build()));
  }

  public Mono<CustomerProfile> createCustomer(CreateCustomerRequest createCustomerRequest) {
    Customer customer = customerMapper.mapCreateCustomer(createCustomerRequest);
    customer.setCustomerId(UUID.randomUUID());
    return customerRepository.save(customer).map(customer1 -> customerMapper.mapCustomer(customer1,null));
  }
}

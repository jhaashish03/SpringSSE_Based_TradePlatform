package com.practice.customerservice.repositories;

import com.practice.customerservice.entities.Holding;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface HoldingRepository extends ReactiveCrudRepository<Holding, UUID> {

  Mono<Holding> findByTickerAndCustomerId(String ticker, String customerId);

  Flux<Holding> findByCustomerId(String customerId);
}

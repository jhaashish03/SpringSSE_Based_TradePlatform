package com.practice.stockservice.configuration;


import dto.StockPriceUpdate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

import java.util.List;

@Configuration
public class StocksPriceConfig {

  @Bean
  public Sinks.Many<StockPriceUpdate> sinks(){
    return Sinks.many().multicast().onBackpressureBuffer();
  }
}

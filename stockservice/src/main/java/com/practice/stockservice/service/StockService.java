package com.practice.stockservice.service;


import dto.StockPriceUpdate;
import enums.Ticker;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class StockService {

  private Sinks.Many<StockPriceUpdate> sink;

  public StockService(Sinks.Many<StockPriceUpdate> sink) {
    this.sink = sink;
  }

  @Scheduled(fixedDelay = 2000)
  public void updateStockPrice() {
    for (Ticker ticker : Ticker.values()) {
      double num=0;
      switch (ticker){
        case HCLTECH -> {
          num= ThreadLocalRandom.current().nextDouble(1700,1735);
          break;
        }
        case TCS ->  {
          num= ThreadLocalRandom.current().nextDouble(4000,4100);
        break;
        }
        case ITC ->   {
          num= ThreadLocalRandom.current().nextDouble(500,525);
          break;
        }
        case INFOSYS ->   {
          num= ThreadLocalRandom.current().nextDouble(1650,1700);
          break;
        }
      }
      sink.tryEmitNext(new StockPriceUpdate(ticker, num));
    }
  }
  public Flux<StockPriceUpdate> getStockPriceStream(){
    return sink.asFlux();
  }
}

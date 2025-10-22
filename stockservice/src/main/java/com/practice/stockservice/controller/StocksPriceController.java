package com.practice.stockservice.controller;


import com.practice.stockservice.service.StockService;
import dto.StockPriceUpdate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/stocks")
public class StocksPriceController {

  private final StockService stockService;

  public StocksPriceController(StockService stockService) {
    this.stockService = stockService;
  }

  @GetMapping(value = "/priceStream",produces = MediaType.APPLICATION_NDJSON_VALUE)
  public Flux<StockPriceUpdate> getStockPriceStream(){
    return stockService.getStockPriceStream();
  }
}

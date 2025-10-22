package com.practice.stockservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StockserviceApplication {

  public static void main(String[] args) {
    SpringApplication.run(StockserviceApplication.class, args);
  }

}

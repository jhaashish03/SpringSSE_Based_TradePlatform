package dto;

import enums.Ticker;
import lombok.Builder;
import lombok.Data;

@Data@Builder
public class TradeResponse {

  private Ticker ticker;
  private String customerId;
  private Integer quantity;
  private Double availableBalance;
  private Double portfolioBalance;
}

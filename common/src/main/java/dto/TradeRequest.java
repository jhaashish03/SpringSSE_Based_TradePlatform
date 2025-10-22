package dto;

import enums.Ticker;
import enums.TradeAction;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class TradeRequest {

  private Ticker ticker;
  private TradeAction tradeAction;
  @Min(1)
  private Integer quantity;
  private Double price;
  private String customerId;
}

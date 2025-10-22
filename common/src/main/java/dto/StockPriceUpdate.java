package dto;


import enums.Ticker;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter@Getter@AllArgsConstructor@NoArgsConstructor
public class StockPriceUpdate {

  private Ticker ticker;
  private double price;

}

package dto;

import enums.Ticker;
import lombok.*;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class HoldingDetail {

  private Ticker ticker;
  private Integer quantity;
}

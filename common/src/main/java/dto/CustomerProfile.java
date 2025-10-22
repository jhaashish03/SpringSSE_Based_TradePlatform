package dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerProfile {

  private String customerId;
  private String name;
  private String email;
  private Double portfolioBalance;
  private Double availableBalance;
  private List<HoldingDetail> holdings;
}

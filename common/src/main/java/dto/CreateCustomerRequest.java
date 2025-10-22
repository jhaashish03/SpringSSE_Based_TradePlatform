package dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerRequest {

  private String name;
  private String email;
  private Double availableBalance;
}

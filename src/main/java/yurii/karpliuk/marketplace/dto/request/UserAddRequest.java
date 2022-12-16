package yurii.karpliuk.marketplace.dto.request;

import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
public class UserAddRequest {
    @NotNull(message = "First name is required")
    private String firstName;
    @NotNull(message = "Last name is required")
    private String lastName;
    @Positive
    private BigDecimal amountOfMoney;
}

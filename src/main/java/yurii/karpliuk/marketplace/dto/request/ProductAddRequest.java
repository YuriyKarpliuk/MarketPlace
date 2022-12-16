package yurii.karpliuk.marketplace.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Setter
@Getter
public class ProductAddRequest {
    @NotNull(message = "Name is required")
    private String name;
    @Positive
    private BigDecimal price;
}

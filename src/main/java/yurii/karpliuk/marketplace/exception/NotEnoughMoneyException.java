package yurii.karpliuk.marketplace.exception;

import lombok.Getter;

@Getter
public class NotEnoughMoneyException extends Exception{
    public NotEnoughMoneyException() {
        super("Not enough money to buy this product!");
    }
}

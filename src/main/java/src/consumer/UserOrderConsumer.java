package src.consumer;

import src.model.UserOrder;

public interface UserOrderConsumer {
    boolean accept(UserOrder order);
}

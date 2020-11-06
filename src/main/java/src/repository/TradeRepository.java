package src.repository;

import src.model.Order;

public interface TradeRepository<T extends Order> {
    T addOrder(T order);
    T removeOrder(T order);
    T partlyCompleteOrder(T order);
}

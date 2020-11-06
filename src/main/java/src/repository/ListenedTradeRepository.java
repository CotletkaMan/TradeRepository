package src.repository;

import src.model.Order;

import java.util.Collection;
import java.util.function.Consumer;

public class ListenedTradeRepository<T extends Order> implements TradeRepository<T>{
    private final TradeRepository<T> tradeRepository;
    private final Collection<Consumer<T>> listeners;


    public ListenedTradeRepository(TradeRepository<T> tradeRepository, Collection<Consumer<T>> listeners) {
        this.tradeRepository = tradeRepository;
        this.listeners = listeners;
    }

    @Override
    public T addOrder(T order) {
        T newOrder = tradeRepository.addOrder(order);
        listeners.forEach(listener -> listener.accept(newOrder));

        return newOrder;
    }

    @Override
    public T removeOrder(T order) {
        T newOrder = tradeRepository.removeOrder(order);
        listeners.forEach(listener -> listener.accept(newOrder));

        return newOrder;
    }

    @Override
    public T partlyCompleteOrder(T order) {
        T newOrder = tradeRepository.partlyCompleteOrder(order);
        listeners.forEach(listener -> listener.accept(newOrder));

        return newOrder;
    }
}

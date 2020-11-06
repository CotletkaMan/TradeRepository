package src.repository;

import src.model.Order;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class TopTradeRepository implements TradeRepository<Order> {
    //Can be optimized by applying orderedHeapSet collection :: Map<String, Set<Order>>
    private Map<String, TreeMap<Long, Order>> repository = new TreeMap<>(Comparator.naturalOrder());


    @Override
    public Order addOrder(Order order) {
        String key = makeKey(order);
        TreeMap<Long, Order> orders = repository.computeIfAbsent(key, innerKey -> getCollection(order));

        Map.Entry<Long, Order> top = orders.firstEntry();

        Order savedOrder = orders.get(order.getPrice());
        if (savedOrder == null) {
            orders.put(order.getPrice(), new Order(order));
            Order newOrder = orders.firstEntry().getValue();
            return !top.getValue().equals(newOrder) ? newOrder : null;
        } else {
            savedOrder.setAmount(savedOrder.getAmount() + order.getAmount());
            savedOrder.setAmountRest(savedOrder.getAmountRest() + order.getAmountRest());
            return top.getValue().getPrice().equals(order.getPrice()) ? savedOrder : null;
        }
    }

    @Override
    public Order removeOrder(Order order) {
        String key = makeKey(order);
        TreeMap<Long, Order> orders = repository.computeIfAbsent(key, innerKey -> getCollection(order));

        Map.Entry<Long, Order> top = orders.firstEntry();

        Order savedOrder = orders.get(order.getPrice());
        if (savedOrder != null) {
            if (savedOrder.getAmount().equals(order.getAmount())) {
                if (top.getValue().equals(orders.remove(order.getPrice()))) {
                    return orders.firstEntry().getValue();
                } else {
                    return null;
                }

            } else {
                savedOrder.setAmount(savedOrder.getAmount() - order.getAmount());
                savedOrder.setAmountRest(savedOrder.getAmountRest() - order.getAmountRest());
                return top.getValue().equals(savedOrder) ? savedOrder : null;
            }
        }

        return null;
    }

    @Override
    public Order partlyCompleteOrder(Order order) {
        return removeOrder(order);
    }

    private String makeKey(Order order) {
        return order.getInstrumentId() + order.getSide().getCode();
    }

    private TreeMap<Long, Order> getCollection(Order order) {
        switch (order.getSide()) {
            case BUY: return new TreeMap<>(Comparator.<Long>naturalOrder().reversed()) {{
                put(0l, new Order(order.getInstrumentId(), order.getSide(), 0l, 0, 0));
            }};

            case SELL:
            default: return new TreeMap<>(Comparator.naturalOrder()){{
                put(Long.MAX_VALUE, new Order(order.getInstrumentId(), order.getSide(), Long.MAX_VALUE, 0, 0));
            }};
        }
    }
}

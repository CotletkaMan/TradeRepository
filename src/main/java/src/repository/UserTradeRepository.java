package src.repository;

import src.model.UserOrder;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class UserTradeRepository implements TradeRepository<UserOrder>{
    private Map<String, UserOrder> repository = new TreeMap<>(Comparator.naturalOrder());

    @Override
    public UserOrder addOrder(UserOrder order) {
        String key = makeKey(order);
        if (!repository.containsKey(key)) {
            UserOrder newOrder = new UserOrder(order);
            repository.put(key, newOrder);
            return newOrder;
        }

        return null;
    }

    @Override
    public UserOrder removeOrder(UserOrder order) {
        String key = makeKey(order);
        UserOrder lastOrder = repository.get(key);

        if (lastOrder != null && lastOrder.getAmount().equals(order.getAmount())) {
            repository.remove(key);
            return lastOrder;
        }

        return null;
    }

    @Override
    public UserOrder partlyCompleteOrder(UserOrder order) {
        String key = makeKey(order);
        UserOrder lastOrder = repository.get(key);

        if (lastOrder != null && lastOrder.getAmount().compareTo(order.getAmount()) > 0) {
            lastOrder.setAmount(lastOrder.getAmount() - order.getAmount());
            lastOrder.setAmountRest(lastOrder.getAmountRest() - order.getAmountRest());
            return lastOrder;
        }

        return null;
    }

    private String makeKey(UserOrder order) {
        return order.getUserId() + order.getClorderId();
    }


}

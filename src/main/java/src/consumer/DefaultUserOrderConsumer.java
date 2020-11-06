package src.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.model.UserOrder;
import src.repository.TradeRepository;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

public class DefaultUserOrderConsumer implements UserOrderConsumer {
    private final Logger logger = LoggerFactory.getLogger(DefaultUserOrderConsumer.class);
    private final Collection<TradeRepository<? super UserOrder>>  repositories;

    public DefaultUserOrderConsumer(Collection<TradeRepository<? super UserOrder>>  repositories) {
        this.repositories = repositories;
    }

    @Override
    public boolean accept(UserOrder order) {
        switch (order.getAction()) {
            case ADD:
                return repositories.stream().map(repository -> repository.addOrder(order)).allMatch(Objects::nonNull);
            case REMOVE:
                return repositories.stream().map(repository -> repository.removeOrder(order)).allMatch(Objects::nonNull);
            case PARTLY_DONE:
                return repositories.stream().map(repository -> repository.partlyCompleteOrder(order)).allMatch(Objects::nonNull);
            default:
                logger.warn("Such action not supported {}. I will do nothing", order.getAction());
                return false;
        }
    }
}

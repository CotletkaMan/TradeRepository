package src;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.consumer.DefaultUserOrderConsumer;
import src.consumer.UserOrderConsumer;
import src.model.Action;
import src.model.Order;
import src.model.Side;
import src.model.UserOrder;
import src.repository.ListenedTradeRepository;
import src.repository.TopTradeRepository;
import src.repository.UserTradeRepository;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        final UserOrderConsumer userOrderConsumer = prepareUserOrderConsumer(Main::printer);
        getOrders().forEach(userOrderConsumer::accept);
    }

    private static UserOrderConsumer prepareUserOrderConsumer(Consumer<Order> consumer) {
        return new DefaultUserOrderConsumer(
                Arrays.asList(
                        new UserTradeRepository(),
                        new ListenedTradeRepository<>(new TopTradeRepository(), Collections.singletonList(consumer))
                )
        );
    }

    private static Stream<UserOrder> getOrders(){
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            return br.lines()
                    .takeWhile(line -> !line.isEmpty())
                    .map(inputLine -> inputLine.split(";"))
                    .filter(inputArray -> inputArray.length == 8)
                    .map(inputArray ->
                            new UserOrder(
                                    inputArray[0], inputArray[1], Action.getActionByCode(inputArray[2]),
                                    Integer.valueOf(inputArray[3]), Side.getSideByCode(inputArray[4]),
                                    Long.valueOf(inputArray[5]), Integer.valueOf(inputArray[6]),
                                    Integer.valueOf(inputArray[7])
                            )
                    ).onClose(() -> close(br));
        } catch (Exception e){
            logger.error("Reading input data has been failed by :{}", e.getMessage());
            close(br);
        };

        return Stream.empty();
    }

    private static void close(Closeable closeable) {
        try {
            closeable.close();
        } catch (Exception e) {
            logger.error("Error during the closing of connection :{}" + e.getMessage());
        }
    }

    private static void printer(Order order) {
        if (order == null) {
            logger.info("-");
        } else {
            logger.info(order.getInstrumentId() + ";" +
                        order.getSide().getCode() + ";" +
                        order.getPrice() + ";" +
                        order.getAmountRest()
            );
        }
    }
}

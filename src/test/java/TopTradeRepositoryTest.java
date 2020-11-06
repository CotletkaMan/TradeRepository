import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import src.model.Order;
import src.model.Side;
import src.repository.TopTradeRepository;

import java.util.Objects;

public class TopTradeRepositoryTest {
    private final Order firstBuyUserOrder = new Order(55 , Side.getSideByCode("B") , 100L ,2, 2);

    private final Order secondBuyUserOrder = new Order(55 , Side.getSideByCode("B") , 200L ,1, 1);

    private final Order thirdBuyUserOrder = new Order(66 , Side.getSideByCode("B") , 200L ,3, 3);

    private final Order fourBuyUserOrder = new Order(55 , Side.getSideByCode("B") , 100L ,3, 3);

    private final Order firstSellUserOrder = new Order(55 , Side.getSideByCode("S") , 100L ,2, 2);

    private final Order secondSellUserOrder = new Order(55 , Side.getSideByCode("S") , 200L ,1, 1);



    @Test
    void emptyCase() {
        TopTradeRepository topTradeRepository = new TopTradeRepository();
        Assertions.assertNull(topTradeRepository.removeOrder(firstBuyUserOrder), "Trash hides in collection");
    }

    @Test
    void oneTop() {
        TopTradeRepository topTradeRepository = new TopTradeRepository();
        Order topOrder = topTradeRepository.addOrder(firstBuyUserOrder);

        Assertions.assertEquals(topOrder, firstBuyUserOrder, "I am not number one (");
    }

    @Test
    void severalOneTop() {
        TopTradeRepository topTradeRepository = new TopTradeRepository();
        topTradeRepository.addOrder(firstBuyUserOrder);
        Order topOrder = topTradeRepository.addOrder(fourBuyUserOrder);

        Assertions.assertTrue(
                Objects.equals(topOrder.getInstrumentId(), firstBuyUserOrder.getInstrumentId()) &&
                        Objects.equals(topOrder.getPrice(), firstBuyUserOrder.getPrice()) &&
                        Objects.equals(topOrder.getAmountRest(), firstBuyUserOrder.getAmountRest() + fourBuyUserOrder.getAmountRest())
                , "I am not number one ("
        );
    }

    @Test
    void removeOneBuyTop() {
        TopTradeRepository topTradeRepository = new TopTradeRepository();
        topTradeRepository.addOrder(firstBuyUserOrder);
        Order topOrder = topTradeRepository.removeOrder(firstBuyUserOrder);

        Assertions.assertTrue(topOrder.getPrice().equals(0L), "Top still alive");
    }

    @Test
    void overrideBuyTop() {
        TopTradeRepository topTradeRepository = new TopTradeRepository();
        topTradeRepository.addOrder(firstBuyUserOrder);
        Order topOrder = topTradeRepository.addOrder(secondBuyUserOrder);

        Assertions.assertEquals(topOrder, secondBuyUserOrder, "Somebody has more power");
    }

    @Test
    void notOverrideBuyTop() {
        TopTradeRepository topTradeRepository = new TopTradeRepository();
        topTradeRepository.addOrder(secondBuyUserOrder);
        Order topOrder = topTradeRepository.addOrder(firstBuyUserOrder);

        Assertions.assertNull(topOrder, "Is firstBuyUserOrder really more appreciate?");
    }

    @Test
    void severalOverrides() {
        TopTradeRepository topTradeRepository = new TopTradeRepository();
        topTradeRepository.addOrder(firstBuyUserOrder);
        Order topOrder = topTradeRepository.addOrder(thirdBuyUserOrder);

        Assertions.assertEquals(topOrder, thirdBuyUserOrder, "Somebody has more power");

        topOrder = topTradeRepository.addOrder(secondBuyUserOrder);

        Assertions.assertEquals(topOrder, secondBuyUserOrder, "Somebody has more power");
    }

    @Test
    void removeOneOfBuyTop() {
        TopTradeRepository topTradeRepository = new TopTradeRepository();
        topTradeRepository.addOrder(firstBuyUserOrder);
        topTradeRepository.addOrder(secondBuyUserOrder);
        Order topOrder = topTradeRepository.removeOrder(secondBuyUserOrder);

        Assertions.assertEquals(topOrder, firstBuyUserOrder, "Second order still alive");
    }

    @Test
    void removeOneSellTop() {
        TopTradeRepository topTradeRepository = new TopTradeRepository();
        topTradeRepository.addOrder(secondSellUserOrder);
        Order topOrder = topTradeRepository.removeOrder(secondSellUserOrder);

        Assertions.assertTrue(topOrder.getPrice().equals(Long.MAX_VALUE), "Top still alive");
    }

    @Test
    void overrideSellTop() {
        TopTradeRepository topTradeRepository = new TopTradeRepository();
        topTradeRepository.addOrder(secondSellUserOrder);
        Order topOrder = topTradeRepository.addOrder(firstSellUserOrder);

        Assertions.assertEquals(topOrder, firstSellUserOrder, "Somebody has more power");
    }

    @Test
    void notOverrideSellTop() {
        TopTradeRepository topTradeRepository = new TopTradeRepository();
        topTradeRepository.addOrder(firstSellUserOrder);
        Order topOrder = topTradeRepository.addOrder(secondSellUserOrder);

        Assertions.assertNull(topOrder, "Is firstBuyUserOrder really more appreciate?");
    }

    @Test
    void removeOneOfSellTop() {
        TopTradeRepository topTradeRepository = new TopTradeRepository();
        topTradeRepository.addOrder(firstSellUserOrder);
        topTradeRepository.addOrder(secondSellUserOrder);
        Order topOrder = topTradeRepository.removeOrder(firstSellUserOrder);

        Assertions.assertEquals(topOrder, secondSellUserOrder, "Second order still alive");
    }

    @Test
    void partlyComplete() {
        TopTradeRepository topTradeRepository = new TopTradeRepository();
        topTradeRepository.addOrder(fourBuyUserOrder);
        Order topOrder = topTradeRepository.partlyCompleteOrder(firstBuyUserOrder);

        Assertions.assertTrue(
                Objects.equals(topOrder.getInstrumentId(), firstBuyUserOrder.getInstrumentId()) &&
                        Objects.equals(topOrder.getPrice(), firstBuyUserOrder.getPrice()) &&
                        Objects.equals(topOrder.getAmountRest(), fourBuyUserOrder.getAmountRest() - firstBuyUserOrder.getAmountRest())
                , "Part operation is sickness"
        );
    }
}

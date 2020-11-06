import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import src.model.Action;
import src.model.Side;
import src.model.UserOrder;
import src.repository.UserTradeRepository;

public class UserTradeRepositoryTest {
    private final UserOrder firstBuyUserOrder = new UserOrder(
            "A", "1", Action.getActionByCode("0") ,
            55 , Side.getSideByCode("B") , 100L ,2, 2);

    private final UserOrder secondBuyUserOrder = new UserOrder(
            "A", "2", Action.getActionByCode("1") ,
            55 , Side.getSideByCode("B") , 100L ,2, 2);

    private final UserOrder thirdBuyUserOrder = new UserOrder(
            "A", "3", Action.getActionByCode("0") ,
            66 , Side.getSideByCode("B") , 200L ,3, 3);

    @Test
    void emptyCase() {
        UserTradeRepository userTradeRepository = new UserTradeRepository();
        Assertions.assertNull(userTradeRepository.removeOrder(secondBuyUserOrder), "Trash hides in collection");
    }

    @Test
    void oneOrder() {
        UserTradeRepository userTradeRepository = new UserTradeRepository();
        Assertions.assertEquals(firstBuyUserOrder, userTradeRepository.addOrder(firstBuyUserOrder), "Trash hides in collection");
    }

    @Test
    void doubleOrder() {
        UserTradeRepository userTradeRepository = new UserTradeRepository();
        userTradeRepository.addOrder(firstBuyUserOrder);
        Assertions.assertNull(userTradeRepository.addOrder(firstBuyUserOrder), "Can add same order twice");
    }

    @Test
    void removeOrder() {
        UserTradeRepository userTradeRepository = new UserTradeRepository();
        userTradeRepository.addOrder(firstBuyUserOrder);
        Assertions.assertEquals(firstBuyUserOrder, userTradeRepository.removeOrder(firstBuyUserOrder), "Removed something else");
    }


    @Test
    void clearMoreOneIHave() {
        UserTradeRepository userTradeRepository = new UserTradeRepository();
        userTradeRepository.addOrder(firstBuyUserOrder);
        firstBuyUserOrder.setAmount(100);
        firstBuyUserOrder.setAmountRest(100);


        Assertions.assertNull(userTradeRepository.removeOrder(firstBuyUserOrder), "What a magic");
        Assertions.assertNull(userTradeRepository.partlyCompleteOrder(firstBuyUserOrder), "What a magic");
    }

    @Test
    void addSeveralOrders() {
        UserTradeRepository userTradeRepository = new UserTradeRepository();
        Assertions.assertNotNull(userTradeRepository.addOrder(firstBuyUserOrder), "What a magic");
        Assertions.assertNotNull(userTradeRepository.addOrder(thirdBuyUserOrder), "What a magic");
    }

    @Test
    void clearMoreOneIHave2() {
        UserTradeRepository userTradeRepository = new UserTradeRepository();
        userTradeRepository.addOrder(firstBuyUserOrder);
        userTradeRepository.addOrder(thirdBuyUserOrder);
        firstBuyUserOrder.setAmount(firstBuyUserOrder.getAmount() + thirdBuyUserOrder.getAmount() - 1);
        firstBuyUserOrder.setAmountRest(firstBuyUserOrder.getAmountRest() + thirdBuyUserOrder.getAmountRest() - 1);


        Assertions.assertNull(userTradeRepository.removeOrder(firstBuyUserOrder), "What a magic");
        Assertions.assertNull(userTradeRepository.partlyCompleteOrder(firstBuyUserOrder), "What a magic");
    }
}

package src.model;

import java.util.Objects;

public class Order {
    private final Integer instrumentId;
    private final Side side;
    private final Long price;
    private Integer amount;
    private Integer amountRest;

    public Order(Order order) {
        this.instrumentId = order.getInstrumentId();
        this.side = order.getSide();
        this.price = order.getPrice();
        this.amount = order.getAmount();
        this.amountRest = order.getAmountRest();
    }

    public Order(Integer instrumentId, Side side, Long price, Integer amount, Integer amountRest) {
        this.instrumentId = instrumentId;
        this.side = side;
        this.price = price;
        this.amount = amount;
        this.amountRest = amountRest;
    }

    public Integer getInstrumentId() {
        return instrumentId;
    }

    public Side getSide() {
        return side;
    }

    public Long getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmountRest() {
        return amountRest;
    }

    public void setAmountRest(Integer amountRest) {
        this.amountRest = amountRest;
    }

    @Override
    public String toString() {
        return "src.model.Order{" +
                "instrumentId=" + instrumentId +
                ", side='" + side + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", amountRest=" + amountRest +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(instrumentId, order.instrumentId) &&
                side == order.side &&
                Objects.equals(price, order.price) &&
                Objects.equals(amount, order.amount) &&
                Objects.equals(amountRest, order.amountRest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrumentId, side, price, amount, amountRest);
    }
}

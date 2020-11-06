package src.model;

import java.util.Objects;

public class UserOrder extends Order{
    private final String userId;
    private final String clorderId;
    private final Action action;

    public UserOrder(String userId, String clorderId, Action action,
                     Integer instrumentId, Side side, Long price, Integer amount, Integer amountRest) {
        super(instrumentId, side, price, amount, amountRest);
        this.userId = userId;
        this.clorderId = clorderId;
        this.action = action;
    }

    public UserOrder(UserOrder userOrder) {
        super(userOrder.getInstrumentId(), userOrder.getSide(), userOrder.getPrice(), userOrder.getAmount(), userOrder.getAmountRest());
        this.userId = userOrder.getUserId();
        this.clorderId = userOrder.getClorderId();
        this.action = userOrder.getAction();
    }

    public String getUserId() {
        return userId;
    }

    public String getClorderId() {
        return clorderId;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "src.model.UserOrder{" +
                "userId='" + userId + '\'' +
                ", clorderId='" + clorderId + '\'' +
                ", action='" + action + '\'' +
                ", order=" + super.toString() +
        '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserOrder userOrder = (UserOrder) o;
        return Objects.equals(userId, userOrder.userId) &&
                Objects.equals(clorderId, userOrder.clorderId) &&
                action == userOrder.action &&
                Objects.equals(getAmount(), userOrder.getAmount()) &&
                Objects.equals(getAmountRest(), userOrder.getAmountRest());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId, clorderId, action, getAmount(), getAmountRest());
    }
}

package src.model;

public enum Action {
    ADD("0"), REMOVE("1"), PARTLY_DONE("2");

    private final String code;

    Action(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static Action getActionByCode(String code) {
        for (Action action : values()) {
            if (action.code.equals(code))
                return action;
        }

        return null;
    }

    @Override
    public String toString() {
        return "src.model.Action{" +
                "code='" + code + '\'' +
                "} " + super.toString();
    }
}



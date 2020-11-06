package src.model;

public enum Side {
    BUY("B"), SELL("S");

    private String code;

    Side(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Side getSideByCode(String code) {
        for(Side side : values()) {
            if (side.getCode().equals(code)) return side;
        }

        return null;
    }
}

package smartcast.uz.enums;

public enum ApiEndpoint {
    CREATE_CARD("/api/v1/cards"),
    WITHDRAW_FUNDS("/api/v1/cards/{cardId}/debit"),
    TOP_UP("/api/v1/cards/{cardId}/credit");


    private final String url;

    ApiEndpoint(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}


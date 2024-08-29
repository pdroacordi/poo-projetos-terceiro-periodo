package infra;

public class UrlBuilder {
    private final String baseUrl;
    private final StringBuilder queryParams = new StringBuilder();

    public UrlBuilder(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public UrlBuilder addParam(String key, String value) {
        if (!queryParams.isEmpty()) {
            queryParams.append("&");
        }
        queryParams.append(key).append("=").append(value);
        return this;
    }

    public String build() {
        return baseUrl + (!queryParams.isEmpty() ? "?" + queryParams : "");
    }
}

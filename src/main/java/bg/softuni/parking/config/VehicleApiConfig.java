package bg.softuni.parking.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "vehicle.api")
public class VehicleApiConfig {

    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public VehicleApiConfig setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }
}

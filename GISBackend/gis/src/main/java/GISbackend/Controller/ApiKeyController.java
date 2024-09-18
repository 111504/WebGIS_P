package GISbackend.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiKeyController {
    @Value("${google.api.key}")
    private String googleApiKey;

    @GetMapping("/api/get-google-api-key")
    public String getGoogleApiKey() {
        return googleApiKey;
    }
}

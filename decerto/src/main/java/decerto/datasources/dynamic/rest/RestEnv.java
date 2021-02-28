package decerto.datasources.dynamic.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestEnv {

    private RestTemplate restTemplate;

    @Autowired
    public RestEnv(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}

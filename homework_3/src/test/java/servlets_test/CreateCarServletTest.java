package servlets_test;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.classic.methods.HttpPost;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.HttpClients;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.ContentType;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpStatus;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.ParseException;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateCarServletTest {

    private final String BASE_URL = "http://localhost:8080/carshop/";

    private static String carJson;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void getData() throws IOException {
        carJson = objectMapper.writeValueAsString(
                objectMapper.readValue(new File("./src/test/resources/car_for_create.json"), Object.class));
    }

    @Test
    public void testCreateCar() throws IOException, ParseException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(BASE_URL + "admin/create_car");

            StringEntity entity = new StringEntity(carJson, ContentType.APPLICATION_JSON, "UTF-8", true);
            request.setEntity(entity);

            CloseableHttpResponse response = httpClient.execute(request);

            int statusCode = response.getCode();
            assertEquals(HttpStatus.SC_CREATED, statusCode);
        }
    }
}

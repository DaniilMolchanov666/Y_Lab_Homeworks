package servlets_test;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.classic.methods.HttpGet;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.HttpClients;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.ParseException;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.io.entity.EntityUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShowCarServletTest {

    private final String BASE_URL = "http://localhost:8080/carshop/";

    private static String cars;

    @BeforeAll
    public static void getData() throws IOException {
        cars = String.join("\n", Files.readAllLines(Path.of("./src/test/resources/cars")));
    }

    @Test
    public void testGetEndpoint() throws IOException, ParseException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL + "show_cars");

            CloseableHttpResponse response = httpClient.execute(request);
            int statusCode = response.getCode();
            assertEquals(200, statusCode);

            String responseBody = Arrays.toString(EntityUtils.toString(response.getEntity()).split("\n"));
            response.setHeader("Content-Type", "text/plain; charset=UTF-8");
            assertEquals(cars, responseBody);
        }
    }
}

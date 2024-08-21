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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthServletTest {

    private final String BASE_URL = "http://localhost:8080/carshop/";

    private static String userJson;

    @BeforeAll
    public static void getData() throws IOException {
        userJson = String.join("\n", Files.readAllLines(Path.of("./src/test/resources/user_for_login")));
    }

    @Test
    public void testAuthorization() throws IOException, ParseException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(BASE_URL + "login");

            StringEntity entity = new StringEntity(userJson, ContentType.APPLICATION_JSON, "UTF-8", true);
            request.setEntity(entity);

            CloseableHttpResponse response = httpClient.execute(request);
            int statusCode = response.getCode();
            assertEquals(HttpStatus.SC_OK, statusCode);
        }
    }
}

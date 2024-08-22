package com.ylab.servlets_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.classic.methods.HttpGet;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.classic.methods.HttpPost;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.HttpClients;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.ContentType;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpStatus;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.ParseException;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.io.entity.EntityUtils;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServletsTests {

    private final String BASE_URL = "http://localhost:8080/carshop/";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static String registerUser;

    private static String authUser;

    private static String cars;

    private static String carForCreate;


    @BeforeAll
    public static void setValues() throws IOException {
        registerUser = objectMapper.writeValueAsString(
                objectMapper.readValue(new File("./src/test/resources/user.json"), Object.class));
        authUser = objectMapper.writeValueAsString(
                objectMapper.readValue(new File("./src/test/resources/user_for_login.json"), Object.class));
        cars = String.join("\n", Files.readAllLines(Path.of("./src/test/resources/cars")));
        carForCreate = objectMapper.writeValueAsString(
                objectMapper.readValue(new File("./src/test/resources/car_for_create.json"), Object.class));

    }

    @Test
    @Order(1)
    public void testRegistration() throws IOException, ParseException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(BASE_URL + "register");

            StringEntity entity = new StringEntity(registerUser,
                    ContentType.APPLICATION_JSON, "UTF-8", true);

            request.setEntity(entity);

            CloseableHttpResponse response = httpClient.execute(request);
            int statusCode = response.getCode();
            assertEquals(HttpStatus.SC_OK, statusCode);
        }
    }

    @Test
    @Order(2)
    public void testAuthorization() throws IOException, ParseException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(BASE_URL + "login");

            StringEntity entity = new StringEntity(authUser, ContentType.APPLICATION_JSON, "UTF-8", true);
            request.setEntity(entity);

            CloseableHttpResponse response = httpClient.execute(request);
            int statusCode = response.getCode();
            assertEquals(HttpStatus.SC_OK, statusCode);
        }
    }

    @Test
    @Order(3)
    public void testShowCars() throws IOException, ParseException {
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

    @Test
    @Order(4)
    public void testCreateCar() throws IOException, ParseException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(BASE_URL + "admin/create_car");

            StringEntity entity = new StringEntity(carForCreate, ContentType.APPLICATION_JSON, "UTF-8", true);
            request.setEntity(entity);

            CloseableHttpResponse response = httpClient.execute(request);

            int statusCode = response.getCode();
            assertEquals(HttpStatus.SC_CREATED, statusCode);
        }
    }

    @Test
    @Order(5)
    public void testShowUsers() throws IOException, ParseException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL + "admin/users");

            CloseableHttpResponse response = httpClient.execute(request);
            int statusCode = response.getCode();
            assertEquals(200, statusCode);
        }
    }

    @Test
    @Order(6)
    public void testShowOrders() throws IOException, ParseException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL + "show_orders");

            CloseableHttpResponse response = httpClient.execute(request);
            int statusCode = response.getCode();
            assertEquals(200, statusCode);
        }
    }
}

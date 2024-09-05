package com.ylab.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Класс-конфигурация для заполнения общей информации для страницы документации
 */
@Configuration
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Приложение для оформления заказов автомобилей",
                version = "1.0",
                description = "Приложение позволяет регистрировать и авторизовать " +
                        "пользователей, делать заказы на покупку автомобилей," +
                        " а также выполнять стандартные CRUD операции для автомобилей",
                contact = @Contact(
                        name = "Даниил Молчанов",
                        email = "cottonpads11901@gmail,com",
                        url = "https://localhost:8080/carshop/"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local server"),
        }
)
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Автосалон")
                        .version("1.0")
                        .description("Description of your API"));
    }
}

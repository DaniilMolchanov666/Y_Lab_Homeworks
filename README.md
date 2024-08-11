# Домашнее задание №2
# Консольное приложение "Автосалон" с использованием миграций БД и JDBC
# Описание
Апгрейд домашнего задания №1 с заменой репозиториев на таблицы PostgreSQL и миграцией баз данных при
помощи инструментов LiquiBase

- аутентификация, авторизация и регистрация пользователей
- присвоение ролей пользователям и разделение функционала
- возможность редактирования заказов, автомобилей, информации о клиентах
- логирование и запись логов в файл

# Старт:
```sh
make run
```

```sh
docker compose up -d
gradle run
```

# Запустить тесты:
```sh
make test
```

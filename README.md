# Домашнее задание №3
# REST приложение "Автосалон" с использованием миграций БД и JDBC и сервлетов
# Описание
Апгрейд домашнего задания №1 с заменой репозиториев на таблицы PostgreSQL и миграцией баз данных при
помощи инструментов LiquiBase, а также добавлением сервлетов 

- аутентификация, авторизация и регистрация пользователей
- присвоение ролей пользователям и разделение функционала
- возможность редактирования заказов, автомобилей, информации о клиентах
- логирование и запись логов в файл

# Запуск 
Создание подключения к БД через docker compose и war файла, который потом добавляеся в директорию webapps вашего TomCat сервера
```sh
make run
```

# Основные запросы:

# Регистрация пользователя
`POST /carshop/register`

```sh
{
    "username": "dan",
    "password": "1234",
    "role": "ADMIN"
}
```

# Авторизация пользователя
`POST /carshop/login`
```sh
{
    "username": "dan",
    "password": "1234"
}
```

# Просмотр всех автомобилей
`GET /carshop/show_cars`

# Создание автомобиля (только для персонала)
`POST /carshop/admin/create_car`
```sh
{
    "brand": "LADA",
    "model": "LADA SEDAN",
    "price": "12929922",
    "year": "1990",
    "condition": "good"
}
```

# Обновление автомобиля (только для персонала)
`PUT /carshop/admin/edit_car`
```sh
{
    "brand": "LADA",
    "model": "LADA SEDAN",
    "price": "1000000",
    "year": "2000",
    "condition": "good"
}
```

# Удаление автомобиля (только для персонала)
`DELETE /carshop/admin/remove_car`
```sh
{
    "brand": "LADA",
    "model": "LADA SEDAN"
}
```

# Просмотр всех пользователей (только для администрации)
`GET /carshop/admin/users`

# Редактирование роли пользователя (только для администрации)
`PUT /carshop/admin/edit_role`
```sh
{
    "username": "dan",
    "role": "MANAGER"
}
```

# Редактирование данных пользователем (для клиентов)
`PUT /carshop/edit_profile`
```sh
{
    "username": "dan",
    "password: "12345"
}
```

# Удаление профиля (для клиентов)
`DELETE /carshop/remove_profile`

# Просмотр всех заказов 
`GET /carshop/show_orders`

# Создание заказа (для клиента)
`POST /carshop/create_order`
```sh
{
    "brand": "LADA",
    "model": "LADA SEDAN"
```

# Изменение статуса заказа (для персонала)
`PUT /carshop/create_order`
```sh
{
    "brand": "LADA",
    "model": "LADA SEDAN",
    "status": "IN_PROGRESS"
}
```

# Удаление заказа (для клиента)
`DELETE /carshop/remove_order`
```sh
{
    "brand": "LADA",
    "model": "LADA SEDAN",
}
```

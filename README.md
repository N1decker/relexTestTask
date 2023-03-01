# Тестовое задание от компании Релэкс #

## Биржа ##

---

Требовалось разработать RESTfull API service — биржу для проведения торгов криптовалютами.

Приложение реализовывает упрощенный функционал криптобиржи.
В системе две роли: пользователь и администратор.

Пользователь имеет возможность зарегистрироваться, открыть счет, пополнить кошелёк, вывести деньги.
Администратор имеет возможность посмотреть статистику по всем кошелькам пользователей,
статистику по торгам за определенный промежуток времени.
Все данные по кошелькам и истории сделок хранятся в базе данных PostgreSQL.

Сервисный слой покрыт тестами.

Добавил миграции liquibase, поэтому приложение уже готово для проверки.
Добавил кэширование на частые запросы.
Также, добавил обработку ошибок. Сделал, чтобы при регистрации
генерировался пароль.

#### Credentials: admin@gmail.com/admin & user@gmail.com/user #### 

Также, можно запустить через докер (выполнять в этом же порядке):

- docker run --name postgresql -e POSTGRES_PASSWORD=password -e POSTGRES_USER=user -e POSTGRES_DB=relexTestTask
  n1decker/relex-testtask-db
- docker run --name app --link postgresql:n1decker/relex-testtask-db -p 8080:8080 -d n1decker/relex-testtask-app

Из основных требований не смог реализовать изменение курса валют, обмен валют по установленному курсу, просмотр
актуальных курсов валют

Из доп. заданий выполнены:

+ подключить базу данных Sqlite или PostgreSQL для хранения данных о балансе пользовательских кошельков и истории
  операций;
+ сервис по запросу может возвращать данные в json ИЛИ xml.
+ подключить и настроить swagger;
+ использовать Spring Security для разграничения ролей (admin/user);

Примеры запросов:

-
    - post request: 'curl --location 'http://localhost:8080/rest/registration' \
      --header 'Content-Type: application/json' \
      --data-raw '{
      "name": "vasya_vezunchik",
      "email": "vasyu_kolbasit@mail.ru"
      }'
    - response: 'Save this code, otherwise important operations cannot be confirmed without it:
      82d69d7c-0981-4503-ba55-78b1e1d649a2' (always random secret key)
-
-
    - get request: curl --location 'http://localhost:8080/rest/user/wallets' \
      --header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' (username: admin@gmail.com, password: admin)
    - response: [
      {
      "name": "RUB",
      "balance": 10302.0
      },
      {
      "name": "BTC",
      "balance": 100.0
      },
      {
      "name": "TON",
      "balance": 250.0
      }
      ]
-
-
    - get request: curl
      --location 'http://localhost:8080/rest/admin/transactions?dateFrom=2023-02-28&dateTo=2023-03-01' \
      --header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' (username: admin@gmail.com, password: admin)
    - response: { "transaction_count": 9 }
-
-
    - post request: curl --location --request POST 'http://localhost:8080/rest/admin/currencies?currencyName=DLR' \
      --header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' (username: admin@gmail.com, password: admin)
    - response: { "id": 1, "name": "DLR" }
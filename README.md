Тестовое задание:
Сервер:
1)      Создать таблицу для хранения аутентификационных данных клиентов: номер телефона (мобильный), пароль (желательно хеш от пароля).
2)      Таблицу с балансами зарегестрированных пользователей с точностью до копеек.
3)      Создать сервлет, принимающий XML-запросы методом POST, который обрабатывает 2 запроса (см. ниже).
Регистрация нового клиента:
```
<?xml version=”1.0” encoding=”utf-8”?>
<request>
<type>registerCustomer</type>
<login>1234567890</login>
<password>password</password>
<request>
``` 
Ответ:
```
<?xml version=”1.0” encoding=”utf-8”?>
<response>
<result-code>0</ result-code>
</response>
```
Где код ошибки:
0 - Все хорошо
1 - Такой пользователь уже зарегистрирован
2 - Неверный формат телефона
3 - Плохой пароль
4 - Другая ошибка, повторите позже
 
Установка баланса:
```
<?xml version=”1.0” encoding=”utf-8”?>
<request>
<type>setBalance</type>
<login>1234567890</login>
<balance></balance>
<request>
```
Ответ:
```
<?xml version=”1.0” encoding=”utf-8”?>
<response>
<code>0</code>
<balance>100.00</balance>
</response>
```
Где код ошибки:
0 - Все хорошо
1 - Пользователь не найден
4 - Другая ошибка, повторите позже
Получение баланса:
```
<?xml version=”1.0” encoding=”utf-8”?>
<request>
<type>getBalance</type>
<login>1234567890</login>
<password>password</password>
<request>
```
Ответ:
```
<?xml version=”1.0” encoding=”utf-8”?>
<response>
<code>0</code>
<balance>100.00</ balance>
</response>
```
Где код ошибки:
0 - Все хорошо
1 - Пользователь не найден
3 - Плохой пароль
4 - Другая ошибка, повторите позже
Клиент
Написать клиентское приложение на Java, которое умеет отправлять на сервер все виды запросов, перечисленные выше.
Реализацию построить на Spring Framework. 
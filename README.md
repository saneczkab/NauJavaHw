## Описание
Генератор паролей.
Проект в рамках курса "Промышленная разработка на Java" от Naumen.  
Проект находится в процессе разработки.

## SQL-скрипты
В папке `sql` находятся скрипты:
1) `init.sql` - создание необходимых таблиц и заполнение их тестовыми данными.
2) `cleanup.sql` - удаление используемых приложением таблиц из базы данных.

Скрипты рассчитаны на PostgreSQL.

## Функции
1) Регистрация: `http://localhost:8080/registration`, авторизация: `http://localhost:8080/login`, выход из аккаунта: `http://localhost:8080/logout`
2) Swagger (доступен только для пользователей с ролью `ADMIN`: `http://localhost:8080/swagger-ui/index.html`
3) Просмотр списка всех пользователей: `http://localhost:8080/view/users/list`
4) Найти пользователей по логину или почте: `http://localhost:8080/api/users/findByUsernameOrEmail?email=<email>&username=<username>`  
Выводит список пользователей, у которых логин или почта совпадают с переданными в запросе параметрами.  
Примеры использования:
- `http://localhost:8080/api/users/findByUsernameOrEmail?email=white@cat.com`
- `http://localhost:8080/api/users/findByUsernameOrEmail?username=water_fox`
- `http://localhost:8080/api/users/findByUsernameOrEmail?email=white@cat.com&username=water_fox`
5) Найти пароли, созданные пользователем с указанным id: `http://localhost:8080/api/passwords/findByUserId?userId=<userId>`
6) Просмотр статистики приложения: `http://localhost:8080/view/stats`
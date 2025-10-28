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
1) Swagger: `http://localhost:8080/swagger-ui/index.html`
2) Просмотр списка всех пользователей: `http://localhost:8080/view/users/list`
3) Найти пользователей по логину или почте: `http://localhost:8080/api/users/findByUsernameOrEmail?email=<email>&username=<username>`  
Выводит список пользователей, у которых логин или почта совпадают с переданными в запросе параметрами.  
Примеры использования:
- `http://localhost:8080/api/users/findByUsernameOrEmail?email=white@cat.com`
- `http://localhost:8080/api/users/findByUsernameOrEmail?username=water_fox`
- `http://localhost:8080/api/users/findByUsernameOrEmail?email=white@cat.com&username=water_fox`
4) Найти пароли, созданные пользователем с указанным id: `http://localhost:8080/api/passwords/findByUserId?userId=<userId>`

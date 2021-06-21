Предотвращаем возможность новых подключений
UPDATE pg_database SET datallowconn = 'false' WHERE datname = 'my_db';

Закрываем текущие сессии
SELECT pg_terminate_backend(pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.datname = 'my_db' AND pid <> pg_backend_pid();

Удаляем базу
DROP DATABASE my_db;
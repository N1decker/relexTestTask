insert into users (id, name, email, enabled, locked, secret_key)
VALUES (1000, 'admin', 'admin@gmail.com', true, false, '$2a$12$4R1Kp92RexyByslL6y29Be74yHxrr0mUFuZwGZanXO8OBKJkOgaCW'),
       (1001, 'user', 'user@gmail.com', true, false, '$2a$12$DTgZ.5QGZ2SIBl6w11kUZeJLIZ6CFSdlLjTryntEkSz2C5wxhVJKq');

insert into user_role (user_id, role)
VALUES (1000, 'ADMIN'),
       (1000, 'USER'),
       (1001, 'USER');
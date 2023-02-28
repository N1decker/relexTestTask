insert into wallet (id, name, balance, user_id)
VALUES (1000, 'RUB', 10302, 1000),
       (1001, 'BTC', 100, 1000),
       (1002, 'TON', 250, 1000),

       (1003, 'RUB', 55003, 1001),
       (1004, 'BTC', 10, 1001);

insert into wallet_audit (id, transaction_date, user_email, wallet_audit_type, wallet_name)
VALUES (1000, current_date - 1, 'admin@gmail.com', 'CREATE', 'RUB'),
       (1001, current_date - 1, 'admin@gmail.com', 'CREATE', 'TON'),
       (1002, current_date - 1, 'admin@gmail.com', 'CREATE', 'BTC'),
       (1003, current_date, 'admin@gmail.com', 'TOPUP', 'RUB'),
       (1004, current_date, 'admin@gmail.com', 'WITHDRAW', 'RUB'),
       (1005, current_date, 'admin@gmail.com', 'TOPUP', 'BTC'),
       (1006, current_date, 'admin@gmail.com', 'TOPUP', 'TON'),

       (1007, current_date, 'user@gmail.com', 'CREATE', 'RUB'),
       (1008, current_date, 'user@gmail.com', 'CREATE', 'BTC'),
       (1009, current_date, 'user@gmail.com', 'TOPUP', 'RUB'),
       (1010, current_date, 'user@gmail.com', 'TOPUP', 'BTC'),
       (1011, current_date, 'user@gmail.com', 'WITHDRAW', 'BTC');
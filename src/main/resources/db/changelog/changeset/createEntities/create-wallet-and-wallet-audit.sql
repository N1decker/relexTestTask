create table wallet
(
    id      bigserial    not null primary key ,
    name    varchar(255) not null,
    balance float(53)    not null,
    user_id bigint       not null,
    constraint unique_wallet_user unique (name, user_id),
    foreign key (user_id) references users
);

create table wallet_audit
(
    id                bigserial not null primary key ,
    transaction_date  date,
    user_email        varchar(255),
    wallet_audit_type varchar(255),
    wallet_name       varchar(255)
);
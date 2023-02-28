create table users
(
    id         bigserial    not null primary key,
    name       varchar(40)  not null,
    email      varchar(50)  not null,
    enabled    boolean      not null,
    locked     boolean      not null,
    secret_key varchar(255) not null,
    constraint unique_name_email unique (name, email)
);

create table user_role
(
    user_id bigint       not null,
    role    varchar(255) not null,
    foreign key (user_id) references users on delete cascade
);

-- liquibase formatted sql

-- changeset Korolenko Sergey:1
create table if not exists items
(
    id        serial primary key,
    name      varchar(255) not null,
    price     numeric(10, 2) not null,
    promotion boolean not null
    );

-- changeset Korolenko Sergey:2
create table if not exists cards
(
    id       serial primary key,
    number   int not null,
    discount int not null
);
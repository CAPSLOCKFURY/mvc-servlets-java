drop table if exists dao_test;
drop table if exists dao_test_insert;
drop table if exists billing;
drop table if exists room_registry;
drop table if exists room_requests;
drop table if exists users;
drop table if exists roles;
drop table if exists rooms;
drop table if exists room_class_translation;
drop table if exists room_class;
drop type if exists room_status;
drop type if exists request_status;
drop domain if exists u_bigint;

create domain u_bigint as bigint check ( value > 0);

create table if not exists dao_test(
    id serial primary key,
    name varchar(255) not null,
    age int not null
);

create table if not exists dao_test_insert(
    id serial primary key,
    name varchar(255) not null,
    age int not null
);

create table if not exists roles(
    id serial primary key,
    name varchar(255) unique not null
);

create table if not exists users(
    id serial primary key,
    login varchar(255) unique not null,
    email text unique not null,
    password varchar(255) not null,
    role u_bigint not null default 1 references roles(id),
    balance decimal(11, 2) default 0,
    first_name varchar(255),
    last_name varchar(255)
);

create type room_status as enum ('unavailable', 'occupied', 'booked', 'free');

create table if not exists room_class(
    id serial primary key
);

create table if not exists room_class_translation(
    id serial primary key,
    class_id u_bigint references room_class(id),
    name varchar(255) not null,
    language varchar(4) not null
);

create table if not exists rooms(
    id serial primary key,
    number smallint unique not null,
    status room_status not null default 'free',
    name varchar(255),
    price decimal(11, 2) not null,
    capacity smallint not null,
    class u_bigint references room_class(id) not null
);

create type request_status as enum ('closed', 'paid', 'awaiting payment', 'awaiting confirmation', 'awaiting');

create table if not exists room_requests(
    id serial primary key,
    user_id u_bigint not null references users(id),
    capacity smallint not null,
    room_class u_bigint references room_class(id) not null,
    check_in_date date not null,
    check_out_date date not null,
    comment text,
    status request_status default 'awaiting',
    room_id u_bigint references rooms(id) default null,
    manager_comment text null
);

create table if not exists room_registry(
    id serial primary key,
    user_id u_bigint references users(id),
    room_id u_bigint references rooms(id),
    check_in_date date,
    check_out_date date,
    archived boolean default false
);

create table if not exists billing(
    id serial primary key,
    request_id u_bigint unique references room_requests(id),
    price decimal(11, 2) not null,
    pay_end_date date not null default current_date + interval '2 day',
    paid bool default false,
    room_registry_id u_bigint not null references room_registry(id)
);

insert into dao_test(name, age) values ('test', 10);
insert into dao_test(name, age) values ('test2', 11);
insert into dao_test(name, age) values ('tes3', 12);
insert into dao_test(name, age) values ('test4', 13);

insert into dao_test_insert(name, age) values ('testUpd', 110);

insert into room_class(id) values (1), (2), (3), (4);

insert into room_class_translation(class_id, name, language)
values (1, 'Cheap', 'en'), (1, 'Дешевый', 'ru'),
       (2, 'Normal', 'en'), (2, 'Обычный', 'ru'),
       (3, 'Half lux', 'en'), (3, 'Полу люкс', 'ru'),
       (4, 'Lux', 'en'), (4, 'Люкс', 'ru');

insert into rooms (number, status, name, price, capacity, class)
values (1, default, 'Room 1', 100, 2, 1), (2, default, 'Room 2', 110, 3, 1), (3, default, 'Room 3', 120, 3, 1), (4, default, 'Room 4', 150, 4, 1),
       (5, default, 'Normal Room 1', 200, 4, 2),(6, default, 'Normal Room 2', 220, 4, 2), (7, default, 'Normal Room 3', 250, 4, 2), (8, default, 'Normal Room 4', 300, 6, 2),
       (9, default, 'Half lux Room 1', 350, 4, 3),(10, default, 'Half lux Room 2', 370, 5, 3), (11, default, 'Half lux Room 3', 400, 4, 3), (12, default, 'Half lux Room 4', 500, 6, 3),
       (13, default, 'lux Room 1', 700, 8, 4),(14, default, 'lux Room 2', 1000, 8, 4);

insert into roles(name) values ('user'), ('manager');

insert into users(login, email, password, first_name, last_name)
values ('vadim', 'vadim@gmail.com', md5('123'), 'Vadim', 'Demb'),
       ('test', 'test@gmail.com', md5('12345'), 'Test', 'Testovich'),
       ('test2', 'test2@gmail.com', md5('test2'), 'Test2', 'Testovich2');

update users set balance = 100000 where id = 2;

insert into users(login, email, password, role, first_name, last_name)
values ('admin', 'admin@gmail.com', md5('333'), 2, 'Admin', 'Adminsky');

insert into room_registry(user_id, room_id, check_in_date, check_out_date) values (1, 1, date(now()), date(now()) + interval '7 day');

insert into room_requests(user_id, capacity, room_class, check_in_date, check_out_date, comment)
    values (2, 2, 1, date(now()), date(now()) + interval '7 day', 'comment');

insert into room_requests(user_id, capacity, room_class, check_in_date, check_out_date, comment, status, room_id)
    values (3, 2, 1, date(now()), date(now()) + interval '7 day', 'comment', 'awaiting confirmation', 5);

insert into room_requests(user_id, capacity, room_class, check_in_date, check_out_date, comment, status, room_id)
    values (3, 2, 1, date(now()), date(now()) + interval '7 day', 'comment', 'awaiting confirmation', 6);

insert into room_requests(user_id, capacity, room_class, check_in_date, check_out_date, comment)
    values (3, 2, 1, date(now()), date(now()) + interval '7 day', 'comment');
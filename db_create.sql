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
    request_id u_bigint unique references room_requests(id) on delete set null ,
    price decimal(11, 2) not null,
    pay_end_date date not null default current_date + interval '2 day',
    paid bool default false,
    room_registry_id u_bigint references room_registry(id) on delete set null
);
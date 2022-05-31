create sequence hibernate_sequence start 2 increment 1;
create table public.testusers (
    id NOT NULL AUTO_INCREMENT ,
    age int4 not null check (age>=0),
    fname varchar(25),
    lname varchar(40),
    mailadress varchar(255),
    patronymic varchar(255),
    salary float8 not null,
    workplace varchar(255),
    primary key (id)
);
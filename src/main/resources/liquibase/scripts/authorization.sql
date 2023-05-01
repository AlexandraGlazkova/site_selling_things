-- liquibase formatted sql

-- changeset filippova-t:1


create table ads
(
    id          int generated by default as identity primary key,
    price       integer not null,
    title       text not null,
    description text,
    author_id   int,
    image_id    int
);

create table comments
(
    id         int generated by default as identity primary key,
    created_at timestamp,
    text       text,
    author_id  int,
    ads_id     int
);



create table users
(
    id         int generated by default as identity primary key,
    email      text,
    first_name text,
    last_name  text,
    phone      text,
    image_id   int,
    role       text

);

create table images
(
    id         int generated by default as identity primary key,
    file_size  bigint not null,
    file_path  text,
    media_type text,
    data       bytea,
    ads_id     int

);
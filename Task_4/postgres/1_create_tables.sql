create table imagemeta
(
    id                   serial primary key,
    name                 varchar(50)          unique not null,
    key                  varchar(1024)        unique not null,
    date_created         date                 not null
);


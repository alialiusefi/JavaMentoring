create table imagemeta
(
    id                   serial primary key,
    name                 varchar(50)          not null,
    key                  varchar(1024)        not null,
    date_created         date                 not null
);


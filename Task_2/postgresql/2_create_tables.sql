create table giftcertificates
(
    id                   serial primary key,
    name                 varchar(50) not null,
    description          text,
    price                float       not null,
    date_created         date        not null,
    date_modified        date,
    duration_till_expiry integer     not null
);

create table tag
(
    id       serial primary key,
    tag_name varchar(16) unique not null
);

create table tagged_giftcertificates
(
    tag_id              integer references tag (id) on delete cascade,
    gift_certificate_id integer references giftcertificates (id) on delete cascade
);

create table users
(
    id       serial primary key,
    username varchar(50) unique not null,
    password varchar(64),
    enabled  boolean            not null
);

/*CREATE TYPE userstatus AS ENUM ('Administrator', 'Guest', 'User');*/

create table authorities
(
    id         serial primary key,
    user_id    integer not null,
    userstatus integer not null,
    constraint fk_authorities_users foreign key (user_id) references users (id)
);
create unique index ix_auth_username on authorities (user_id, userstatus);

create table orders
(
    id        serial primary key,
    ordercost float     not null,
    timestamp timestamp not null
);

create table order_user
(
    order_id integer not null,
    user_id  integer not null,
    constraint fk_users foreign key (user_id) references users (id),
    constraint fk_orders foreign key (order_id) references orders (id)

);

create table order_giftcertificate
(
    order_id           integer not null,
    giftcertificate_id integer not null,
    constraint fk_orders_id foreign key (order_id) references orders (id),
    constraint fk_orders_giftcertificates foreign key (giftcertificate_id) references giftcertificates (id)
);

CREATE FUNCTION public.consists(IN a text, IN b text)
    RETURNS boolean
    LANGUAGE 'plpgsql'
AS
$BODY$
begin
    return b like '%' || a || '%';
end;
$BODY$;

ALTER FUNCTION public.consists(text, text)
    OWNER TO postgres;
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
    username varchar(50) not null primary key,
    password varchar(50) not null,
    enabled  boolean     not null
);

create table authorities
(
    username  varchar(50) not null,
    authority integer not null,
    constraint fk_authorities_users foreign key (username) references users (username)
);
create unique index ix_auth_username on authorities (username, authority);

create table orders
(
    id        serial primary key,
    username           varchar(50) not null,
    giftcertificate_id integer     not null,
    ordercost float     not null,
    timestamp timestamp not null,
    constraint fk_orders_users foreign key (username) references users (username),
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
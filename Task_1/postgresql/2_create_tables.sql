create table giftcertificates
(
    id                   serial primary key,
    name                 varchar(16),
    description          text,
    price                float,
    date_created         date,
    date_modified        date,
    duration_till_expiry integer
);

create table tag
(
    id       serial primary key,
    tag_name varchar(16)
);

create table tagged_giftcertificates
(
    tag_id              integer references tag (id),
    gift_certificate_id integer references giftcertificates (id)
);



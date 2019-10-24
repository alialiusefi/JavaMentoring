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